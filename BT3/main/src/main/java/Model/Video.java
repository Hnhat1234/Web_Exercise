package Model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "videos")
@IdClass(VideoId.class)
public class Video implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "username")
	private String username;
	
	@Id
	@Column(name = "video_name")
	private String video_name;
	
	@Column(name = "uploaded_at")
	private LocalDateTime uploaded_at;
	
	public Video(){
		
	}
	public Video(String username, String video_name){
		this.username = username;
		this.video_name = video_name;
		this.uploaded_at = LocalDateTime.now();
	}
	public String getUsername() {
		return username;
	}
	public String getVideoName() {
		return video_name;
	}
	public LocalDateTime getUploadedAt() {
		return uploaded_at;
	}
}
