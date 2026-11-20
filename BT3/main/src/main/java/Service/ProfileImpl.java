package Service;

import java.security.NoSuchAlgorithmException;

import Model.UserProfile;

public class ProfileImpl implements Profile {
	private ProfileDao profile = new ProfileDaoImpl();
	public UserProfile getInfo(String username) {
		return profile.getInfo(username);
	}
	public boolean createProfile(String username, String full_name, String phone_number, String email) {
		return profile.createProfile(username, full_name, phone_number, email);
	}
	public String getOTP(String username, String email) {
		return profile.getOTP(username, email);
	}
	public boolean updateInfo(String username, String full_name, String phone_number, String email, String otp) {
		String stored = profile.getHash(email);
		if (stored == null) {
			return false;
		}
		try {
			String attempt = Hash.toHexString(Hash.getSHA(username + email + otp));
			if (!stored.equals(attempt)) {
				return false;
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return profile.updateInfo(username, full_name, phone_number, email);
	}
	public boolean updateImage(String username, String image) {
		return profile.updateImage(username, image);
	}
}
