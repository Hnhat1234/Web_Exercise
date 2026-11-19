package controller;

import java.io.IOException;

import Form.ForgotPasswordForm;
import Form.ResetPasswordForm;
import Service.RegisterImpl;
import Service.TLSEmail;
import Validation.ForgotPasswordFormValidator;
import Validation.ResetPasswordFormValidator;

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
		ForgotPasswordForm form = new ForgotPasswordForm(request.getParameter("email"));

		String error = new ForgotPasswordFormValidator().validate(form);
		if (error != null) {
			request.setAttribute("error", error);
			request.getRequestDispatcher("forgot.jsp").forward(request, response);
			return;
		}

		String email = form.getEmail();

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

		ResetPasswordForm form = new ResetPasswordForm(
				request.getParameter("otp"), request.getParameter("password"));

		String error = new ResetPasswordFormValidator().validate(form);
		if (error != null) {
			request.setAttribute("email", email);
			request.setAttribute("error", error);
			request.getRequestDispatcher("reset.jsp").forward(request, response);
			return;
		}

		Service.Register register = new RegisterImpl();

		if (!register.verifyResetOTP(email, form.getOtp())) {
			request.setAttribute("email", email);
			request.setAttribute("error", "Invalid OTP. Please try again.");
			request.getRequestDispatcher("reset.jsp").forward(request, response);
			return;
		}

		if (register.resetPassword(email, form.getPassword())) {
			session.removeAttribute("resetEmail");
			response.sendRedirect("login?reset=1");
		} else {
			request.setAttribute("email", email);
			request.setAttribute("error", "Could not reset your password. Please try again.");
			request.getRequestDispatcher("reset.jsp").forward(request, response);
		}
	}
}
