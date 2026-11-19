package Form;

public class ForgotPasswordForm {
	private final String email;

	public ForgotPasswordForm(String email) {
		this.email = email == null ? "" : email.trim();
	}

	public String getEmail() {
		return email;
	}
}
