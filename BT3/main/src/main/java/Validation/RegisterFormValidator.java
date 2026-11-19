package Validation;

import Form.RegisterForm;

public class RegisterFormValidator implements FormValidator<RegisterForm> {
	public String validate(RegisterForm form) {
		if (InputChecks.isBlank(form.getEmail())
				|| InputChecks.isBlank(form.getUsername())
				|| form.getPassword().isEmpty()) {
			return "Please enter your email, username and password.";
		}
		if (!InputChecks.isValidEmail(form.getEmail())) {
			return "Please enter a valid email address.";
		}
		if (!InputChecks.isValidUsername(form.getUsername())) {
			return "Username must be 3-30 characters using letters, numbers, '.', '_' or '-'.";
		}
		if (!InputChecks.isValidPassword(form.getPassword())) {
			return "Password must be at least 6 characters.";
		}
		return null;
	}
}
