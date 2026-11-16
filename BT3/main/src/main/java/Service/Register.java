package Service;

public interface Register {
	boolean exists(String email, String username);
	String generatedOTP(String email, String password);
	boolean verifyOTP(String email, String username, String password, String otp);
}
