package Validation;

import Form.UpdateProfileOtpForm;

public class UpdateProfileOtpFormValidator implements FormValidator<UpdateProfileOtpForm> {
	public String validate(UpdateProfileOtpForm form) {
		if (InputChecks.isBlank(form.getOtp())) {
			return "Please enter the OTP.";
		}
		if (!InputChecks.isValidOtp(form.getOtp())) {
			return "Please enter a 4-digit OTP.";
		}
		return null;
	}
}
