package Service;

import java.util.List;

public interface Videos {
	boolean checkNameExists(String username, String video_name);
	boolean uploadVideo(String username, String video_name);
	List<String> getTenVideos(String username);
	List<String> getFullVideos(String username);
}
