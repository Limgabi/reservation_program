package esql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import user.*;
import reserve.*;
import pickup.*;
import review.*;

public class Main {
	
	public static void main(String[] args) throws SQLException {
		final String jdbc_driver = "com.mysql.jdbc.Driver";
		final String dbURL = "jdbc:mysql://localhost:3306/reservation";
		final String dbID = "root";
		final String dbPassword = "rkql142005*";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
				Class.forName(jdbc_driver);
				conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
				
				if (conn != null) {
					System.out.println("접속 성공");
				}
				else {
					System.out.println("접속 실패");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		int num = 0;
		int exit = 0; // case 13에서 종료를 위한 변수
		Scanner sc = new Scanner(System.in);
		
		UserDAO userDAO = new UserDAO();
		ReserveDAO reserveDAO = new ReserveDAO();
		PickupDAO pickupDAO = new PickupDAO();
		ReviewDAO reviewDAO = new ReviewDAO();
		
		String userID=null, userPassword, userName, userPhone;
		String reserveDate, reserveTime, reserveMenu; 
		int reserveNum, reservePeople, pickupNum, reviewNum = 0;
		String pickupTime, pickupMenu;
		String reviewTitle = null, reviewContent = null, reviewDate = null;
		
		while(true) {
			System.out.println("------------------");
			System.out.println("가비의 식당 예약 서비스");
			System.out.println("------------------");
			System.out.println("0.user 등록");
			System.out.println("1.로그인");
			System.out.println("2.식당 예약");
			System.out.println("3.식당 예약 수정");
			System.out.println("4.식당 예약 취소");
			System.out.println("5.식사 예약 내역");
			System.out.println("6.포장 주문 예약");
			System.out.println("7.포장 주문 수정");
			System.out.println("8.포장 주문 취소");
			System.out.println("9.포장 주문 내역");
			System.out.println("10.리뷰 등록");
			System.out.println("11.리뷰 수정");
			System.out.println("12.리뷰 삭제");
			System.out.println("13.리뷰 조회");
			System.out.println("-1.종료");
			System.out.println("------------------");
			
			System.out.print("수행할 번호를 선택하세요.");
			num = sc.nextInt();
			
			switch(num) {
			case 0:
				System.out.println("회원가입을 위한 아이디, 비밀번호, 이름, 전화번호를 적으세요.");
				System.out.print("ID: ");
				userID = sc.next();
				System.out.print("Password: ");
				userPassword = sc.next();
				System.out.print("이름: ");
				userName = sc.next();
				System.out.print("전화번호: ");
				userPhone = sc.next();
				System.out.println("회원가입에 성공하였습니다.");
				
				PreparedStatement psID = conn.prepareStatement("INSERT INTO USER VALUE (?, ?, ?, ?)");
				psID.setString(1, userID);
				psID.setString(2, userPassword);
				psID.setString(3, userName);
				psID.setString(4, userPhone);
				psID.executeUpdate();
				
				break;

			case 1:
				System.out.println("로그인을 하세요.");
				System.out.print("아이디: ");
				userID = sc.next();
				System.out.print("비밀번호: ");
				userPassword = sc.next();
				
				int login = userDAO.login(userID, userPassword);
				if (login == 1) {
					System.out.println("로그인에 성공하였습니다.");
				}
				else {
					System.out.println("로그인에 실패했습니다.");
				}
				break;
			case 2:
				System.out.println("식사 예약 서비스입니다. 원하는 날짜, 시간과 인원 수, 메뉴를 입력하세요");
				System.out.print("예약 날짜: ");
				reserveDate = sc.next();
				System.out.print("예약 시간: ");
				reserveTime = sc.next();
				System.out.print("인원 수: ");
				reservePeople = sc.nextInt();
				System.out.print("메뉴: ");
				reserveMenu = sc.next();
				
				reserveDAO.write(userID, reserveDate, reserveTime,reservePeople, reserveMenu);
				System.out.println("예약되었습니다.");
				break;
			case 3:
				System.out.print("수정하고자 하는 예약 번호를 입력하세요: ");
				reserveNum = sc.nextInt();
				
				System.out.print("예약 날짜: ");
				reserveDate = sc.next();
				System.out.print("예약 시간: ");
				reserveTime = sc.next();
				System.out.print("인원 수: ");
				reservePeople = sc.nextInt();
				System.out.print("메뉴: ");
				reserveMenu = sc.next();
				
				reserveDAO.update(reserveNum, reserveDate, reserveTime, reservePeople, reserveMenu);
				System.out.println("예약이 수정되었습니다.");
				
				break;
			case 4:
				System.out.print("취소할 예약 번호를 입력하세요: ");
				reserveNum = sc.nextInt();
				
				reserveDAO.delete(reserveNum);
				System.out.println("예약이 정상적으로 취소되었습니다.");
				break;
			case 5:
				ArrayList<Reserve> reserveList = reserveDAO.getList(1);
				for (int i = 0; i < reserveList.size(); i++) {
					System.out.println("식당 예약 번호: " + reserveList.get(i).getReserveNum());
					System.out.println("예약자 ID: " + reserveList.get(i).getUserID());
					System.out.println("예약 날짜: " + reserveList.get(i).getReserveDate());
					System.out.println("예약 시간: " + reserveList.get(i).getReserveTime());
					System.out.println("예약 인원: " + reserveList.get(i).getReservePeople());
					System.out.println("예약 메뉴: " + reserveList.get(i).getReserveMenu());
					System.out.println("========================================================");
				}
				break;

			case 6:
				System.out.println("포장 주문 예약 서비스입니다. 원하는 음식과 방문 예정 시간을 입력하세요.");
				System.out.print("포장 주문 메뉴: ");
				pickupMenu = sc.next();
				System.out.print("방문 예정 시간: ");
				pickupTime = sc.next();
				
				System.out.println("포장 주문이 예약되었습니다.");
				
				pickupDAO.write(userID, pickupMenu, pickupTime);
				break;
			case 7:
				System.out.print("수정하고자 하는 주문 번호를 입력하세요: ");
				pickupNum = sc.nextInt();
				
				System.out.print("포장 주문 메뉴: ");
				pickupMenu = sc.next();
				System.out.print("방문 예정 시간: ");
				pickupTime = sc.next();
				
				pickupDAO.update(pickupNum, pickupMenu, pickupTime);
				
				System.out.println("주문이 수정되었습니다.");
				break;
			case 8:
				System.out.print("취소할 주문 번호를 입력하세요: ");
				pickupNum = sc.nextInt();
				
				pickupDAO.delete(pickupNum);
				System.out.println("주문이 정상적으로 취소되었습니다.");
				break;
			case 9:
				ArrayList<Pickup> pickupList = pickupDAO.getList(1);
				for (int i = 0; i < pickupList.size(); i++) {
					System.out.println("주문 예약 번호: " + pickupList.get(i).getPickupNum());
					System.out.println("주문자 ID: " + pickupList.get(i).getUserID());
					System.out.println("주문 예약 메뉴: " + pickupList.get(i).getPickupMenu());
					System.out.println("방문 예정 시간: " + pickupList.get(i).getPickupTime());
					System.out.println("========================================================");
				}
				break;
			case 10:
				System.out.println("리뷰를 등록해주세요.");
				System.out.print("리뷰 제목: ");
				reviewTitle = sc.next();
				System.out.print("리뷰 내용: ");
				reviewContent = sc.next();
				
				System.out.println("리뷰가 등록되었습니다.");
				
				reviewDAO.write(reviewTitle, userID, reviewContent);
				break;
			case 11:
				System.out.print("수정하고자 하는 리뷰 번호를 입력하세요: ");
				reviewNum = sc.nextInt();
				
				System.out.print("리뷰 제목: ");
				reviewTitle = sc.next();
				System.out.print("리뷰 내용: ");
				reviewContent = sc.next();
				
				reviewDAO.update(reviewNum, reviewTitle, reviewContent);
				System.out.println("리뷰가 수정되었습니다.");
				break;
			case 12:
				System.out.print("삭제할 리뷰 번호를 입력하세요: ");
				reviewNum = sc.nextInt();
				
				reviewDAO.delete(reviewNum);
				System.out.println("리뷰가 정상적으로 삭제되었습니다.");
				break;
			case 13:
				ArrayList<Review> reviewList = reviewDAO.getList(1);
				for (int i = 0; i < reviewList.size(); i++) {
					System.out.println("리뷰 번호: " + reviewList.get(i).getReviewNum());
					System.out.println("리뷰 제목: " + reviewList.get(i).getReviewTitle());
					System.out.println("작성자 ID: " + reviewList.get(i).getUserID());
					System.out.println("리뷰 내용: " + reviewList.get(i).getReviewContent());
					System.out.println("작성일자: " + reviewList.get(i).getReviewDate());
					System.out.println("========================================================");
				}	
				break;
			case -1:
				exit = -1;
				System.out.println("프로그램 종료");
				break;	
			}
			
			
			if (exit == -1) {
				break;
			}
			
		}
	
	}
}
