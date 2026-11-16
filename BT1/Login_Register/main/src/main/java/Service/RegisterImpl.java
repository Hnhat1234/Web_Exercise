package Service;

public class RegisterImpl implements Register {
	private RegisterDao dao = new RegisterDaoImpl();
	
	public boolean register(String username, String password) {
		return dao.register(username, password);
	}
	
	public boolean exists(String username) {
		return dao.exists(username);
	}
}
