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
		<h3 class="font-weight-bold">후기 목록</h3>
		
		<c:forEach var="purchaseView" items="${purchaseViewList}">
			<c:forEach var="purchaseProductView" items="${purchaseView.purchaseProductViewList}">
				<c:if test="${not empty purchaseProductView.purchaseProduct.star}">
					<div class="purchase-box d-flex align-items-center mt-5 mr-5 bg-light">
						<div class="col-3">
							<img src="${purchaseProductView.productPicture.imagePath}" alt="product" width="150" height="150">
						</div>
						<div class="col-4">
							<big class="font-weight-bold">${purchaseProductView.product.name}</big>
							<div class="mt-3">
								<c:set var="star" value="${purchaseProductView.purchaseProduct.star}" />
								<c:forEach var="count" begin="0" end="4" step="1">
									<c:choose>
										<c:when test="${star > 0}">
											<img src="https://cdn-icons-png.flaticon.com/512/1828/1828961.png" alt="filled-star" width="15" height="15">
										</c:when>
										<c:otherwise>
											<img src="https://cdn-icons-png.flaticon.com/512/1828/1828970.png" alt="empty-star" width="15" height="15">
										</c:otherwise>
									</c:choose>
									<c:set var="star" value="${star - 1}" />
								</c:forEach>
							</div>
							<c:if test="${not empty purchaseProductView.purchaseProduct.review}">
								<div class="mt-3">
									<c:choose>
										<c:when test="${fn:length(purchaseProductView.purchaseProduct.review) > 15}">
											${fn:substring(purchaseProductView.purchaseProduct.review, 0, 15)}...
										</c:when>
										<c:otherwise>
											${purchaseProductView.purchaseProduct.review}
										</c:otherwise>
									</c:choose>
								</div>
							</c:if>
						</div>
						<div class="col-3">
							<c:if test="${not empty purchaseProductView.purchaseProduct.imagePath}">
								<img src="${purchaseProductView.purchaseProduct.imagePath}" alt="review" width="150" height="150">
							</c:if>
						</div>
						<div class="col-2 text-right">
							<button type="button" class="edit-review-btn btn btn-secondary" data-purchase-product-id="${purchaseProductView.purchaseProduct.id}">후기 수정</button>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</c:forEach>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 후기 수정 버튼
		$('.edit-review-btn').on('click', function() {
			let purchaseProductId = $(this).data('purchase-product-id');
			location.href = "/purchase/product_review_update_view/" + purchaseProductId;
		});
	});
</script>