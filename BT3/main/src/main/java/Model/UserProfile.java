package Model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_profile")
public class UserProfile {
	@Id
	@Column(name = "username")
	private String username;
	
	@Column(name = "full_name")
	private String full_name;
	
	@Column(name = "phone_number")
	private String phone_number;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "image")
	private String image;
	
	public UserProfile() {
		
	}
	public UserProfile(String username, String full_name, String phone_number, String email) {
		this.username = username;
		this.full_name = full_name;
		this.phone_number = phone_number;
		this.email = email;
		this.image = "";
	}
	public String getUsername() {
		return username;
	}
	public String getFullname() {
		return full_name;
	}
	public String getPhonenumber() {
		return phone_number;
	}
	public String getEmail() {
		return email;
	}
	public String getImage() {
		return image;
	}
}
