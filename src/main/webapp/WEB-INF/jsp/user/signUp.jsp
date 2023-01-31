<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="sign-up-box">
	<h3 class="text-center font-weight-bold">회원가입</h3>
	
	<div class="d-flex justify-content-between align-items-center mt-4">
		<span>아이디</span>
		<input type="text" id="loginId" class="form-control col-8">
		
	</div>
	
	<!-- 아이디 체크 결과 -->
	<div class="d-flex justify-content-end">
		<div class="col-8">
			<div id="idCheckLength" class="small text-danger d-none">5~20자 아이디만 사용 가능합니다.</div>
			<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 아이디입니다.</div>
			<div id="idCheckOk" class="small text-success d-none">사용 가능한 아이디입니다.</div>
		</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>비밀번호</span>
		<input type="password" id="password" class="form-control col-8">
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>비밀번호 확인</span>
		<input type="password" id="confirmPassword" class="form-control col-8">
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>이름</span>
		<input type="text" id="name" class="form-control col-8">
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>휴대폰 번호</span>
		<input type="text" id="phoneNumber" class="form-control col-8">
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>이메일</span>
		<input type="text" id="email" class="form-control col-8">
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>우편번호</span>
		<input type="text" id="zonecode" class="form-control col-8">
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>주소</span>
		<input type="text" id="address" class="form-control col-8">
	</div>
	
	<button type="button" id="signUpBtn" class="btn btn-secondary btn-block mt-3">회원가입</button>
</div>

<script>
	$(document).ready(function() {
		$('#signUpBtn').on('click', function() {
			alert(111);
		});
	});
</script>