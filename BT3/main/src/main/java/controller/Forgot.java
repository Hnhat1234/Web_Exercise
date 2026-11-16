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

@WebServlet("/forgot")
public class Forgot extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String page = "forgot.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);

		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		if ("reset".equals(request.getParameter("action"))) {
			reset(request, response);
		} else {
			sendOTP(request, response);
		}
	}

	private void sendOTP(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String email = request.getParameter("email");

		if (isBlank(email)) {
			request.setAttribute("error", "Please enter your email.");
			request.getRequestDispatcher("forgot.jsp").forward(request, response);
			return;
		}

		email = email.trim();

		Service.Register register = new RegisterImpl();

		if (!register.emailExists(email)) {
			request.setAttribute("error", "No account found with that email.");
			request.getRequestDispatcher("forgot.jsp").forward(request, response);
			return;
		}

		String otp = register.generatedOTP(email, "");
		if (otp == null || !new TLSEmail().sendOTP(email, otp)) {
			request.setAttribute("error", "Could not send the OTP. Please try again.");
			request.getRequestDispatcher("forgot.jsp").forward(request, response);
			return;
		}

		HttpSession session = request.getSession();
		session.setAttribute("resetEmail", email);

		request.setAttribute("email", email);
		request.getRequestDispatcher("reset.jsp").forward(request, response);
	}

	private void reset(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("resetEmail") == null) {
			response.sendRedirect("forgot");
			return;
		}

		String email = (String) session.getAttribute("resetEmail");
		String otp = request.getParameter("otp");
		String password = request.getParameter("password");

		if (isBlank(otp) || isBlank(password)) {
			request.setAttribute("email", email);
			request.setAttribute("error", "Please enter the OTP and your new password.");
			request.getRequestDispatcher("reset.jsp").forward(request, response);
			return;
		}

		Service.Register register = new RegisterImpl();

		if (!register.verifyResetOTP(email, otp.trim())) {
			request.setAttribute("email", email);
			request.setAttribute("error", "Invalid OTP. Please try again.");
			request.getRequestDispatcher("reset.jsp").forward(request, response);
			return;
		}

		if (register.resetPassword(email, password)) {
			session.removeAttribute("resetEmail");
			response.sendRedirect("login?reset=1");
		} else {
			request.setAttribute("email", email);
			request.setAttribute("error", "Could not reset your password. Please try again.");
			request.getRequestDispatcher("reset.jsp").forward(request, response);
		}
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
