<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="reserve.ReserveDAO" %>
    <%@ page import="java.io.PrintWriter" %>
    <%request.setCharacterEncoding("UTF-8"); %>
    <jsp:useBean id="reserve" class="reserve.Reserve" scope="page"/> 
    <jsp:setProperty name="reserve" property="reserveDate"/>
    <jsp:setProperty name="reserve" property="reserveTime"/>
    <jsp:setProperty name="reserve" property="reservePeople"/>
    <jsp:setProperty name="reserve" property="reserveMenu"/>
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
			if (reserve.getReserveDate() == null || reserve.getReserveTime() == null
					|| reserve.getReservePeople() == 0 || reserve.getReserveMenu() == null) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('입력이 안 된 사항이 있습니다.')");
				script.println("history.back()");
				script.println("</script>");
		} else {
			ReserveDAO reserveDAO = new ReserveDAO();
			int result = reserveDAO.write(userID, reserve.getReserveDate(), reserve.getReserveTime(),
					reserve.getReservePeople(), reserve.getReserveMenu());
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
				script.println("location.href = 'reservation.jsp'");
				script.println("</script>");
			}
		}
		}	
	%>
</body>
</html>