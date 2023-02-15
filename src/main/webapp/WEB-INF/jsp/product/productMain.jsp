<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="product-main-box">
	<c:forEach var="productCardView" items="${productCardViewList}">
		<div class="product-small-box mt-4">
			<a href="/product/product_detail_view/${productCardView.product.id}">
				<div class="text-center">
					<img src="${productCardView.productPictureList[0].imagePath}" alt="product" width="200" height="200">
				</div>
				<div class="font-weight-bold ml-4 mt-2">${productCardView.product.name}</div>
				<div class="ml-4"><fmt:formatNumber value="${productCardView.product.price}" type="number" />원</div>
			</a>
		</div>
	</c:forEach>
</div>