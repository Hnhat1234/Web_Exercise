package controller;

import java.io.IOException;

import Service.RegisterImpl;
import Service.TLSEmail;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/register")
public class Register extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
		if ("verify".equals(request.getParameter("action"))) {
			verify(request, response);
		} else {
			sendOTP(request, response);
		}
	}

	private void sendOTP(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (isBlank(email) || isBlank(username) || isBlank(password)) {
			request.setAttribute("error", "Please enter your email, username and password.");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}

		email = email.trim();
		username = username.trim();

		Service.Register register = new RegisterImpl();

		if (register.exists(email, username)) {
			request.setAttribute("error", "Email or username already exists.");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}

		String otp = register.generatedOTP(email, password);
		if (otp == null || !new TLSEmail().sendOTP(email, otp)) {
			request.setAttribute("error", "Could not send the OTP. Please try again.");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}

		HttpSession session = request.getSession();
		session.setAttribute("otpEmail", email);
		session.setAttribute("otpUsername", username);
		session.setAttribute("otpPassword", password);

		request.setAttribute("email", email);
		request.getRequestDispatcher("verify.jsp").forward(request, response);
	}

	private void verify(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("otpEmail") == null) {
			response.sendRedirect("register");
			return;
		}

		String email = (String) session.getAttribute("otpEmail");
		String username = (String) session.getAttribute("otpUsername");
		String password = (String) session.getAttribute("otpPassword");
		String otp = request.getParameter("otp");

		if (isBlank(otp)) {
			request.setAttribute("email", email);
			request.setAttribute("error", "Please enter the OTP.");
			request.getRequestDispatcher("verify.jsp").forward(request, response);
			return;
		}

		Service.Register register = new RegisterImpl();

		if (register.verifyOTP(email, username, password, otp.trim())) {
			session.removeAttribute("otpEmail");
			session.removeAttribute("otpUsername");
			session.removeAttribute("otpPassword");
			response.sendRedirect("login?registered=1");
		} else {
			request.setAttribute("email", email);
			request.setAttribute("error", "Invalid OTP. Please try again.");
			request.getRequestDispatcher("verify.jsp").forward(request, response);
		}
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
