<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="text-center">
	<h2>SEARCH</h2>
	<div class="d-flex justify-content-center align-items-center mt-5 mr-5">
		<div class="col-5 d-flex align-items-center">
			<span class="mr-3">가격대</span>
			<input type="text" id="minPrice" class="form-control col-4" value="${minPrice}">
			<span class="mx-3">~</span>
			<input type="text" id="maxPrice" class="form-control col-4" value="${maxPrice}">
		</div>
		
		<div class="col-3 d-flex align-items-center">
			<span class="mr-3">검색명</span>
			<input type="text" id="keyword2" class="form-control col-8" value="${keyword}">
		</div>
		
		<a href="#" id="searchBtn2"><img src="https://cdn-icons-png.flaticon.com/512/622/622669.png" alt="search" width="20" height="20"></a>
	</div>
	
	<hr class="mt-5">
</div>

<div>
	<div class="product-main-box">
		<c:forEach var="productView" items="${productViewList}">
			<div class="product-small-box mt-5">
				<a href="/product/product_detail_view/${productView.product.id}">
					<div class="text-center">
						<img src="${productView.productPictureList[0].imagePath}" alt="product" width="200" height="200">
					</div>
					<div class="font-weight-bold ml-4 mt-2">${productView.product.name}</div>
					<div class="ml-4"><fmt:formatNumber value="${productView.product.price}" type="number" />원</div>
				</a>
			</div>
		</c:forEach>
		
		<c:if test="${empty productViewList}">
			<h2 class="text-center font-weight-bold mt-5">조건에 해당하는 상품이 없습니다</h2>
		</c:if>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 엔터키 작동
		$("#keyword2").on('keydown', function(e) {
			if (e.keyCode == 13) {
				$('#searchBtn2').trigger('click');
			}
		});
		
		$("#minPrice").on('keydown', function(e) {
			if (e.keyCode == 13) {
				$('#searchBtn2').trigger('click');
			}
		});
		
		$("#maxPrice").on('keydown', function(e) {
			if (e.keyCode == 13) {
				$('#searchBtn2').trigger('click');
			}
		});
		
		// 검색 버튼
		$('#searchBtn2').on('click', function() {
			let keyword = $('#keyword2').val().trim();
			let minPrice = $('#minPrice').val().trim();
			let maxPrice = $('#maxPrice').val().trim();
			
			if (keyword == '') {
				alert('검색어를 입력하세요');
				return;
			}
			
			if (isNaN(minPrice) || isNaN(maxPrice)) {
				alert('가격은 숫자만 가능합니다');
				return;
			}
			
			location.href="/product/product_search_view?keyword=" + keyword + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice;
		});
	});
</script>