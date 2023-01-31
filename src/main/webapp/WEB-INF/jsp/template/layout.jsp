<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GirlFit</title>
	<!-- jQuery -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	
	<!-- bootstrap -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	
	<!-- style -->
	<link rel="stylesheet" type="text/css" href="/static/css/style.css">
</head>
<body>
	<div id="wrap">
		<nav class="d-flex justify-content-end">
			<ul class="nav d-flex align-items-center">
				<!-- 비로그인 상태 -->
				<li class="nav-item"><a href="#" class="nav-link font-weight-bold">로그인</a></li>
				<li class="nav-item"><a href="#" class="nav-link font-weight-bold">회원가입</a></li>
				
				<!-- 로그인 상태 -->
				<span class="d-none">000님</span>
				<li class="nav-item"><a href="#" class="nav-link font-weight-bold d-none">로그아웃</a></li>
				<li class="nav-item"><a href="#" class="nav-link font-weight-bold d-none">장바구니</a></li>
				<li class="nav-item"><a href="#" class="nav-link font-weight-bold d-none">마이페이지</a></li>
			</ul>
		</nav>
		<header class="d-flex align-items-center">
			<jsp:include page="../include/header.jsp" />
		</header>
		<div class="center d-flex">
			<section class="contents col-10">
				<jsp:include page="../${viewName}.jsp" />
			</section>
			<aside class="col-2 d-flex justify-content-center">
				<jsp:include page="../include/aside.jsp" />
			</aside>
		</div>
		<footer class="d-flex justify-content-center align-items-center">
			<jsp:include page="../include/footer.jsp" />
		</footer>
	</div>
</body>
</html>