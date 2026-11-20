package Service;

import Model.UserProfile;

public interface ProfileDao {
	UserProfile getInfo(String username);
	boolean createProfile(String username, String full_name, String phone_number, String email);
	boolean updateInfo(String username, String full_name, String phone_number, String email);
	boolean updateImage(String username, String image);
	String getOTP(String username, String email);
	String getHash(String email);
}
