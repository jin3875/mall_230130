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
	
	<div class="input-box mt-5">
		<h3 class="text-center font-weight-bold">회원 탈퇴</h3>
		
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
		
		<button type="button" id="signOutBtn" class="btn btn-danger btn-block mt-4">회원 탈퇴</button>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 회원 탈퇴 버튼
		$('#signOutBtn').on('click', function() {
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
			
			if (confirm("탈퇴하시겠습니까?")) {
				$.ajax({
					type:"DELETE"
					, url:"/user/user_sign_out"
					, data:{"loginId":loginId, "password":password}
					
					, success:function(data) {
						if (data.code == 1) {
							alert("탈퇴가 완료되었습니다");
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
			} else {
				location.reload();
			}
		});
	});
</script>