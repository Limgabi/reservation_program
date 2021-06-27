<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="review.ReviewDAO" %>
<%@ page import="review.Review" %>
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
		
		int reviewNum = 0;
		if (request.getParameter("reviewNum") != null) {
			reviewNum = Integer.parseInt(request.getParameter("reviewNum"));
		}
		if (reviewNum == 0) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않은 글입니다.')");
			script.println("location.href = 'review.jsp'");
			script.println("</script>");
		}
		Review review = new ReviewDAO().getReview(reviewNum);
		if (!userID.equals(review.getUserID())) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('권한이 없습니다.')");
			script.println("location.href = 'review.jsp'");
			script.println("</script>");
		} else {
			
		ReviewDAO reviewDAO = new ReviewDAO();
		int result = reviewDAO.delete(reviewNum);
		if(result == -1){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('리뷰 삭제에 실패했습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
		else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'review.jsp'");
			script.println("</script>");
		}
	}
	%>
</body>
</html>