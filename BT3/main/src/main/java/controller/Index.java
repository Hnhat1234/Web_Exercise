package controller;

import java.io.IOException;

import Service.VideosImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/home")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("login");
			return;
		}

		Service.Videos videos = new VideosImpl();
		request.setAttribute("videos", videos.getTenVideos((String) session.getAttribute("email")));

        String page = "index.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);

        dispatcher.forward(request, response);
    }
}
