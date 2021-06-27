package pickup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PickupDAO {
	private Connection conn;
	private ResultSet rs;
	
	public PickupDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/reservation";
			String dbID = "root";
			String dbPassword = "rkql142005*";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getNext() {
		String SQL = "SELECT pickupNum FROM PICKUP ORDER BY pickupNum DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; // 첫 번째 게시물인 경우
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	public int write(String userID, String pickupMenu, String pickupTime) {
		String SQL = "INSERT INTO pickup VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, userID);
			pstmt.setString(3, pickupMenu);
			pstmt.setString(4, pickupTime);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	public ArrayList<Pickup> getList(int pageNumber) {
		String SQL = "SELECT * FROM PICKUP WHERE pickupNum < ? ORDER BY pickupNum DESC LIMIT 10";
		ArrayList<Pickup> list = new ArrayList<Pickup>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Pickup pickup = new Pickup();
				pickup.setPickupNum(rs.getInt(1));
				pickup.setUserID(rs.getString(2));
				pickup.setPickupMenu(rs.getString(3));
				pickup.setPickupTime(rs.getString(4));
				list.add(pickup);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) {
		String SQL = "SELECT * FROM PICKUP WHERE pickupNum < ? ORDER BY pickupNum DESC LIMIT 10";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Pickup getPickup(int pickupNum) {
		String SQL = "SELECT * FROM PICKUP WHERE pickupNum = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, pickupNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Pickup pickup = new Pickup();
				pickup.setPickupNum(rs.getInt(1));
				pickup.setUserID(rs.getString(2));
				pickup.setPickupMenu(rs.getString(3));
				pickup.setPickupTime(rs.getString(4));
				
				return pickup;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int pickupNum, String pickupMenu, String pickupTime) {
		String SQL = "UPDATE PICKUP SET pickupMenu = ?, pickupTime = ? WHERE pickupNum = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, pickupMenu);
			pstmt.setString(2, pickupTime);
			pstmt.setInt(3, pickupNum);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	public int delete(int pickupNum) {
		String SQL = "DELETE FROM PICKUP WHERE pickupNum = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, pickupNum);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
}
