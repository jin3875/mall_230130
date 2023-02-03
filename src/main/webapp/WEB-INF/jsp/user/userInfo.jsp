<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="d-flex">
	<div class="my-page-box mt-4">
		<h4 class="text-secondary">마이페이지</h4>
		<nav class="mt-4">
			<ul class="nav">
				<li class="nav-item"><a href="/user/user_purchase_list_view" class="nav-link font-weight-bold pl-0">구매 목록</a></li>
				<li class="nav-item"><a href="/user/user_review_list_view" class="nav-link font-weight-bold pl-0 mt-2">후기 목록</a></li>
				<li class="nav-item"><a href="/user/user_info_view" class="nav-link font-weight-bold pl-0 mt-2">회원 정보</a></li>
				<li class="nav-item"><a href="/user/user_sign_out_view" class="nav-link font-weight-bold pl-0 mt-2">회원 탈퇴</a></li>
			</ul>
		</nav>
	</div>
	
	<div class="input-big-box">
		<h3 class="text-center font-weight-bold">회원 정보</h3>
		
		<div class="d-flex justify-content-between align-items-center mt-4">
			<div class="input-small-box">
				<div class="d-flex justify-content-between align-items-center mt-4">
					<span>아이디</span>
					<div class="form-control col-8 bg-light">${user.loginId}</div>
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
				
				<button type="button" id="passwordEditBtn" class="btn btn-secondary btn-block mt-4">비밀번호 변경</button>
			</div>
			
			<div class="input-small-box">
				<div class="d-flex justify-content-between align-items-center mt-4">
					<span>이름</span>
					<div class="form-control col-8 bg-light">${user.name}</div>
				</div>
				
				<div class="d-flex justify-content-between align-items-center mt-3">
					<span>휴대폰 번호</span>
					<input type="text" id="phoneNumber" class="form-control col-8" value="${user.phoneNumber}">
				</div>
				
				<div>
					<div id="phoneNumberCheckValidation" class="text-center ml-4 small text-danger d-none">숫자만 사용 가능합니다.</div>
					<div id="phoneNumberCheckLength" class="text-center ml-5 small text-danger d-none">휴대폰 번호가 올바르지 않습니다.</div>
				</div>
				
				<div class="d-flex justify-content-between align-items-center mt-3">
					<span>이메일</span>
					<input type="text" id="email" class="form-control col-8" value="${user.email}">
				</div>
				
				<div>
					<div id="emailCheckValidation" class="text-center ml-5 small text-danger d-none">이메일 주소가 올바르지 않습니다.</div>
				</div>
				
				<div class="d-flex justify-content-between align-items-center mt-3">
					<span>우편번호</span>
					<input type="text" id="postcode" class="form-control col-8" value="${user.postcode}" readonly/>
				</div>
				
				<div class="d-flex justify-content-between align-items-center mt-3">
					<span>주소</span>
					<input type="text" id="address" class="form-control col-8" value="${user.address}">
				</div>
				
				<div>
					<div id="addressCheckValidation" class="text-center small text-danger d-none">주소를 확인하세요.</div>
				</div>
				
				<div class="d-flex justify-content-between align-items-center mt-3">
					<span>상세 주소</span>
					<input type="text" id="detailAddress" class="form-control col-8" value="${user.detailAddress}">
				</div>
				
				<div>
					<div id="detailAddressCheckEmpty" class="text-center ml-4 small text-danger d-none">상세 주소를 입력하세요.</div>
				</div>
				
				<button type="button" id="infoEditBtn" class="btn btn-secondary btn-block mt-4">회원 정보 수정</button>
			</div>
		</div>
	</div>
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
		
		// 비밀번호 변경 버튼
		$('#passwordEditBtn').on('click', function() {
			let password = $('#password').val();
			let confirmPassword = $('#confirmPassword').val();
			
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
			
			$.ajax({
				type:"PUT"
				, url:"/user/user_password_update"
				, data:{"password":password}
				
				, success:function(data) {
					if (data.code == 1) {
						alert("비밀번호가 변경되었습니다");
						location.reload();
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
		
		// 회원 정보 수정 버튼
		$('#infoEditBtn').on('click', function() {
			let phoneNumber = $('#phoneNumber').val().trim();
			let email = $('#email').val().trim();
			let postcode = $('#postcode').val().trim();
			let address = $('#address').val().trim();
			let detailAddress = $('#detailAddress').val().trim();
			
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
				type:"PUT"
				, url:"/user/user_info_update"
				, data:{"phoneNumber":phoneNumber, "email":email, "postcode":postcode, "address":address, "detailAddress":detailAddress}
				
			, success:function(data) {
				if (data.code == 1) {
					alert("회원 정보가 수정되었습니다");
					location.reload();
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