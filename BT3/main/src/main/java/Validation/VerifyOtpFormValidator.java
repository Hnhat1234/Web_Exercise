package Validation;

import Form.VerifyOtpForm;

public class VerifyOtpFormValidator implements FormValidator<VerifyOtpForm> {
	public String validate(VerifyOtpForm form) {
		if (InputChecks.isBlank(form.getOtp())) {
			return "Please enter the OTP.";
		}
		if (!InputChecks.isValidOtp(form.getOtp())) {
			return "Please enter a 4-digit OTP.";
		}
		return null;
	}
}
