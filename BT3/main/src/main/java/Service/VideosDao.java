package Service;

import java.util.List;

import Model.Video;

public interface VideosDao {
	public boolean checkNameExists(String email, String name);
	public boolean uploadVideo(String email, String video_name);
	public List<String> getTenVideos(String email);
	public List<String> getFullVideos(String email);
}
