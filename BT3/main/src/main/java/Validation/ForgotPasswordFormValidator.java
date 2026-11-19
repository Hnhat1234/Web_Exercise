package Validation;

import Form.ForgotPasswordForm;

public class ForgotPasswordFormValidator implements FormValidator<ForgotPasswordForm> {
	public String validate(ForgotPasswordForm form) {
		if (InputChecks.isBlank(form.getEmail())) {
			return "Please enter your email.";
		}
		if (!InputChecks.isValidEmail(form.getEmail())) {
			return "Please enter a valid email address.";
		}
		return null;
	}
}
