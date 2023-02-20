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
	
	<div class="w-100 ml-5">
		<div id="calendar"></div>
	</div>
</div>

<script>
	document.addEventListener('DOMContentLoaded', function() {
		$.ajax({
			type:"POST"
			, url:"/user/user_purchase_calendar"
			, dataType: "json"
			
			, success:function(data) {
				let calendar = new FullCalendar.Calendar($('#calendar')[0], {
					titleFormat: function (date) {
						year = date.date.year;
						month = date.date.month + 1;
						
						return year + "년 " + month + "월";
					}
					, events: data
				});
				
				calendar.render();
			}
			, error:function(jqXHR, textStatus, errorThrown) {
				let errorMsg = jqXHR.responseJSON.status;
				alert(errorMsg + ":" + textStatus);
			}
		});
	});
</script>