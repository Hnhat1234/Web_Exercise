package Validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class InputChecks {
	private static final Pattern EMAIL = Pattern.compile("^\\S+@\\S+\\.\\S+$");
	private static final Pattern USERNAME = Pattern.compile("^[A-Za-z0-9._-]{3,30}$");
	private static final Pattern OTP = Pattern.compile("^\\d{4}$");

	private static final Set<String> VIDEO_EXTENSIONS = Collections.unmodifiableSet(
		    new HashSet<>(Arrays.asList("mp4", "webm", "ogv", "ogg", "mov", "avi", "mkv", "m4v", "3gp"))
			);

	public static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}

	public static boolean isValidEmail(String email) {
		return email != null && EMAIL.matcher(email).matches();
	}

	public static boolean isValidUsername(String username) {
		return username != null && USERNAME.matcher(username).matches();
	}

	public static boolean isValidOtp(String otp) {
		return otp != null && OTP.matcher(otp).matches();
	}

	public static boolean isValidPassword(String password) {
		return password != null && password.length() >= 6;
	}

	public static boolean hasVideoExtension(String fileName) {
		if (fileName == null) {
			return false;
		}
		int dot = fileName.lastIndexOf('.');
		if (dot < 0) {
			return false;
		}
		return VIDEO_EXTENSIONS.contains(fileName.substring(dot + 1).toLowerCase());
	}
}
