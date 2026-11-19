package Form;

public class RegisterForm {
	private final String email;
	private final String username;
	private final String password;

	public RegisterForm(String email, String username, String password) {
		this.email = email == null ? "" : email.trim();
		this.username = username == null ? "" : username.trim();
		this.password = password == null ? "" : password;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
