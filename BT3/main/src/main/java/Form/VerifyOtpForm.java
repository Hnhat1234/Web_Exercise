package Form;

public class VerifyOtpForm {
	private final String otp;

	public VerifyOtpForm(String otp) {
		this.otp = otp == null ? "" : otp.trim();
	}

	public String getOtp() {
		return otp;
	}
}
