package controller;

import java.io.File;
import java.io.IOException;

import Form.VideoUploadForm;
import Service.VideosImpl;
import Validation.VideoUploadFormValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/Videos")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class Videos extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String uploadPath = "videos";

	private String getFileName(Part part) {
		String header = part.getHeader("content-disposition");
		if (header == null) {
			return "";
		}
		for (String content : header.split(";")) {
			if (content.trim().startsWith("filename")) {
				String fileName = content.substring(content.indexOf("=") + 2, content.length() - 1);
				return sanitize(new File(fileName).getName());
			}
		}
		return "";
	}

	private String sanitize(String value) {
		return value.replaceAll("[^A-Za-z0-9 ._-]", "_");
	}

	private String extensionOf(String fileName) {
		int dot = fileName.lastIndexOf('.');
		return dot < 0 ? "" : fileName.substring(dot);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("email") == null) {
			response.sendRedirect("login");
			return;
		}
		showUpload(request, response, (String) session.getAttribute("email"), null);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("email") == null) {
			response.sendRedirect("login");
			return;
		}
		String email = (String) session.getAttribute("email");

		String submitted = "";
		Part filePart = null;
		for (Part part : request.getParts()) {
			String uploaded = getFileName(part);
			if (uploaded.isEmpty()) {
				continue;
			}
			submitted = uploaded;
			filePart = part;
		}

		VideoUploadForm form = new VideoUploadForm(submitted, request.getParameter("name"));

		String error = new VideoUploadFormValidator().validate(form);
		if (error != null) {
			showUpload(request, response, email, error);
			return;
		}

		String name = form.getName();

		String fileName = submitted;
		if (!name.isEmpty()) {
			String extension = extensionOf(submitted);
			String base = sanitize(name);
			fileName = base.toLowerCase().endsWith(extension.toLowerCase()) ? base : base + extension;
		}

		Service.Videos videos = new VideosImpl();
		if (videos.checkNameExists(email, fileName)) {
			showUpload(request, response, email, "You have already uploaded a video with that name.");
			return;
		}

		File dir = new File(getServletContext().getRealPath("/" + uploadPath));
		dir.mkdirs();
		filePart.write(new File(dir, fileName).getAbsolutePath());

		if (!videos.uploadVideo(email, fileName)) {
			showUpload(request, response, email, "Could not save the video. Please try again.");
			return;
		}

		request.setAttribute("name", fileName);
		request.setAttribute("fileName", fileName);
		request.getRequestDispatcher("views/result.jsp").forward(request, response);
	}

	private void showUpload(HttpServletRequest request, HttpServletResponse response, String email, String error)
	throws ServletException, IOException {
		if (error != null) {
			request.setAttribute("error", error);
		}
		request.setAttribute("videos", new VideosImpl().getFullVideos(email));
		request.getRequestDispatcher("videos.jsp").forward(request, response);
	}
}
