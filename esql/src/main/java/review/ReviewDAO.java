package review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReviewDAO {
	private Connection conn;
	private ResultSet rs;
	public ReviewDAO() {
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
	
	public String getDate() {
		String SQL = "SELECT now()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public int getNext() {
		String SQL = "SELECT reviewNum FROM REVIEW ORDER BY reviewNum DESC";
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
	
	public int write(String reviewTitle, String userID, String reviewContent) {
		String SQL = "INSERT INTO review VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, reviewTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, reviewContent);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	public ArrayList<Review> getList(int pageNumber) {
		String SQL = "SELECT * FROM REVIEW WHERE reviewNum < ? ORDER BY reviewNum DESC LIMIT 10";
		ArrayList<Review> list = new ArrayList<Review>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Review review = new Review();
				review.setReviewNum(rs.getInt(1));
				review.setReviewTitle(rs.getString(2));
				review.setUserID(rs.getString(3));
				review.setReviewDate(rs.getString(4));
				review.setReviewContent(rs.getString(5));
				list.add(review);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) {
		String SQL = "SELECT * FROM REVIEW WHERE reviewNum < ? ORDER BY reviewNum DESC LIMIT 10";
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
	
	public Review getReview(int reviewNum) {
		String SQL = "SELECT * FROM REVIEW WHERE reviewNum = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, reviewNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Review review = new Review();
				review.setReviewNum(rs.getInt(1));
				review.setReviewTitle(rs.getString(2));
				review.setUserID(rs.getString(3));
				review.setReviewDate(rs.getString(4));
				review.setReviewContent(rs.getString(5));
				return review;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int reviewNum, String reviewTitle, String reviewContent) {
		String SQL = "UPDATE REVIEW SET reviewTitle = ?, reviewContent = ? WHERE reviewNum = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, reviewTitle);
			pstmt.setString(2, reviewContent);
			pstmt.setInt(3, reviewNum);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	public int delete(int reviewNum) {
		String SQL = "DELETE FROM REVIEW WHERE reviewNum = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, reviewNum);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
}
