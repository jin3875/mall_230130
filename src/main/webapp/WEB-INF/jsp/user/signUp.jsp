<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="input-box">
	<h3 class="text-center font-weight-bold">회원가입</h3>
	
	<div class="d-flex justify-content-between align-items-center mt-4">
		<span>아이디</span>
		<input type="text" id="loginId" class="form-control col-8">
	</div>
	
	<div>
		<div id="idCheckValidation" class="text-right small text-danger d-none">5~20자의 영문 소문자와 숫자만 사용 가능합니다.</div>
		<div id="idCheckDuplicated" class="text-center ml-5 small text-danger d-none">이미 사용중인 아이디입니다.</div>
		<div id="idCheckOk" class="text-center ml-4 small text-success d-none">사용 가능한 아이디입니다.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>비밀번호</span>
		<input type="password" id="password" class="form-control col-8">
	</div>
	
	<div>
		<div id="passwordCheckValidation" class="text-right mr-4 small text-danger d-none">5~20자의 비밀번호만 사용 가능합니다.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>비밀번호 확인</span>
		<input type="password" id="confirmPassword" class="form-control col-8">
	</div>
	
	<div>
		<div id="passwordCheckEqual" class="text-center ml-5 small text-danger d-none">비밀번호가 일치하지 않습니다.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>이름</span>
		<input type="text" id="name" class="form-control col-8">
	</div>
	
	<div>
		<div id="nameCheckValidation" class="text-right mr-5 small text-danger d-none">2자 이상의 한글만 사용 가능합니다.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>휴대폰 번호</span>
		<input type="text" id="phoneNumber" class="form-control col-8">
	</div>
	
	<div>
		<div id="phoneNumberCheckValidation" class="text-center ml-4 small text-danger d-none">숫자만 사용 가능합니다.</div>
		<div id="phoneNumberCheckLength" class="text-center ml-5 small text-danger d-none">휴대폰 번호가 올바르지 않습니다.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>이메일</span>
		<input type="text" id="email" class="form-control col-8">
	</div>
	
	<div>
		<div id="emailCheckValidation" class="text-center ml-5 small text-danger d-none">이메일 주소가 올바르지 않습니다.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>우편번호</span>
		<input type="text" id="postcode" class="form-control col-8" readonly/>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>주소</span>
		<input type="text" id="address" class="form-control col-8">
	</div>
	
	<div>
		<div id="addressCheckValidation" class="text-center small text-danger d-none">주소를 확인하세요.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>상세 주소</span>
		<input type="text" id="detailAddress" class="form-control col-8">
	</div>
	
	<div>
		<div id="detailAddressCheckEmpty" class="text-center ml-4 small text-danger d-none">상세 주소를 입력하세요.</div>
	</div>
	
	<button type="button" id="signUpBtn" class="btn btn-secondary btn-block mt-4">회원가입</button>
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	$(document).ready(function() {
		// 카카오 주소찾기 API
		$('#address').on('click', function() {
			new daum.Postcode({
				oncomplete: function(data) {
					$('#addressCheckValidation').addClass('d-none');
					$('#postcode').val(data.zonecode);
					$('#address').val(data.address);
					$('#detailAddress').focus();
				}
			}).open();
		});
		
		// 아이디 체크
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
			
			$.ajax({
				type:"GET"
				, url:"/user/is_duplicated_id"
				, data:{"loginId":loginId}
				
				, success:function(data) {
					if (data.code == 1) {
						if (data.result) {
							$('#idCheckDuplicated').removeClass('d-none');
						} else {
							$('#idCheckOk').removeClass('d-none');
						}
					} else {
						alert(data.errorMessage);
					}
				}
				, error:function(jqXHR, textStatus, errorThrown) {
					let errorMsg = jqXHR.responseJSON.status;
					alert(errorMsg + ":" + textStatus);
				}
			});
		});
		
		// 비밀번호 체크
		$('#password').on('change', function() {
			$('#passwordCheckValidation').addClass('d-none');
			$('#passwordCheckEqual').addClass('d-none');
			
			let password = $('#password').val();
			let confirmPassword = $('#confirmPassword').val();
			
			if (password.length < 5 || password.length > 20) {
				$('#passwordCheckValidation').removeClass('d-none');
				return;
			}
			
			if (confirmPassword == '') {
				return;
			}
			
			if (password != confirmPassword) {
				$('#passwordCheckEqual').removeClass('d-none');
				return;
			}
		});
		
		// 비밀번호 확인 체크
		$('#confirmPassword').on('change', function() {
			$('#passwordCheckEqual').addClass('d-none');
			
			let password = $('#password').val();
			let confirmPassword = $('#confirmPassword').val();
			
			if (password != confirmPassword) {
				$('#passwordCheckEqual').removeClass('d-none');
				return;
			}
		});
		
		// 이름 체크
		$('#name').on('change', function() {
			$('#nameCheckValidation').addClass('d-none');
			
			let name = $('#name').val().trim();
			let nameFormat = /^[가-힣]{2,15}$/;
			
			if (nameFormat.test(name) == false) {
				$('#nameCheckValidation').removeClass('d-none');
				return;
			}
		});
		
		// 휴대폰 번호 체크
		$('#phoneNumber').on('change', function() {
			$('#phoneNumberCheckValidation').addClass('d-none');
			$('#phoneNumberCheckLength').addClass('d-none');
			
			let phoneNumber = $('#phoneNumber').val().trim();
			let phoneNumberFormat = /^(010)[0-9]{8}$/;
			
			if (isNaN(phoneNumber)) {
				$('#phoneNumberCheckValidation').removeClass('d-none');
				return;
			}
			
			if (phoneNumberFormat.test(phoneNumber) == false) {
				$('#phoneNumberCheckLength').removeClass('d-none');
				return;
			}
		});
		
		// 이메일 체크
		$('#email').on('change', function() {
			$('#emailCheckValidation').addClass('d-none');
			
			let email = $('#email').val().trim();
			
			if (email.indexOf("@") == -1 || email.indexOf(".") == -1) {
				$('#emailCheckValidation').removeClass('d-none');
				return;
			}
		});
		
		// 주소 체크
		$('#address').on('change', function() {
			$('#addressCheckValidation').removeClass('d-none');
			return;
		});
		
		// 상세 주소 체크
		$('#detailAddress').on('change', function() {
			$('#detailAddressCheckEmpty').addClass('d-none');
			
			let detailAddress = $('#detailAddress').val().trim();
			
			if (detailAddress == '') {
				$('#detailAddressCheckEmpty').removeClass('d-none');
				return;
			}
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
			
			if ($('#idCheckValidation').hasClass('d-none') == false || $('#idCheckDuplicated').hasClass('d-none') == false) {
				$('#loginId').focus();
				return;
			}
			
			if ($('#idCheckOk').hasClass('d-none')) {
				$('#idCheckValidation').removeClass('d-none');
				$('#loginId').focus();
				return;
			}
			
			if (password == '') {
				$('#passwordCheckValidation').removeClass('d-none');
				$('#password').focus();
				return;
			}
			
			if ($('#passwordCheckValidation').hasClass('d-none') == false) {
				$('#password').focus();
				return;
			}
			
			if (confirmPassword == '') {
				$('#passwordCheckEqual').removeClass('d-none');
				$('#confirmPassword').focus();
				return;
			}
			
			if ($('#passwordCheckEqual').hasClass('d-none') == false) {
				$('#confirmPassword').focus();
				return;
			}
			
			if (name == '') {
				$('#nameCheckValidation').removeClass('d-none');
				$('#name').focus();
				return;
			}
			
			if ($('#nameCheckValidation').hasClass('d-none') == false) {
				$('#name').focus();
				return;
			}
			
			if (phoneNumber == '') {
				$('#phoneNumberCheckValidation').removeClass('d-none');
				$('#phoneNumber').focus();
				return;
			}
			
			if ($('#phoneNumberCheckValidation').hasClass('d-none') == false || $('#phoneNumberCheckLength').hasClass('d-none') == false) {
				$('#phoneNumber').focus();
				return;
			}
			
			if (email == '') {
				$('#emailCheckValidation').removeClass('d-none');
				$('#email').focus();
				return;
			}
			
			if ($('#emailCheckValidation').hasClass('d-none') == false) {
				$('#email').focus();
				return;
			}
			
			if (address == '') {
				$('#addressCheckValidation').removeClass('d-none');
				$('#address').focus();
				return;
			}
			
			if ($('#addressCheckValidation').hasClass('d-none') == false) {
				$('#address').focus();
				return;
			}
			
			if (detailAddress == '') {
				$('#detailAddressCheckEmpty').removeClass('d-none');
				$('#detailAddress').focus();
				return;
			}
			
			if ($('#detailAddressCheckEmpty').hasClass('d-none') == false) {
				$('#detailAddress').focus();
				return;
			}
			
			$.ajax({
				type:"POST"
				, url:"/user/sign_up"
				, data:{"loginId":loginId, "password":password, "name":name, "phoneNumber":phoneNumber,
					"email":email, "postcode":postcode, "address":address, "detailAddress":detailAddress}
				
				, success:function(data) {
					if (data.code == 1) {
						alert("회원가입이 완료되었습니다.");
						location.href="/product/product_main_view";
					} else {
						alert(data.errorMessage);
					}
				}
				, error:function(jqXHR, textStatus, errorThrown) {
					let errorMsg = jqXHR.responseJSON.status;
					alert(errorMsg + ":" + textStatus);
				}
			});
		});
	});
</script>