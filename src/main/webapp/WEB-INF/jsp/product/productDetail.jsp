<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="userCheck" class="d-none">${userId}</div>
<div class="d-flex ml-3">
	<div class="product-img-box">
		<ul class="slider">
			<c:forEach var="productPicture" items="${productCardView.productPictureList}">
				<li><img src="${productPicture.imagePath}" alt="product" width="400" height="400"></li>
			</c:forEach>
		</ul>
	</div>
	
	<div class="col-4 mt-3 ml-5">
		<h2 class="font-weight-bold">${productCardView.product.name}</h2>
		<div>
			<div class="d-flex justify-content-between align-items-center mt-5">
				<span>가격</span>
				<h5><fmt:formatNumber value="${productCardView.product.price}" type="number" />원</h5>
			</div>
			
			<div class="d-flex justify-content-between align-items-center mt-3">
				<span>옵션</span>
				<select id="colorAndSize" class="form-control col-8">
					<option value="" selected>-- 색상 / 사이즈 --</option>
					<c:forEach var="productDetail" items="${productCardView.productDetailList}">
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
			
			<c:if test="${productCardView.product.price < 30000}">
				<div class="d-flex justify-content-between mt-4">
					<div>
						<span>배송비</span><br>
						<small class="text-secondary">* 30,000원 이상 구매시 무료 *</small>
					</div>
					<h5>3,000원</h5>
				</div>
			</c:if>
		</div>
		
		<div class="d-flex justify-content-between mt-5">
			<button type="button" id="wishListBtn" class="btn btn-light" data-product-id="${productCardView.product.id}">장바구니</button>
			<button type="button" id="purchaseBtn" class="btn btn-secondary" data-product-id="${productCardView.product.id}">구매하기</button>
		</div>
	</div>
</div>

<div class="mt-5 text-center">
	<hr>
	<h3 class="font-weight-bold my-5">Info</h3>
	<c:forEach var="productPicture" items="${productCardView.productPictureList}">
		<div class="mt-5">
			<img src="${productPicture.imagePath}" alt="product" width="400" height="400">
		</div>
	</c:forEach>
	<div class="mt-5">${productCardView.product.detail}</div>
</div>

<div class="mt-5">
	<hr>
	<h3 class="text-center font-weight-bold my-5">Review</h3>
	<c:forEach var="purchaseProductCardView" items="${purchaseProductCardViewList}">
		<c:if test="${not empty purchaseProductCardView.purchaseProduct.star}">
			<div class="input-big-box d-flex justify-content-between align-items-center mt-3 p-3 border border-light rounded">
				<div>
					<div class="font-weight-bold">${purchaseProductCardView.user.name}</div>
					<div class="small text-secondary mt-2">* ${purchaseProductCardView.productDetailCardView.productDetail.color} / ${purchaseProductCardView.productDetailCardView.productDetail.size} *</div>
					<div class="mt-2">
						<c:set var="star" value="${purchaseProductCardView.purchaseProduct.star}" />
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
					<div class="mt-3">${purchaseProductCardView.purchaseProduct.review}</div>
					<div class="small text-secondary mt-3">상품 구매일 : <fmt:formatDate value="${purchaseProductCardView.purchaseProduct.createdAt}" pattern="yyyy/MM/dd" /></div>
					<div class="small text-secondary">후기 작성일 : <fmt:formatDate value="${purchaseProductCardView.purchaseProduct.updatedAt}" pattern="yyyy/MM/dd" /></div>
				</div>
				<c:if test="${not empty purchaseProductCardView.purchaseProduct.imagePath}">
					<div class="ml-5">
						<img src="${purchaseProductCardView.purchaseProduct.imagePath}" alt="review" width="200" height="200">
					</div>
				</c:if>
			</div>
		</c:if>
	</c:forEach>
</div>

<script>
	$(document).ready(function() {
		// 사진 슬라이드
		$('.slider').bxSlider();
		
		// 장바구니 버튼
		$('#wishListBtn').on('click', function() {
			let productId = $(this).data('product-id');
			let colorAndSize = $('#colorAndSize').val();
			let amount = $('#amount').val();
			
			if (colorAndSize == '') {
				alert('옵션을 선택하세요');
				return;
			}
			
			if (amount == '') {
				alert('수량을 선택하세요');
				return;
			}
			
			let color = colorAndSize.split('/')[0].trim();
			let size = colorAndSize.split('/')[1].trim();
			
			if ($('#userCheck').text() == '') {
				if (confirm("로그인 후 이용 가능합니다")) {
					location.href="/user/sign_in_view";
				}
			} else {
				$.ajax({
					type:"POST"
					, url:"/purchase/wish_list_create"
					, data:{"productId":productId, "color":color, "size":size, "amount":amount}
					
					, success:function(data) {
						if (data.code == 1) {
							if (confirm("장바구니 추가가 완료되었습니다. 장바구니 목록으로 이동하시겠습니까?")) {
								location.href="/purchase/wish_list_view";
							} else {
								location.reload();
							}
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
		
		// 구매하기 버튼
		$('#purchaseBtn').on('click', function() {
			let productId = $(this).data('product-id');
			let colorAndSize = $('#colorAndSize').val();
			let amount = $('#amount').val();
			
			if (colorAndSize == '') {
				alert('옵션을 선택하세요');
				return;
			}
			
			if (amount == '') {
				alert('수량을 선택하세요');
				return;
			}
			
			let color = colorAndSize.split('/')[0].trim();
			let size = colorAndSize.split('/')[1].trim();
			
			if ($('#userCheck').text() == '') {
				if (confirm("로그인 후 이용 가능합니다")) {
					location.href="/user/sign_in_view";
				}
			} else {
				location.href="/purchase/purchase_view?productId=" + productId + "&color=" + color + "&size=" + size + "&amount=" + amount;
			}
		});
	});
</script>