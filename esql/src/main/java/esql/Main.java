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
					System.out.println("���� ����");
				}
				else {
					System.out.println("���� ����");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		int num = 0;
		int exit = 0; // case 13���� ���Ḧ ���� ����
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
			System.out.println("������ �Ĵ� ���� ����");
			System.out.println("------------------");
			System.out.println("0.user ���");
			System.out.println("1.�α���");
			System.out.println("2.�Ĵ� ����");
			System.out.println("3.�Ĵ� ���� ����");
			System.out.println("4.�Ĵ� ���� ���");
			System.out.println("5.�Ļ� ���� ����");
			System.out.println("6.���� �ֹ� ����");
			System.out.println("7.���� �ֹ� ����");
			System.out.println("8.���� �ֹ� ���");
			System.out.println("9.���� �ֹ� ����");
			System.out.println("10.���� ���");
			System.out.println("11.���� ����");
			System.out.println("12.���� ����");
			System.out.println("13.���� ��ȸ");
			System.out.println("-1.����");
			System.out.println("------------------");
			
			System.out.print("������ ��ȣ�� �����ϼ���.");
			num = sc.nextInt();
			
			switch(num) {
			case 0:
				System.out.println("ȸ�������� ���� ���̵�, ��й�ȣ, �̸�, ��ȭ��ȣ�� ��������.");
				System.out.print("ID: ");
				userID = sc.next();
				System.out.print("Password: ");
				userPassword = sc.next();
				System.out.print("�̸�: ");
				userName = sc.next();
				System.out.print("��ȭ��ȣ: ");
				userPhone = sc.next();
				System.out.println("ȸ�����Կ� �����Ͽ����ϴ�.");
				
				PreparedStatement psID = conn.prepareStatement("INSERT INTO USER VALUE (?, ?, ?, ?)");
				psID.setString(1, userID);
				psID.setString(2, userPassword);
				psID.setString(3, userName);
				psID.setString(4, userPhone);
				psID.executeUpdate();
				
				break;

			case 1:
				System.out.println("�α����� �ϼ���.");
				System.out.print("���̵�: ");
				userID = sc.next();
				System.out.print("��й�ȣ: ");
				userPassword = sc.next();
				
				int login = userDAO.login(userID, userPassword);
				if (login == 1) {
					System.out.println("�α��ο� �����Ͽ����ϴ�.");
				}
				else {
					System.out.println("�α��ο� �����߽��ϴ�.");
				}
				break;
			case 2:
				System.out.println("�Ļ� ���� �����Դϴ�. ���ϴ� ��¥, �ð��� �ο� ��, �޴��� �Է��ϼ���");
				System.out.print("���� ��¥: ");
				reserveDate = sc.next();
				System.out.print("���� �ð�: ");
				reserveTime = sc.next();
				System.out.print("�ο� ��: ");
				reservePeople = sc.nextInt();
				System.out.print("�޴�: ");
				reserveMenu = sc.next();
				
				reserveDAO.write(userID, reserveDate, reserveTime,reservePeople, reserveMenu);
				System.out.println("����Ǿ����ϴ�.");
				break;
			case 3:
				System.out.print("�����ϰ��� �ϴ� ���� ��ȣ�� �Է��ϼ���: ");
				reserveNum = sc.nextInt();
				
				System.out.print("���� ��¥: ");
				reserveDate = sc.next();
				System.out.print("���� �ð�: ");
				reserveTime = sc.next();
				System.out.print("�ο� ��: ");
				reservePeople = sc.nextInt();
				System.out.print("�޴�: ");
				reserveMenu = sc.next();
				
				reserveDAO.update(reserveNum, reserveDate, reserveTime, reservePeople, reserveMenu);
				System.out.println("������ �����Ǿ����ϴ�.");
				
				break;
			case 4:
				System.out.print("����� ���� ��ȣ�� �Է��ϼ���: ");
				reserveNum = sc.nextInt();
				
				reserveDAO.delete(reserveNum);
				System.out.println("������ ���������� ��ҵǾ����ϴ�.");
				break;
			case 5:
				ArrayList<Reserve> reserveList = reserveDAO.getList(1);
				for (int i = 0; i < reserveList.size(); i++) {
					System.out.println("�Ĵ� ���� ��ȣ: " + reserveList.get(i).getReserveNum());
					System.out.println("������ ID: " + reserveList.get(i).getUserID());
					System.out.println("���� ��¥: " + reserveList.get(i).getReserveDate());
					System.out.println("���� �ð�: " + reserveList.get(i).getReserveTime());
					System.out.println("���� �ο�: " + reserveList.get(i).getReservePeople());
					System.out.println("���� �޴�: " + reserveList.get(i).getReserveMenu());
					System.out.println("========================================================");
				}
				break;

			case 6:
				System.out.println("���� �ֹ� ���� �����Դϴ�. ���ϴ� ���İ� �湮 ���� �ð��� �Է��ϼ���.");
				System.out.print("���� �ֹ� �޴�: ");
				pickupMenu = sc.next();
				System.out.print("�湮 ���� �ð�: ");
				pickupTime = sc.next();
				
				System.out.println("���� �ֹ��� ����Ǿ����ϴ�.");
				
				pickupDAO.write(userID, pickupMenu, pickupTime);
				break;
			case 7:
				System.out.print("�����ϰ��� �ϴ� �ֹ� ��ȣ�� �Է��ϼ���: ");
				pickupNum = sc.nextInt();
				
				System.out.print("���� �ֹ� �޴�: ");
				pickupMenu = sc.next();
				System.out.print("�湮 ���� �ð�: ");
				pickupTime = sc.next();
				
				pickupDAO.update(pickupNum, pickupMenu, pickupTime);
				
				System.out.println("�ֹ��� �����Ǿ����ϴ�.");
				break;
			case 8:
				System.out.print("����� �ֹ� ��ȣ�� �Է��ϼ���: ");
				pickupNum = sc.nextInt();
				
				pickupDAO.delete(pickupNum);
				System.out.println("�ֹ��� ���������� ��ҵǾ����ϴ�.");
				break;
			case 9:
				ArrayList<Pickup> pickupList = pickupDAO.getList(1);
				for (int i = 0; i < pickupList.size(); i++) {
					System.out.println("�ֹ� ���� ��ȣ: " + pickupList.get(i).getPickupNum());
					System.out.println("�ֹ��� ID: " + pickupList.get(i).getUserID());
					System.out.println("�ֹ� ���� �޴�: " + pickupList.get(i).getPickupMenu());
					System.out.println("�湮 ���� �ð�: " + pickupList.get(i).getPickupTime());
					System.out.println("========================================================");
				}
				break;
			case 10:
				System.out.println("���並 ������ּ���.");
				System.out.print("���� ����: ");
				reviewTitle = sc.next();
				System.out.print("���� ����: ");
				reviewContent = sc.next();
				
				System.out.println("���䰡 ��ϵǾ����ϴ�.");
				
				reviewDAO.write(reviewTitle, userID, reviewContent);
				break;
			case 11:
				System.out.print("�����ϰ��� �ϴ� ���� ��ȣ�� �Է��ϼ���: ");
				reviewNum = sc.nextInt();
				
				System.out.print("���� ����: ");
				reviewTitle = sc.next();
				System.out.print("���� ����: ");
				reviewContent = sc.next();
				
				reviewDAO.update(reviewNum, reviewTitle, reviewContent);
				System.out.println("���䰡 �����Ǿ����ϴ�.");
				break;
			case 12:
				System.out.print("������ ���� ��ȣ�� �Է��ϼ���: ");
				reviewNum = sc.nextInt();
				
				reviewDAO.delete(reviewNum);
				System.out.println("���䰡 ���������� �����Ǿ����ϴ�.");
				break;
			case 13:
				ArrayList<Review> reviewList = reviewDAO.getList(1);
				for (int i = 0; i < reviewList.size(); i++) {
					System.out.println("���� ��ȣ: " + reviewList.get(i).getReviewNum());
					System.out.println("���� ����: " + reviewList.get(i).getReviewTitle());
					System.out.println("�ۼ��� ID: " + reviewList.get(i).getUserID());
					System.out.println("���� ����: " + reviewList.get(i).getReviewContent());
					System.out.println("�ۼ�����: " + reviewList.get(i).getReviewDate());
					System.out.println("========================================================");
				}	
				break;
			case -1:
				exit = -1;
				System.out.println("���α׷� ����");
				break;	
			}
			
			
			if (exit == -1) {
				break;
			}
			
		}
	
	}
}
