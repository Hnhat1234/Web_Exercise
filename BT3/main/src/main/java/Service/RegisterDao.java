package Service;

public interface RegisterDao {
	boolean register(String email, String username, String password);
	boolean exists(String email, String username);
	boolean emailExists(String email);
	String generatedOTP(String email, String password);
	String getHash(String email);
	boolean removeOTP(String email);
	boolean updatePassword(String email, String password);
}
