package controller;

import java.io.IOException;

import Service.VideosImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/watch")
public class Watch extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("email") == null) {
			response.sendRedirect("login");
			return;
		}
		String email = (String) session.getAttribute("email");

		String name = request.getParameter("name");
		if (name == null || name.trim().isEmpty()) {
			response.sendRedirect("Videos");
			return;
		}
		name = name.trim();

		Service.Videos videos = new VideosImpl();
		if (!videos.checkNameExists(email, name)) {
			response.sendRedirect("Videos");
			return;
		}

		request.setAttribute("name", name);
		request.setAttribute("fileName", name);
		request.getRequestDispatcher("views/result.jsp").forward(request, response);
	}
}
