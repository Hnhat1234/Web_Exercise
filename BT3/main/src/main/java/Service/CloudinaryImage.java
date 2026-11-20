package Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryImage {
	private static final Properties ENV = loadEnv();

	private static Properties loadEnv() {
		Properties env = new Properties();
		try (InputStream in = CloudinaryImage.class.getClassLoader().getResourceAsStream(".env")) {
			if (in == null) {
				System.err.println("CloudinaryImage: .env not found on the classpath.");
			} else {
				env.load(in);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return env;
	}

	public String upload(byte[] bytes, String username) {
		Cloudinary cloudinary = new Cloudinary(ENV.getProperty("CLOUDINARY_URL", ""));
		try {
			Map<?, ?> result = cloudinary.uploader().upload(bytes, ObjectUtils.asMap(
					"public_id", "avatars/" + username,
					"overwrite", true,
					"invalidate", true,
					"transformation", "c_fill,w_256,h_256"));
			return (String) result.get("secure_url");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
