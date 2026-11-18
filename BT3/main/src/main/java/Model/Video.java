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
	@Column(name = "email")
	private String email;
	
	@Id
	@Column(name = "video_name")
	private String video_name;
	
	@Column(name = "uploaded_at")
	private LocalDateTime uploaded_at;
	
	public Video(){
		
	}
	public Video(String email, String video_name){
		this.email = email;
		this.video_name = video_name;
		this.uploaded_at = LocalDateTime.now();
	}
	public String getEmail() {
		return email;
	}
	public String getVideoName() {
		return video_name;
	}
	public LocalDateTime getUploadedAt() {
		return uploaded_at;
	}
}
