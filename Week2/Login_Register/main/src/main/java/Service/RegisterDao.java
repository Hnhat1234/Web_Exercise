package Service;

public interface RegisterDao {
	boolean register(String username, String password);
	boolean exists(String username);
}
