<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="reserve.ReserveDAO" %>
<%@ page import="reserve.Reserve" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>식당 예약 프로그램</title>
<style type="text/css">
	a, a:hover {
		color: #000000;
		text-decoraion: none;
	}
</style>
</head>
<body>
	<%
		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		int pageNumber = 1;
		if (request.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
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
						<th style="background-color: #eeeeee; text-align: center;">번호</th>
						<th style="background-color: #eeeeee; text-align: center;">ID</th>
						<th style="background-color: #eeeeee; text-align: center;">예약 일자</th>
						<th style="background-color: #eeeeee; text-align: center;">예약 시간</th>
						<th style="background-color: #eeeeee; text-align: center;">인원</th>
						<th style="background-color: #eeeeee; text-align: center;">메뉴</th>
					</tr>
				</thead>
				<tbody>
					<%
						ReserveDAO reserveDAO= new ReserveDAO();
						ArrayList<Reserve> list = reserveDAO.getList(pageNumber);
						for (int i = 0; i < list.size(); i++) {
					%>
					<tr>
						<td><%= list.get(i).getReserveNum() %></td>
						<td><a href="reserveView.jsp?reserveNum=<%= list.get(i).getReserveNum()%>"><%= list.get(i).getUserID() %></a></td>
						<td><%= list.get(i).getReserveDate() %></td>
						<td><%= list.get(i).getReserveTime() %></td>
						<td><%= list.get(i).getReservePeople() %></td>
						<td><%= list.get(i).getReserveMenu() %></td>
					</tr>
					<%		
						}
					%>
				</tbody>
			</table>
			<%
				if (pageNumber != 1) {
			%>		
				<a href="reservation.jsp?pageNumber=<%=pageNumber - 1 %>" class="btn btn-success btn-arraw-left">이전</a>
			<%
				} if (reserveDAO.nextPage(pageNumber + 1)) {
			%>
				<a href="reservation.jsp?pageNumber=<%=pageNumber + 1 %>" class="btn btn-success btn-arraw-left">다음</a>
			<%
				}
			%>
			<a href="reserve.jsp" class="btn btn-primary pull-right">예약하기</a>
		</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>