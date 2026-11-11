package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterDaoImpl implements RegisterDao {
	
	@Override
	public boolean exists(String username) {
		String sql = "SELECT name FROM user WHERE name = ?";
		try (Connection conn = GetDBConnection.getconn();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, username);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean register(String username, String password) {
		String sql = "INSERT INTO user(name, password) VALUES(?, ?)";
		try (Connection conn = GetDBConnection.getconn();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, username);
			ps.setString(2, password);
			return ps.executeUpdate() == 1;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
