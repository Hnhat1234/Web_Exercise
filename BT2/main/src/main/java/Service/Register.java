package Service;

public interface Register {
	boolean register(String username, String password);
	boolean exists(String username);
}
