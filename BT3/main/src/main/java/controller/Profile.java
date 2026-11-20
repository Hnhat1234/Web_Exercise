package controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import Form.ImageUploadForm;
import Form.UpdateProfileForm;
import Form.UpdateProfileOtpForm;
import Model.UserProfile;
import Service.CloudinaryImage;
import Service.ProfileImpl;
import Service.RegisterImpl;
import Service.TLSEmail;
import Validation.ImageUploadFormValidator;
import Validation.UpdateProfileFormValidator;
import Validation.UpdateProfileOtpFormValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/profile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
public class Profile extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("login");
			return;
		}

		String username = (String) session.getAttribute("username");
		request.setAttribute("profile", new ProfileImpl().getInfo(username));

		request.getRequestDispatcher("profile.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		if ("verify".equals(request.getParameter("action"))) {
			verify(request, response);
		} else if ("image".equals(request.getParameter("action"))) {
			uploadImage(request, response);
		} else {
			update(request, response);
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("login");
			return;
		}
		String username = (String) session.getAttribute("username");

		UpdateProfileForm form = new UpdateProfileForm(
				request.getParameter("full_name"),
				request.getParameter("phone_number"),
				request.getParameter("email"));

		String error = new UpdateProfileFormValidator().validate(form);
		if (error != null) {
			showProfile(request, response, username, error, null);
			return;
		}

		String fullName = form.getFullName();
		String phoneNumber = form.getPhoneNumber();
		String email = form.getEmail();

		Service.Profile profile = new ProfileImpl();
		UserProfile current = profile.getInfo(username);
		String currentEmail = current == null ? null : current.getEmail();

		if (!email.equals(currentEmail) && new RegisterImpl().emailExists(email)) {
			showProfile(request, response, username, "That email is already in use.", null);
			return;
		}

		String otp = profile.getOTP(username, email);
		if (otp == null || !new TLSEmail().sendOTP(email, otp)) {
			showProfile(request, response, username, "Could not send the OTP. Please try again.", null);
			return;
		}

		session.setAttribute("profileFullName", fullName);
		session.setAttribute("profilePhoneNumber", phoneNumber);
		session.setAttribute("profileEmail", email);

		request.setAttribute("email", email);
		request.getRequestDispatcher("update_verify.jsp").forward(request, response);
	}

	private void verify(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("login");
			return;
		}
		if (session.getAttribute("profileEmail") == null) {
			response.sendRedirect("profile");
			return;
		}

		String username = (String) session.getAttribute("username");
		String fullName = (String) session.getAttribute("profileFullName");
		String phoneNumber = (String) session.getAttribute("profilePhoneNumber");
		String email = (String) session.getAttribute("profileEmail");

		UpdateProfileOtpForm form = new UpdateProfileOtpForm(request.getParameter("otp"));

		String error = new UpdateProfileOtpFormValidator().validate(form);
		if (error != null) {
			request.setAttribute("email", email);
			request.setAttribute("error", error);
			request.getRequestDispatcher("update_verify.jsp").forward(request, response);
			return;
		}

		Service.Profile profile = new ProfileImpl();
		if (profile.updateInfo(username, fullName, phoneNumber, email, form.getOtp())) {
			session.removeAttribute("profileFullName");
			session.removeAttribute("profilePhoneNumber");
			session.removeAttribute("profileEmail");
			session.setAttribute("email", email);
			response.sendRedirect("profile?updated=1");
		} else {
			request.setAttribute("email", email);
			request.setAttribute("error", "Invalid OTP. Please try again.");
			request.getRequestDispatcher("update_verify.jsp").forward(request, response);
		}
	}

	private void uploadImage(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("login");
			return;
		}
		String username = (String) session.getAttribute("username");

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

		ImageUploadForm form = new ImageUploadForm(submitted);

		String error = new ImageUploadFormValidator().validate(form);
		if (error != null) {
			showProfile(request, response, username, null, error);
			return;
		}

		String image;
		try {
			image = new CloudinaryImage().upload(readBytes(filePart), username);
		} catch (IOException e) {
			e.printStackTrace();
			image = null;
		}
		if (image == null) {
			showProfile(request, response, username, null, "Could not upload the image. Please try again.");
			return;
		}

		if (!new ProfileImpl().updateImage(username, image)) {
			showProfile(request, response, username, null, "Could not save the image. Please try again.");
			return;
		}

		response.sendRedirect("profile?uploaded=1");
	}

	private byte[] readBytes(Part part) throws IOException {
		try (InputStream in = part.getInputStream()) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			return out.toByteArray();
		}
	}

	private String getFileName(Part part) {
		String header = part.getHeader("content-disposition");
		if (header == null) {
			return "";
		}
		for (String content : header.split(";")) {
			if (content.trim().startsWith("filename")) {
				String fileName = content.substring(content.indexOf("=") + 2, content.length() - 1);
				return new File(fileName).getName();
			}
		}
		return "";
	}

	private void showProfile(HttpServletRequest request, HttpServletResponse response, String username, String error, String imageError)
	throws ServletException, IOException {
		if (error != null) {
			request.setAttribute("error", error);
		}
		if (imageError != null) {
			request.setAttribute("imageError", imageError);
		}
		request.setAttribute("profile", new ProfileImpl().getInfo(username));
		request.getRequestDispatcher("profile.jsp").forward(request, response);
	}
}
