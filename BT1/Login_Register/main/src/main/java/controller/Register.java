package controller;

import java.io.IOException;

import Service.RegisterImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class Register extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String page = "register.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (username == null || username.trim().isEmpty()
				|| password == null || password.isEmpty()) {
			request.setAttribute("error", "Please enter your username and password.");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		
		username = username.trim();
		Service.Register register = new RegisterImpl();
		
		if (register.exists(username)) {
			request.setAttribute("error", "Username already exists.");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		
		if (register.register(username, password)) {
			response.sendRedirect("login?registered=1");
		} else {
			request.setAttribute("error", "Registration failed. Please try again.");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
	}
}
