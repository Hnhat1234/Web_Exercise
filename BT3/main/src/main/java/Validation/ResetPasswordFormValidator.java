package Validation;

import Form.ResetPasswordForm;

public class ResetPasswordFormValidator implements FormValidator<ResetPasswordForm> {
	public String validate(ResetPasswordForm form) {
		if (InputChecks.isBlank(form.getOtp()) || form.getPassword().isEmpty()) {
			return "Please enter the OTP and your new password.";
		}
		if (!InputChecks.isValidOtp(form.getOtp())) {
			return "Please enter a 4-digit OTP.";
		}
		if (!InputChecks.isValidPassword(form.getPassword())) {
			return "Password must be at least 6 characters.";
		}
		return null;
	}
}
