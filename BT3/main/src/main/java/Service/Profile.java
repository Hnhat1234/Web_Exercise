package Service;

import Model.UserProfile;

public interface Profile {
	UserProfile getInfo(String username);
	String getOTP(String username, String email);
	boolean createProfile(String username, String full_name, String phone_number, String email);
	public boolean updateInfo(String username, String full_name, String phone_number, String email, String otp);
	boolean updateImage(String username, String image);
}
