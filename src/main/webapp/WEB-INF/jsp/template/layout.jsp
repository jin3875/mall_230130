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
	
	<!-- slide -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
	<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>
	
	<!-- datepicker -->
	<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
	<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
	
	<!-- fullcalendar -->
	<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.4/index.global.min.js'></script>
	
	<!-- style -->
	<link rel="stylesheet" type="text/css" href="/static/css/style.css">
</head>
<body>
	<div id="wrap">
		<nav class="d-flex justify-content-end">
			<jsp:include page="../include/menu.jsp" />
		</nav>
		<header class="d-flex align-items-center">
			<jsp:include page="../include/header.jsp" />
		</header>
		<div class="d-flex my-5">
			<section class="contents">
				<jsp:include page="../${viewName}.jsp" />
			</section>
			<aside class="d-flex justify-content-center">
				<jsp:include page="../include/aside.jsp" />
			</aside>
		</div>
		<footer class="d-flex justify-content-center align-items-end">
			<jsp:include page="../include/footer.jsp" />
		</footer>
	</div>
</body>
</html>