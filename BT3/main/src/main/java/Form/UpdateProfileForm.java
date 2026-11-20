package Form;

public class UpdateProfileForm {
	private final String fullName;
	private final String phoneNumber;
	private final String email;

	public UpdateProfileForm(String fullName, String phoneNumber, String email) {
		this.fullName = fullName == null ? "" : fullName.trim();
		this.phoneNumber = phoneNumber == null ? "" : phoneNumber.trim();
		this.email = email == null ? "" : email.trim();
	}

	public String getFullName() {
		return fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}
}
