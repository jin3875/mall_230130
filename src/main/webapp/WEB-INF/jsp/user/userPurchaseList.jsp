<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
		<h3 class="font-weight-bold">구매 목록</h3>
		
		<c:forEach var="purchaseView" items="${purchaseViewList}">
			<c:choose>
				<c:when test="${empty purchaseView.purchase.trackingNumber}">
					<div class="purchase-box d-flex align-items-center mt-5 mr-5 bg-light">
						<div class="col-3">
							<img src="${purchaseView.purchaseProductViewList[0].productPicture.imagePath}" alt="product" width="150" height="150">
						</div>
						<div class="col-5">
							<big class="font-weight-bold">${purchaseView.purchaseProductViewList[0].product.name}</big>
							<br>
							<c:if test="${fn:length(purchaseView.purchaseProductViewList) > 1}">
								<small>( + 상품 ${fn:length(purchaseView.purchaseProductViewList) - 1}개 )</small>
							</c:if>
							<div class="text-secondary mt-3">
								<div class="col-4 d-flex justify-content-between p-0">
									<span>가격</span>
									<span><fmt:formatNumber value="${purchaseView.purchaseProductViewList[0].product.price}" type="number" />원</span>
								</div>
								
								<div class="col-4 d-flex justify-content-between p-0">
									<span>색상</span>
									<span>${purchaseView.purchaseProductViewList[0].productDetail.color}</span>
								</div>
								
								<div class="col-4 d-flex justify-content-between p-0">
									<span>사이즈</span>
									<span>${purchaseView.purchaseProductViewList[0].productDetail.size}</span>
								</div>
								
								<div class="col-4 d-flex justify-content-between p-0">
									<span>수량</span>
									<span>${purchaseView.purchaseProductViewList[0].purchaseProduct.amount}</span>
								</div>
							</div>
						</div>
						<div class="col-2">
							<div>합계</div>
							<h4><fmt:formatNumber value="${purchaseView.purchase.totalPrice}" type="number" />원</h4>
						</div>
						<c:choose>
							<c:when test="${purchaseView.purchase.cancellation eq 0}">
								<div class="col-2 text-right">
									<button type="button" class="cancel-btn btn btn-outline-secondary" data-purchase-id="${purchaseView.purchase.id}">구매 취소</button>
								</div>
							</c:when>
							<c:when test="${purchaseView.purchase.cancellation eq 1}">
								<div class="col-2 text-right">
									<div class="btn btn-secondary disabled">취소 중</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="col-2 text-right">
									<div class="btn btn-secondary disabled">취소 완료</div>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</c:when>
				<c:otherwise>
					<c:forEach var="purchaseProductView" items="${purchaseView.purchaseProductViewList}">
						<div class="purchase-box d-flex align-items-center mt-5 mr-5 bg-light">
							<div class="col-3">
								<img src="${purchaseProductView.productPicture.imagePath}" alt="product" width="150" height="150">
							</div>
							<div class="col-5">
								<big class="font-weight-bold">${purchaseProductView.product.name}</big>
								<div class="text-secondary mt-3">
									<div class="col-4 d-flex justify-content-between p-0">
										<span>가격</span>
										<span><fmt:formatNumber value="${purchaseProductView.product.price}" type="number" />원</span>
									</div>
									
									<div class="col-4 d-flex justify-content-between p-0">
										<span>색상</span>
										<span>${purchaseProductView.productDetail.color}</span>
									</div>
									
									<div class="col-4 d-flex justify-content-between p-0">
										<span>사이즈</span>
										<span>${purchaseProductView.productDetail.size}</span>
									</div>
									
									<div class="col-4 d-flex justify-content-between p-0">
										<span>수량</span>
										<span>${purchaseProductView.purchaseProduct.amount}</span>
									</div>
								</div>
							</div>
							<h4 class="col-2"><fmt:formatNumber value="${purchaseProductView.product.price * purchaseProductView.purchaseProduct.amount}" type="number" />원</h4>
							<c:choose>
								<%-- 구매 확정 --%>
								<c:when test="${purchaseProductView.purchaseProduct.completion eq 1}">
									<c:choose>
										<c:when test="${empty purchaseProductView.purchaseProduct.star}">
											<div class="col-2 text-right">
												<button type="button" class="add-review-btn btn btn-outline-secondary" data-purchase-product-id="${purchaseProductView.purchaseProduct.id}">후기 작성</button>
											</div>
										</c:when>
										<c:otherwise>
											<div class="col-2 text-right">
												<button type="button" class="edit-review-btn btn btn-secondary" data-purchase-product-id="${purchaseProductView.purchaseProduct.id}">후기 수정</button>
											</div>
										</c:otherwise>
									</c:choose>
								</c:when>
								
								<%-- 환불 --%>
								<c:when test="${purchaseProductView.purchaseProduct.refund eq 1}">
									<div class="col-2 text-right">
										<div class="btn btn-secondary disabled">환불 중</div>
									</div>
								</c:when>
								
								<c:when test="${purchaseProductView.purchaseProduct.refund eq 2}">
									<div class="col-2 text-right">
										<div class="btn btn-secondary disabled">환불 완료</div>
									</div>
								</c:when>
								
								<%-- 교환 --%>
								<c:when test="${purchaseProductView.purchaseProduct.exchange eq 1}">
									<div class="col-2 text-right">
										<div class="btn btn-secondary disabled">교환 중</div>
										<button type="button" class="refund-btn btn btn-outline-secondary mt-2" data-purchase-product-id="${purchaseProductView.purchaseProduct.id}">환불 신청</button>
										<button type="button" class="exchange-btn btn btn-outline-secondary mt-2" data-purchase-product-id="${purchaseProductView.purchaseProduct.id}">교환 신청</button>
										<button type="button" class="complete-btn btn btn-outline-secondary mt-2" data-purchase-product-id="${purchaseProductView.purchaseProduct.id}">구매 확정</button>
									</div>
								</c:when>
								
								<c:when test="${purchaseProductView.purchaseProduct.exchange eq 2}">
									<div class="col-2 text-right">
										<div class="btn btn-secondary disabled">교환 완료</div>
										<button type="button" class="refund-btn btn btn-outline-secondary mt-2" data-purchase-product-id="${purchaseProductView.purchaseProduct.id}">환불 신청</button>
										<button type="button" class="exchange-btn btn btn-outline-secondary mt-2" data-purchase-product-id="${purchaseProductView.purchaseProduct.id}">교환 신청</button>
										<button type="button" class="complete-btn btn btn-outline-secondary mt-2" data-purchase-product-id="${purchaseProductView.purchaseProduct.id}">구매 확정</button>
									</div>
								</c:when>
								
								<%-- 초기 세팅 --%>
								<c:otherwise>
									<div class="col-2 text-right">
										<button type="button" class="btn btn-outline-secondary">배송 조회</button>
										<button type="button" class="refund-btn btn btn-outline-secondary mt-2" data-purchase-product-id="${purchaseProductView.purchaseProduct.id}">환불 신청</button>
										<button type="button" class="exchange-btn btn btn-outline-secondary mt-2" data-purchase-product-id="${purchaseProductView.purchaseProduct.id}">교환 신청</button>
										<button type="button" class="complete-btn btn btn-outline-secondary mt-2" data-purchase-product-id="${purchaseProductView.purchaseProduct.id}">구매 확정</button>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 구매 취소 버튼
		$('.cancel-btn').on('click', function() {
			let purchaseId = $(this).data('purchase-id');
			location.href = "/purchase/purchase_cancel_view/" + purchaseId;
		});
		
		// 환불 신청 버튼
		$('.refund-btn').on('click', function() {
			let purchaseProductId = $(this).data('purchase-product-id');
			location.href = "/purchase/product_refund_view/" + purchaseProductId;
		});
		
		// 교환 신청 버튼
		$('.exchange-btn').on('click', function() {
			let purchaseProductId = $(this).data('purchase-product-id');
			location.href = "/purchase/product_exchange_view/" + purchaseProductId;
		});
		
		// 구매 확정 버튼
		$('.complete-btn').on('click', function() {
			let purchaseProductId = $(this).data('purchase-product-id');
			
			if (confirm("구매를 확정하시겠습니까?")) {
				$.ajax({
					type:"PUT"
					, url:"/purchase/product_complete"
					, data:{"purchaseProductId":purchaseProductId}
					
					, success:function(data) {
						if (data.code == 1) {
							alert("구매 확정이 완료되었습니다");
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
			}
		});
		
		// 후기 작성 버튼
		$('.add-review-btn').on('click', function() {
			let purchaseProductId = $(this).data('purchase-product-id');
			location.href = "/purchase/product_review_create_view/" + purchaseProductId;
		});
		
		// 후기 수정 버튼
		$('.edit-review-btn').on('click', function() {
			let purchaseProductId = $(this).data('purchase-product-id');
			location.href = "/purchase/product_review_update_view/" + purchaseProductId;
		});
	});
</script>