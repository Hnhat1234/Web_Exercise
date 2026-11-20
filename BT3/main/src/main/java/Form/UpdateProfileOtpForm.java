package Form;

public class UpdateProfileOtpForm {
	private final String otp;

	public UpdateProfileOtpForm(String otp) {
		this.otp = otp == null ? "" : otp.trim();
	}

	public String getOtp() {
		return otp;
	}
}
