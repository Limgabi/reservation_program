<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="pickup.Pickup" %>
<%@ page import="pickup.PickupDAO" %>
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
		
		int pickupNum = 0;
		if (request.getParameter("pickupNum") != null) {
			pickupNum = Integer.parseInt(request.getParameter("pickupNum"));
		}
		if (pickupNum == 0) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않은 글입니다.')");
			script.println("location.href = 'pickup.jsp'");
			script.println("</script>");
		}
		Pickup pickup = new PickupDAO().getPickup(pickupNum);
		if (!userID.equals(pickup.getUserID())) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('권한이 없습니다.')");
			script.println("location.href = 'pickup.jsp'");
			script.println("</script>");
		} else {
			
		PickupDAO pickupDAO = new PickupDAO();
		int result = pickupDAO.delete(pickupNum);
		if(result == -1){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('포장 주문 취소를 실패했습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
		else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'pickup.jsp'");
			script.println("</script>");
		}
	}
	%>
</body>
</html>