<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h3 class="font-weight-bold">장바구니</h3>

<c:forEach var="productCardView" items="${productCardViewList}">
	<div class="purchase-box d-flex align-items-center mt-5 mr-5 bg-light">
		<div class="col-3">
			<img src="${productCardView.productPicture.imagePath}" alt="product" width="150" height="150">
		</div>
		<div class="col-5">
			<big class="font-weight-bold">${productCardView.product.name}</big>
			<div class="text-secondary mt-3">
				<div class="col-4 d-flex justify-content-between p-0">
					<span>가격</span>
					<span><fmt:formatNumber value="${productCardView.product.price}" type="number" />원</span>
				</div>
				
				<div class="col-4 d-flex justify-content-between p-0">
					<span>색상</span>
					<span>${productCardView.productDetail.color}</span>
				</div>
				
				<div class="col-4 d-flex justify-content-between p-0">
					<span>사이즈</span>
					<span>${productCardView.productDetail.size}</span>
				</div>
				
				<div class="col-4 d-flex justify-content-between p-0">
					<span>수량</span>
					<span>${productCardView.amount}</span>
				</div>
			</div>
		</div>
		<h4 class="col-3"><fmt:formatNumber value="${productCardView.product.price * productCardView.amount}" type="number" />원</h4>
		<input type="checkbox" class="check col-1" value="${productCardView.wishListId}">
	</div>
</c:forEach>

<div class="d-flex justify-content-between mt-5 mr-5">
	<button type="button" id="deleteBtn" class="btn btn-light">장바구니 삭제</button>
	<button type="button" id="purchaseBtn" class="btn btn-secondary">구매하기</button>
</div>

<script>
	$(document).ready(function() {
		// 장바구니 삭제 버튼
		$('#deleteBtn').on('click', function() {
			let idList = [];
			
			$('.check').each(function() {
				if(this.checked){
					idList.push(this.value);
				}
			});
			
			if (idList == '') {
				alert('상품을 선택해주세요');
				return;
			}
			
			$.ajax({
				type:"DELETE"
				, url:"/purchase/wish_list_delete"
				, data:{"idList":idList}
				
				, success:function(data) {
					if (data.code == 1) {
						alert("삭제되었습니다");
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
		});
		
		// 구매하기 버튼
		$('#purchaseBtn').on('click', function() {
			let idList = [];
			
			$('.check').each(function() {
				if(this.checked){
					idList.push(this.value);
				}
			});
			
			if (idList == '') {
				alert('상품을 선택해주세요');
				return;
			}
			
			location.href="/purchase/purchase_view?wishListId=" + idList;
		});
	});
</script>