<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="d-flex ml-3">
	<div><img src="${productView.productPictureList[0].imagePath}" width="400" height="400"></div>
	<div class="col-4 mt-3 ml-5">
		<h2 class="font-weight-bold">${productView.product.name}</h2>
		<div>
			<div class="d-flex justify-content-between align-items-center mt-5">
				<span>가격</span>
				<span><fmt:formatNumber value="${productView.product.price}" type="number" />원</span>
			</div>
			
			<div class="d-flex justify-content-between align-items-center mt-4">
				<span>옵션</span>
				<select id="colorAndSize" class="form-control col-8">
					<option value="" selected>-- 색상 / 사이즈 --</option>
					<c:forEach var="productDetail" items="${productView.productDetailList}">
						<c:choose>
								<c:when test="${productDetail.amount ne 0}">
									<option>${productDetail.color} / ${productDetail.size}</option>
								</c:when>
								<c:otherwise>
									<option disabled class="text-secondary">${productDetail.color} / ${productDetail.size} &nbsp;&nbsp;&nbsp; * SOLD OUT *</option>
								</c:otherwise>
							</c:choose>
					</c:forEach>
				</select>
			</div>
			
			<div class="d-flex justify-content-between align-items-center mt-3">
				<span>수량</span>
				<select id="amount" class="form-control col-8">
					<option value="" selected>-- 수량 (최대 10) --</option>
					<c:forEach begin="1" end="10" step="1" var="count">
						<option>${count}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="d-flex justify-content-between mt-5">
			<button type="button" id="wishListButton" class="btn btn-light">장바구니</button>
			<button type="button" id="purchaseButton" class="btn btn-secondary">구매하기</button>
		</div>
	</div>
</div>

<div class="mt-5 text-center">
	<hr>
	<h3 class="font-weight-bold my-5">Info</h3>
	<c:forEach var="productPicture" items="${productView.productPictureList}">
		<div class="mt-5">
			<img src="${productPicture.imagePath}" width="400" height="400">
		</div>
	</c:forEach>
	<div class="mt-5">${productView.product.detail}</div>
</div>

<div class="d-flex justify-content-center mt-5">
	<div class="col-8 bg-secondary">
		-- 구매하기 기능 만든 후 --
		<div>별점</div>
		<div>리뷰</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 장바구니 버튼
		$('#wishListButton').on('click', function() {
			
		});
		
		// 구매하기 버튼
		$('#purchaseButton').on('click', function() {
			let colorAndSize = $('#colorAndSize').val();
			let amount = $('#amount').val();
			
			let color = colorAndSize.split('/')[0].trim();
			let size = colorAndSize.split('/')[1].trim();
			
			if (colorAndSize == '') {
				alert('옵션을 선택하세요');
				return;
			}
			
			if (amount == '') {
				alert('수량을 선택하세요');
				return;
			}
			
			
		});
	});
</script>