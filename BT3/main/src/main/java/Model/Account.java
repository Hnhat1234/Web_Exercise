package Model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "email")
	private String email;
	
	@Column(name = "name")
	private String username;

	@Column(name = "password")
	private String password;

	public Account() {
	}

	public Account(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}