package Model;

import java.io.Serializable;
import java.util.Objects;

public class VideoId implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;
	private String video_name;

	public VideoId() {
	}

	public VideoId(String username, String video_name) {
		this.username = username;
		this.video_name = video_name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, video_name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof VideoId)) {
			return false;
		}
		VideoId other = (VideoId) obj;
		return Objects.equals(username, other.username) && Objects.equals(video_name, other.video_name);
	}
}
