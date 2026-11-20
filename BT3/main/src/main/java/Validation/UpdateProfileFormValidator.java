package Validation;

import Form.UpdateProfileForm;

public class UpdateProfileFormValidator implements FormValidator<UpdateProfileForm> {
	public String validate(UpdateProfileForm form) {
		if (InputChecks.isBlank(form.getEmail())) {
			return "Please enter your email.";
		}
		if (!InputChecks.isValidEmail(form.getEmail())) {
			return "Please enter a valid email address.";
		}
		if (!InputChecks.isBlank(form.getPhoneNumber()) && !InputChecks.isValidPhoneNumber(form.getPhoneNumber())) {
			return "Please enter a valid phone number.";
		}
		return null;
	}
}
