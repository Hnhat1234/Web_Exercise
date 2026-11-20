package Form;

public class RegisterForm {
	private final String email;
	private final String username;
	private final String password;
	private final String fullName;
	private final String phoneNumber;

	public RegisterForm(String email, String username, String password, String fullName, String phoneNumber) {
		this.email = email == null ? "" : email.trim();
		this.username = username == null ? "" : username.trim();
		this.password = password == null ? "" : password;
		this.fullName = fullName == null ? "" : fullName.trim();
		this.phoneNumber = phoneNumber == null ? "" : phoneNumber.trim();
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

	public String getFullName() {
		return fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
}
