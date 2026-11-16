package Service;

public interface Register {
	boolean exists(String email, String username);
	boolean emailExists(String email);
	String generatedOTP(String email, String password);
	boolean verifyOTP(String email, String username, String password, String otp);
	boolean verifyResetOTP(String email, String otp);
	boolean resetPassword(String email, String password);
}
