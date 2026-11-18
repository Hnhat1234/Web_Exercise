package Service;

import java.util.List;

public class VideosImpl implements Videos {
	private VideosDao dao = new VideosDaoImpl();

	public boolean checkNameExists(String email, String video_name) {
		return dao.checkNameExists(email, video_name);
	}

	public boolean uploadVideo(String email, String video_name) {
		return dao.uploadVideo(email, video_name);
	}

	public List<String> getTenVideos(String email) {
		return dao.getTenVideos(email);
	}

	public List<String> getFullVideos(String email) {
		return dao.getFullVideos(email);
	}
}
