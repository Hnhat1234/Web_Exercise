package Service;

import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDaoImpl implements LoginDao {
	
	@Override
	public Account login(String username, String password) {
		String sql = "SELECT * FROM user WHERE name = ? AND password = ?";
		try (Connection conn = GetDBConnection.getconn();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, username);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Account c = new Account();
					c.set(rs.getString("name"), rs.getString("password"));
					return c;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String get() {
		return "Test";
	}
	
	public static void main(String args[]) {
		LoginDaoImpl c = new LoginDaoImpl();
		Account account = c.login("hnhatn", "123456");
		System.out.printf("%s", account == null ? "null" : account.get());
	}
}
