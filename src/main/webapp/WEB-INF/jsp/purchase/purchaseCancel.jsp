<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<h3 class="font-weight-bold">구매 취소</h3>
		
		<c:set var="totalPrice" value="0" />
		<c:forEach var="purchaseProductCardView" items="${purchaseCardView.purchaseProductCardViewList}" varStatus="status">
			<div class="purchase-box d-flex align-items-center mt-5 mr-5 bg-light">
				<h3 class="col-1 d-flex justify-content-center">${status.count}</h3>
				<div class="col-3">
					<img src="${purchaseProductCardView.productDetailCardView.productPicture.imagePath}" alt="product" width="150" height="150">
				</div>
				<div class="col-6">
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
				<h4 class="col-2 p-0"><fmt:formatNumber value="${purchaseProductCardView.productDetailCardView.product.price * purchaseProductCardView.purchaseProduct.amount}" type="number" />원</h4>
				<c:set var="totalPrice" value="${totalPrice + purchaseProductCardView.productDetailCardView.product.price * purchaseProductCardView.purchaseProduct.amount}" />
			</div>
		</c:forEach>
		
		<c:if test="${totalPrice < 30000}">
			<div class="d-flex justify-content-end mt-4 mr-5">
				<h5 class="mr-5">배송비 : 3,000원</h5>
			</div>
		</c:if>
		
		<div class="d-flex justify-content-end mt-4 mr-5">
			<h2 id="totalPrice" class="font-weight-bold mr-5">총 <fmt:formatNumber value="${purchaseCardView.purchase.totalPrice}" type="number" />원</h2>
		</div>
		
		<div class="d-flex justify-content-end mt-5 mr-5">
			<button type="button" id="cancelBtn" class="btn btn-secondary" data-purchase-id="${purchaseCardView.purchase.id}">취소하기</button>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 취소하기 버튼
		$('#cancelBtn').on('click', function() {
			let purchaseId = $(this).data('purchase-id');
			
			if (confirm("구매를 취소하시겠습니까?")) {
				$.ajax({
					type:"PUT"
					, url:"/purchase/purchase_cancel"
					, data:{"purchaseId":purchaseId}
					
					, success:function(data) {
						if (data.code == 1) {
							alert("구매가 취소되었습니다");
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
			}
		});
	});
</script>