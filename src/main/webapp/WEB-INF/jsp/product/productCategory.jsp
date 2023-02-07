<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h3 class="ml-3">${category}</h3>

<div class="product-main-box">
	<c:forEach var="productView" items="${productViewList}">
		<div class="product-big-box mt-4">
			<a href="#">
				<div class="text-center">
					<img src="${productView.productPictureList[0].imagePath}" alt="product" width="250" height="250">
				</div>
				<div class="font-weight-bold ml-5 mt-2">${productView.product.name}</div>
				<div class="ml-5"><fmt:formatNumber value="${productView.product.price}" type="number" />원</div>
				<div class="small d-flex ml-5 mt-2">
					<div>option&nbsp;&gt;&gt;</div>
					<div class="col-8">
						<c:forEach var="productDetail" items="${productView.productDetailList}">
							<c:choose>
								<c:when test="${productDetail.amount ne 0}">
									<div>${productDetail.color} - ${productDetail.size}</div>
								</c:when>
								<c:otherwise>
									<div class="d-flex justify-content-between text-secondary">
										<div>${productDetail.color} - ${productDetail.size}</div>
										<div>* SOLD OUT *</div>
									</div>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</div>
			</a>
		</div>
	</c:forEach>
</div>