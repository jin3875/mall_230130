<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="sign-up-box">
	<h3 class="text-center font-weight-bold">회원가입</h3>
	
	<div class="d-flex justify-content-between align-items-center mt-4">
		<span>아이디</span>
		<input type="text" id="loginId" class="form-control col-8">
		
	</div>
	
	<!-- 아이디 체크 -->
	<div>
		<div id="idCheckValidation" class="text-right small text-danger d-none">5~20자의 영문 소문자와 숫자만 사용 가능합니다.</div>
		<div id="idCheckDuplicated" class="text-center ml-5 small text-danger d-none">이미 사용중인 아이디입니다.</div>
		<div id="idCheckOk" class="text-center ml-5 small text-success d-none">사용 가능한 아이디입니다.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>비밀번호</span>
		<input type="password" id="password" class="form-control col-8">
	</div>
	
	<!-- 비밀번호 체크 -->
	<div>
		<div id="passwordCheckValidation" class="text-right mr-4 small text-danger d-none">5~20자의 비밀번호만 사용 가능합니다.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>비밀번호 확인</span>
		<input type="password" id="confirmPassword" class="form-control col-8">
	</div>
	
	<!-- 비밀번호 일치 체크 -->
	<div>
		<div id="passwordCheck" class="text-center ml-5 small text-danger d-none">비밀번호가 일치하지 않습니다.</div>
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
		<input type="text" id="postcode" class="form-control col-8" readonly/>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>주소</span>
		<input type="text" id="address" class="form-control col-8">
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>상세 주소</span>
		<input type="text" id="detailAddress" class="form-control col-8">
	</div>
	
	<button type="button" id="signUpBtn" class="btn btn-secondary btn-block mt-3">회원가입</button>
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	$(document).ready(function() {
		// 아이디 검사
		$('#loginId').on('change', function() {
			$('#idCheckValidation').addClass('d-none');
			$('#idCheckDuplicated').addClass('d-none');
			$('#idCheckOk').addClass('d-none');
			
			let loginId = $('#loginId').val().trim();
			let idFormat = /^[a-z|0-9]{5,20}$/;
			
			if (idFormat.test(loginId) == false) {
				$('#idCheckValidation').removeClass('d-none');
				return;
			}
			
			// 중복 확인 AJAX
		});
		
		// 비밀번호 검사
		$('#password').on('change', function() {
			$('#passwordCheckValidation').addClass('d-none');
			
			let password = $('#password').val();
			
			if (password.length < 5 || password.length > 20) {
				$('#passwordCheckValidation').removeClass('d-none');
				return;
			}
		});
		
		// 카카오 주소찾기 API
		$('#address').on('click', function() {
			new daum.Postcode({
				oncomplete: function(data) {
					document.getElementById("postcode").value = data.zonecode; // 우편번호 넣기
					document.getElementById("address").value = data.address; // 주소 넣기
					document.getElementById("detailAddress").focus(); // 상세 주소 포커싱
				}
			}).open();
		});
		
		// 회원가입 버튼
		$('#signUpBtn').on('click', function() {
			let loginId = $('#loginId').val().trim();
			let password = $('#password').val();
			let confirmPassword = $('#confirmPassword').val();
			let name = $('#name').val().trim();
			let phoneNumber = $('#phoneNumber').val().trim();
			let email = $('#email').val().trim();
			let postcode = $('#postcode').val().trim();
			let address = $('#address').val().trim();
			let detailAddress = $('#detailAddress').val().trim();
			
			if ($('#idCheckOk').hasClass('d-none')) {
				alert("아이디를 확인하세요");
				return;
			}
			
		});
	});
</script>