package Form;

public class LoginForm {
	private final String username;
	private final String password;

	public LoginForm(String username, String password) {
		this.username = username == null ? "" : username.trim();
		this.password = password == null ? "" : password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
