package Validation;

import Form.LoginForm;

public class LoginFormValidator implements FormValidator<LoginForm> {
	public String validate(LoginForm form) {
		if (InputChecks.isBlank(form.getUsername()) || form.getPassword().isEmpty()) {
			return "Please enter your username and password.";
		}
		return null;
	}
}
