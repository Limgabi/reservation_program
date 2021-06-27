<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="reserve.Reserve" %>
<%@ page import="reserve.ReserveDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>식당 예약 프로그램</title>
</head>
<body>
	<%
		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		if (userID == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 하세요.')");
			script.println("location.href = 'login.jsp'");
			script.println("</script>");
		} 
		
		int reserveNum = 0;
		if (request.getParameter("reserveNum") != null) {
			reserveNum = Integer.parseInt(request.getParameter("reserveNum"));
		}
		if (reserveNum == 0) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않은 글입니다.')");
			script.println("location.href = 'reservation.jsp'");
			script.println("</script>");
		}
		Reserve reserve = new ReserveDAO().getReserve(reserveNum);
		if (!userID.equals(reserve.getUserID())) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('권한이 없습니다.')");
			script.println("location.href = 'review.jsp'");
			script.println("</script>");
		} else {
			
		ReserveDAO reserveDAO = new ReserveDAO();
		int result = reserveDAO.delete(reserveNum);
		if(result == -1){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('예약 취소를 실패했습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
		else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'reservation.jsp'");
			script.println("</script>");
		}
	}
	%>
</body>
</html>