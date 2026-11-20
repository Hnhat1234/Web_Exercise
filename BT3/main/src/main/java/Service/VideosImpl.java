package Service;

import java.util.List;

public class VideosImpl implements Videos {
	private VideosDao dao = new VideosDaoImpl();

	public boolean checkNameExists(String username, String video_name) {
		return dao.checkNameExists(username, video_name);
	}

	public boolean uploadVideo(String username, String video_name) {
		return dao.uploadVideo(username, video_name);
	}

	public List<String> getTenVideos(String username) {
		return dao.getTenVideos(username);
	}

	public List<String> getFullVideos(String username) {
		return dao.getFullVideos(username);
	}
}
