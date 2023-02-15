<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
		<h3 class="font-weight-bold">환불 신청</h3>
		
		<div class="purchase-box d-flex align-items-center mt-5 mr-5 bg-light">
			<div class="col-3">
				<img src="${purchaseProductCardView.productDetailCardView.productPicture.imagePath}" alt="product" width="150" height="150">
			</div>
			<div class="col-7">
				<big class="font-weight-bold">${purchaseProductCardView.productDetailCardView.product.name}</big>
				<div class="text-secondary mt-3">
					<div class="col-4 d-flex justify-content-between p-0">
						<span>가격</span>
						<span><fmt:formatNumber value="${purchaseProductCardView.productDetailCardView.product.price}" type="number" />원</span>
					</div>
					
					<div class="col-4 d-flex justify-content-between p-0">
						<span>색상</span>
						<span>${purchaseProductCardView.productDetailCardView.productDetail.color}</span>
					</div>
					
					<div class="col-4 d-flex justify-content-between p-0">
						<span>사이즈</span>
						<span>${purchaseProductCardView.productDetailCardView.productDetail.size}</span>
					</div>
					
					<div class="col-4 d-flex justify-content-between p-0">
						<span>수량</span>
						<span>${purchaseProductCardView.purchaseProduct.amount}</span>
					</div>
				</div>
			</div>
			<h4 class="col-2"><fmt:formatNumber value="${purchaseProductCardView.productDetailCardView.product.price * purchaseProductCardView.purchaseProduct.amount}" type="number" />원</h4>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-5 mr-5">
			<h4 class="font-weight-bold m-0">환불 신청 이유</h4>
			<select id="reason" class="form-control col-4">
				<option value="" selected>-- 환불 신청 이유 --</option>
				<option>상품 불량</option>
				<option>디자인/색상 불만족</option>
				<option>사이즈 불만족</option>
				<option>재질/소재 불만족</option>
				<option>기타 이유</option>
			</select>
		</div>
		
		<div class="d-flex justify-content-between mt-5 mr-5">
			<h4 class="font-weight-bold m-0">상세 이유</h4>
			<textarea id="detailReason" class="form-control col-10" rows="5"></textarea>
		</div>
		
		<div class="d-flex justify-content-end mt-5 mr-5">
			<button type="button" id="refundBtn" class="btn btn-secondary" data-purchase-product-id="${purchaseProductCardView.purchaseProduct.id}">신청하기</button>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 신청하기 버튼
		$('#refundBtn').on('click', function() {
			let purchaseProductId = $(this).data('purchase-product-id');
			let reason = $('#reason').val();
			let detailReason = $('#detailReason').val();
			
			if (reason == '') {
				alert('환불 신청 이유를 선택하세요');
				return;
			}
			
			if (detailReason == '') {
				alert('상세 이유를 작성하세요');
				return;
			}
			
			$.ajax({
				type:"PUT"
				, url:"/purchase/product_refund"
				, data:{"purchaseProductId":purchaseProductId}
				
				, success:function(data) {
					if (data.code == 1) {
						alert("환불 신청이 완료되었습니다");
						location.href="/user/user_purchase_list_view";
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