package Service;

import java.util.List;

public interface Videos {
	boolean checkNameExists(String email, String video_name);
	boolean uploadVideo(String email, String video_name);
	List<String> getTenVideos(String email);
	List<String> getFullVideos(String email);
}
