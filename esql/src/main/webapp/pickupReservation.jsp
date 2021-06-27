<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="pickup.PickupDAO" %>
    <%@ page import="java.io.PrintWriter" %>
    <%request.setCharacterEncoding("UTF-8"); %>
    <jsp:useBean id="pickup" class="pickup.Pickup" scope="page"/> 
    <jsp:setProperty name="pickup" property="pickupMenu"/>
    <jsp:setProperty name="pickup" property="pickupTime"/>
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
		} else {
			if (pickup.getPickupMenu() == null || pickup.getPickupTime() == null) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('입력이 안 된 사항이 있습니다.')");
				script.println("history.back()");
				script.println("</script>");
		} else {
			PickupDAO pickupDAO = new PickupDAO();
			int result = pickupDAO.write(userID, pickup.getPickupMenu(), pickup.getPickupTime());
			if(result == -1){
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('예약에 실패했습니다.')");
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
		}	
	%>
</body>
</html>