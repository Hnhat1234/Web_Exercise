package controller;

import java.io.IOException;

import Model.Account;
import Service.LoginImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class Login extends HttpServlet {
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("username") != null) {
			response.sendRedirect("home");
			return;
		}
		
		String page = "login.jsp";
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
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		
		Service.Login login = new LoginImpl();
		Account account = login.login(username.trim(), password);
		
		if (account != null) {
			HttpSession session = request.getSession();
			session.setAttribute("username", account.get());
			response.sendRedirect("home");
		} else {
			request.setAttribute("error", "Invalid username or password.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
