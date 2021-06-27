package reserve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReserveDAO {
	private Connection conn;
	private ResultSet rs;
	
	public ReserveDAO() {
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
		String SQL = "SELECT reserveNum FROM RESERVE ORDER BY reserveNum DESC";
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
	
	public int write(String userID, String reserveDate, String reserveTime, int reservePeople, String reserveMenu) {
		String SQL = "INSERT INTO reserve VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, userID);
			pstmt.setString(3, reserveDate);
			pstmt.setString(4, reserveTime);
			pstmt.setInt(5, reservePeople);
			pstmt.setString(6, reserveMenu);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	public ArrayList<Reserve> getList(int pageNumber) {
		String SQL = "SELECT * FROM RESERVE WHERE reserveNum < ? ORDER BY reserveNum DESC LIMIT 10";
		ArrayList<Reserve> list = new ArrayList<Reserve>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Reserve reserve = new Reserve();
				reserve.setReserveNum(rs.getInt(1));
				reserve.setUserID(rs.getString(2));
				reserve.setReserveDate(rs.getString(3));
				reserve.setReserveTime(rs.getString(4));
				reserve.setReservePeople(rs.getInt(5));
				reserve.setReserveMenu(rs.getString(6));
				list.add(reserve);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) {
		String SQL = "SELECT * FROM RESERVE WHERE reserveNum < ? ORDER BY reserveNum DESC LIMIT 10";
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
	
	public Reserve getReserve(int reserveNum) {
		String SQL = "SELECT * FROM RESERVE WHERE reserveNum = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, reserveNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Reserve reserve = new Reserve();
				reserve.setReserveNum(rs.getInt(1));
				reserve.setUserID(rs.getString(2));
				reserve.setReserveDate(rs.getString(3));
				reserve.setReserveTime(rs.getString(4));
				reserve.setReservePeople(rs.getInt(5));
				reserve.setReserveMenu(rs.getString(6));
				
				return reserve;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int reserveNum, String reserveDate, String reserveTime, int reservePeople, String reserveMenu) {
		String SQL = "UPDATE RESERVE SET reserveDate = ?, reserveTime = ?, reservePeople = ?, reserveMenu = ? WHERE reserveNum = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, reserveDate);
			pstmt.setString(2, reserveTime);
			pstmt.setInt(3, reservePeople);
			pstmt.setString(4, reserveMenu);
			pstmt.setInt(5, reserveNum);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	public int delete(int reserveNum) {
		String SQL = "DELETE FROM RESERVE WHERE reserveNum = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, reserveNum);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
}
