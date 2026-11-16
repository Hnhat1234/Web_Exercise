package Model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "OTP")
public class OTP implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "email")
	private String email;
	
	@Column(name = "hash")
	private String hash;
	
	public OTP() {
		
	}
	public OTP(String email, String hash) {
		this.email = email;
		this.hash = hash;
	}
	public String getHash() {
		return hash;
	}
	public String getEmail() {
		return email;
	}
}
