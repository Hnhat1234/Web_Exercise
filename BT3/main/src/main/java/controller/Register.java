package controller;

import java.io.IOException;

import Form.RegisterForm;
import Form.VerifyOtpForm;
import Service.RegisterImpl;
import Service.TLSEmail;
import Validation.RegisterFormValidator;
import Validation.VerifyOtpFormValidator;

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
		RegisterForm form = new RegisterForm(
				request.getParameter("email"),
				request.getParameter("username"),
				request.getParameter("password"),
				request.getParameter("full_name"),
				request.getParameter("phone_number"));

		String error = new RegisterFormValidator().validate(form);
		if (error != null) {
			request.setAttribute("error", error);
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}

		String email = form.getEmail();
		String username = form.getUsername();
		String password = form.getPassword();

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
		session.setAttribute("otpFullName", form.getFullName());
		session.setAttribute("otpPhoneNumber", form.getPhoneNumber());

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
		String fullName = (String) session.getAttribute("otpFullName");
		String phoneNumber = (String) session.getAttribute("otpPhoneNumber");

		VerifyOtpForm form = new VerifyOtpForm(request.getParameter("otp"));

		String error = new VerifyOtpFormValidator().validate(form);
		if (error != null) {
			request.setAttribute("username", username);
			request.setAttribute("error", error);
			request.getRequestDispatcher("verify.jsp").forward(request, response);
			return;
		}

		Service.Register register = new RegisterImpl();

		if (register.verifyOTP(email, username, password, fullName, phoneNumber, form.getOtp())) {
			session.removeAttribute("otpEmail");
			session.removeAttribute("otpUsername");
			session.removeAttribute("otpPassword");
			session.removeAttribute("otpFullName");
			session.removeAttribute("otpPhoneNumber");
			response.sendRedirect("login?registered=1");
		} else {
			request.setAttribute("email", email);
			request.setAttribute("error", "Invalid OTP. Please try again.");
			request.getRequestDispatcher("verify.jsp").forward(request, response);
		}
	}
}
