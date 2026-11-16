package Service;

import Model.Account;

public class LoginImpl implements Login {
	private LoginDao dao = new LoginDaoImpl();
	
	public Account login(String username, String password) {
		return dao.login(username, password);
	}
	
	public String get() {
		return dao.get();
	}
}
