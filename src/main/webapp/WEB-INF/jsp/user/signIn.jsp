<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="input-box mt-5">
	<h3 class="text-center font-weight-bold">로그인</h3>
	
	<div class="d-flex justify-content-between align-items-center mt-5">
		<span>아이디</span>
		<input type="text" id="loginId" class="form-control col-8">
	</div>
	
	<div>
		<div id="idCheckEmpty" class="text-center small text-danger d-none">아이디를 입력하세요.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-4">
		<span>비밀번호</span>
		<input type="password" id="password" class="form-control col-8">
	</div>
	
	<div>
		<div id="passwordCheckEmpty" class="text-center ml-3 small text-danger d-none">비밀번호를 입력하세요.</div>
	</div>
	
	<button type="button" id="signInBtn" class="btn btn-secondary btn-block mt-4">로그인</button>
	
	<div class="d-flex justify-content-between mt-4">
		<a href="/user/search_id_view" class="col-3 btn btn-sm btn-light"><small>아이디 찾기</small></a>
		<a href="/user/search_password_view" class="col-3 btn btn-sm btn-light"><small>비밀번호 찾기</small></a>
		<a href="/user/sign_up_view" class="col-3 btn btn-sm btn-light"><small>회원가입</small></a>
	</div>
</div>

<script>
	$(document).ready(function() {
		$("#loginId").on('keydown', function(e) {
			if (e.keyCode == 13) {
				$('#signInBtn').trigger('click');
			}
		});
		
		$("#password").on('keydown', function(e) {
			if (e.keyCode == 13) {
				$('#signInBtn').trigger('click');
			}
		});
		
		$('#signInBtn').on('click', function() {
			$('#idCheckEmpty').addClass('d-none');
			$('#passwordCheckEmpty').addClass('d-none');
			
			let loginId = $('#loginId').val().trim();
			let password = $('#password').val();
			
			if (loginId == '') {
				$('#idCheckEmpty').removeClass('d-none');
				$('#loginId').focus();
				return;
			}
			
			if (password == '') {
				$('#passwordCheckEmpty').removeClass('d-none');
				$('#password').focus();
				return;
			}
			
			$.ajax({
				type:"POST"
				, url:"/user/sign_in"
				, data:{"loginId":loginId, "password":password}
				
				, success:function(data) {
					if (data.code == 1) {
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