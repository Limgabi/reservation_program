<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="reserve.Reserve" %>
<%@ page import="reserve.ReserveDAO" %>
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
			<%
				if (userID == null) {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">접속하기<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul>
				</li>
			</ul>
			<%
				} else {
			%>
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
			<%
				}
			%>
		</div>
	</nav>
	
	<div class="container">
		<div class="row">
				<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th colspan="3" style="background-color: #eeeeee; text-align: center;">예약 내역 보기</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td style="width: 20%;">예약자 ID</td>
							<td colspan="2"><%= reserve.getUserID() %></td>
						</tr>
						<tr>
							<td>예약 날짜</td>
							<td colspan="2"><%= reserve.getReserveDate() %></td>
						</tr>
						<tr>
							<td>예약 시간</td>
							<td colspan="2"><%= reserve.getReserveTime() %></td>
						</tr>
						<tr>
							<td>예약 메뉴</td>
							<td colspan="2"><%= reserve.getReserveMenu() %></td>
						</tr>
					</tbody>
				</table>
				<a href="reservation.jsp" class="btn btn-primary">목록</a>
				<%
					if (userID != null && userID.equals(reserve.getUserID())) {
				%>
					<a href="reserveUpdate.jsp?reserveNum=<%=reserveNum %>" class="btn btn-primary">예약 수정</a>
					<a onclick="return confirm('예약을 취소하겠습니까?')"href="reserveDelete.jsp?reserveNum=<%=reserveNum %>" class="btn btn-primary">예약 취소</a>
				<%
					}
				%>
				<input type="submit" class="btn btn-primary pull-right" value="예약 작성">
		</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>