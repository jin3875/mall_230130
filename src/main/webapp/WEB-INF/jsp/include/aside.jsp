<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="recent-list w-100">
	<div class="text-center font-weight-bold">최근 본 상품</div>
	<c:forEach var="recent" items="${recentList}" varStatus="status">
		<c:if test="${status.index < 5}">
			<div class="text-center mt-4">
				<a href="/product/product_detail_view/${recent.productId}"><img src="${recent.imagePath}" width="80" height="80"></a>
			</div>
		</c:if>
	</c:forEach>
</div>