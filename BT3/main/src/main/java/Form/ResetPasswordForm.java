package Form;

public class ResetPasswordForm {
	private final String otp;
	private final String password;

	public ResetPasswordForm(String otp, String password) {
		this.otp = otp == null ? "" : otp.trim();
		this.password = password == null ? "" : password;
	}

	public String getOtp() {
		return otp;
	}

	public String getPassword() {
		return password;
	}
}
