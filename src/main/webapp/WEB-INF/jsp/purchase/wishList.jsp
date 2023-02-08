<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h3 class="font-weight-bold">장바구니</h3>

<c:forEach var="purchaseProductView" items="${purchaseProductViewList}">
	<div class="purchase-box d-flex align-items-center mt-5 mr-5 bg-light">
		<div class="col-3">
			<img src="${purchaseProductView.productPicture.imagePath}" alt="product" width="150" height="150">
		</div>
		<div class="col-5">
			<big class="font-weight-bold">${purchaseProductView.product.name}</big>
			<div class="mt-3">
				<div class="col-6 d-flex justify-content-between p-0">
					<span>가격</span>
					<span><fmt:formatNumber value="${purchaseProductView.product.price}" type="number" />원</span>
				</div>
				
				<div class="col-6 d-flex justify-content-between p-0">
					<span>색상</span>
					<span>${purchaseProductView.productDetail.color}</span>
				</div>
				
				<div class="col-6 d-flex justify-content-between p-0">
					<span>사이즈</span>
					<span>${purchaseProductView.productDetail.size}</span>
				</div>
				
				<div class="col-6 d-flex justify-content-between p-0">
					<span>수량</span>
					<span>${purchaseProductView.amount}</span>
				</div>
			</div>
		</div>
		<h4 class="col-3">총 <fmt:formatNumber value="${purchaseProductView.product.price * purchaseProductView.amount}" type="number" />원</h4>
		<input type="checkbox" class="col-1">
	</div>
</c:forEach>

<div class="d-flex justify-content-between mt-5 mr-5">
	<button type="button" id="" class="btn btn-light">장바구니 삭제</button>
	<button type="button" id="" class="btn btn-secondary">구매하기</button>
</div>