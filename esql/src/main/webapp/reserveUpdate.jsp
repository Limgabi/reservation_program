<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="reserve.ReserveDAO" %>
<%@ page import="reserve.Reserve" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
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
			script.println("location.href = 'reservation.jsp'");
			script.println("</script>");
		}
	 %>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">식당 예약 프로그램</a>
		</div>
		<div class="collapse navbar-collapse" id="bs=example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
				<li class="active"><a href="reservation.jsp">예약</a></li>
				<li><a href="pickup.jsp">포장</a></li>
				<li><a href="review.jsp">리뷰</a></li>
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">회원관리<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="logoutAction.jsp">로그아웃</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>
	
	<div class="container">
		<div class="row">
			<form method="post" action="reserveUpdateAction.jsp?reserveNum=<%= reserveNum%>">
				<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th colspan="2" style="background-color: #eeeeee; text-align: center;">예약 수정 양식</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="text" class="form-control" placeholder="예약 날짜" name="reserveDate" maxlength="50" value="<%= reserve.getReserveDate() %>"></td>
						</tr>
						<tr>
							<td><input type="text" class="form-control" placeholder="예약 시간" name="reserveTime" maxlength="50" value="<%= reserve.getReserveTime() %>"></td>
						</tr>
						<tr>
							<td><input type="number" class="form-control" placeholder="인원 수" name="reservePeople" maxlength="50" value="<%= reserve.getReservePeople() %>"></td>
						</tr>
						<tr>
							<td><input type="text" class="form-control" placeholder="한식, 일식, 양식, 중식 중에 선택" name="reserveMenu" maxlength="50" value="<%= reserve.getReserveMenu() %>"></td>
						</tr>
					</tbody>
				</table>
				<input type="submit" class="btn btn-primary pull-right" value="예약 수정">
			</form>
		</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>