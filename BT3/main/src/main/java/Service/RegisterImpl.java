package Service;

import java.security.NoSuchAlgorithmException;

public class RegisterImpl implements Register {
	private RegisterDao dao = new RegisterDaoImpl();

	public boolean exists(String email, String username) {
		return dao.exists(email, username);
	}

	public String generatedOTP(String email, String password) {
		return dao.generatedOTP(email, password);
	}

	public boolean verifyOTP(String email, String username, String password, String otp) {
		String hash = dao.getHash(email);
		if (hash == null) {
			return false;
		}

		try {
			String attempt = Hash.toHexString(Hash.getSHA(email + password + otp));
			if (!hash.equals(attempt)) {
				return false;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}

		if (dao.register(email, username, password)) {
			dao.removeOTP(email);
			return true;
		}
		return false;
	}
}
