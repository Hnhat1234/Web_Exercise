package Service;

import java.util.List;

import Model.Video;

public interface VideosDao {
	public boolean checkNameExists(String username, String name);
	public boolean uploadVideo(String username, String video_name);
	public List<String> getTenVideos(String username);
	public List<String> getFullVideos(String username);
}
