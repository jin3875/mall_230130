<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="user-box mt-5">
	<h3 class="text-center font-weight-bold">비밀번호 찾기</h3>
	<h6 class="text-center mt-3">회원 정보 확인 후 임시 비밀번호를 제공합니다.</h6>
	
	<div class="d-flex justify-content-between align-items-center mt-5">
		<span>아이디</span>
		<input type="text" id="loginId" class="form-control col-8">
	</div>
	
	<div>
		<div id="idCheckEmpty" class="text-center small text-danger d-none">아이디를 입력하세요.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-4">
		<span>이름</span>
		<input type="text" id="name" class="form-control col-8">
	</div>
	
	<div>
		<div id="nameCheckEmpty" class="text-center mr-3 small text-danger d-none">이름을 입력하세요.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-4">
		<span>휴대폰 번호</span>
		<input type="text" id="phoneNumber" class="form-control col-8">
	</div>
	
	<div>
		<div id="phoneNumberCheckEmpty" class="text-center ml-4 small text-danger d-none">휴대폰 번호를 입력하세요.</div>
	</div>
	
	<button type="button" id="searchPasswordBtn" class="btn btn-secondary btn-block mt-4">확인</button>
</div>

<script>
	$(document).ready(function() {
		$("#loginId").on('keydown', function(e) {
			if (e.keyCode == 13) {
				$('#searchPasswordBtn').trigger('click');
			}
		});
		
		$("#name").on('keydown', function(e) {
			if (e.keyCode == 13) {
				$('#searchPasswordBtn').trigger('click');
			}
		});
		
		$("#phoneNumber").on('keydown', function(e) {
			if (e.keyCode == 13) {
				$('#searchPasswordBtn').trigger('click');
			}
		});
		
		$('#searchPasswordBtn').on('click', function() {
			$('#idCheckEmpty').addClass('d-none');
			$('#nameCheckEmpty').addClass('d-none');
			$('#phoneNumberCheckEmpty').addClass('d-none');
			
			let loginId = $('#loginId').val().trim();
			let name = $('#name').val().trim();
			let phoneNumber = $('#phoneNumber').val().trim();
			
			if (loginId == '') {
				$('#idCheckEmpty').removeClass('d-none');
				$('#loginId').focus();
				return;
			}
			
			if (name == '') {
				$('#nameCheckEmpty').removeClass('d-none');
				$('#name').focus();
				return;
			}
			
			if (phoneNumber == '') {
				$('#phoneNumberCheckEmpty').removeClass('d-none');
				$('#phoneNumber').focus();
				return;
			}
			
			$.ajax({
				type:"POST"
				, url:"/user/search_password"
				, data:{"loginId":loginId, "name":name, "phoneNumber":phoneNumber}
				
				, success:function(data) {
					if (data.code == 1) {
						alert(data.userName + "님의 임시 비밀번호는 " + data.userPassword + "입니다");
						location.href="/user/sign_in_view";
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