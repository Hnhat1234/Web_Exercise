package Model;

import java.io.Serializable;

public class Account implements Serializable {
	private String username;
	private String password;
	
	public String get() {
		return username;
	}
	
	public void set(String u, String p) {
		this.username = u;
		this.password = p;
	}
}
