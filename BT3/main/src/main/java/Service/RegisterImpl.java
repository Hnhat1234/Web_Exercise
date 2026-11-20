package Service;

import java.security.NoSuchAlgorithmException;

public class RegisterImpl implements Register {
	private RegisterDao dao = new RegisterDaoImpl();
	private Profile profile = new ProfileImpl();

	public boolean exists(String email, String username) {
		return dao.exists(email, username);
	}

	public boolean emailExists(String email) {
		return dao.emailExists(email);
	}

	public String generatedOTP(String email, String password) {
		return dao.generatedOTP(email, password);
	}

	public boolean verifyOTP(String email, String username, String password, String fullName, String phoneNumber, String otp) {
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

		if (dao.register(email, username, Hash.hashPassword(password))) {
			profile.createProfile(username, fullName, phoneNumber, email);
			dao.removeOTP(email);
			return true;
		}
		return false;
	}

	public boolean verifyResetOTP(String email, String otp) {
		String hash = dao.getHash(email);
		if (hash == null) {
			return false;
		}

		try {
			String attempt = Hash.toHexString(Hash.getSHA(email + otp));
			return hash.equals(attempt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean resetPassword(String email, String password) {
		if (dao.updatePassword(email, Hash.hashPassword(password))) {
			dao.removeOTP(email);
			return true;
		}
		return false;
	}
}
