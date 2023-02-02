<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="user-box mt-5">
	<div id="searchIdResult" class="text-center border p-4">
		<span id="userName" class="font-weight-bold"></span>님의 아이디는 <span id="userLoginId" class="font-weight-bold"></span>입니다.
	</div>
	
	<button type="button" id="searchIdBtn" class="btn btn-secondary btn-block mt-4">확인</button>
</div>

<script>
	$(document).ready(function() {
		let urlParams = new URL(location.href).searchParams;
		let userName = urlParams.get('userName');
		let userLoginId = urlParams.get('userLoginId');
		
		$('#userName').text(userName);
		$('#userLoginId').text(userLoginId);
	});
</script>