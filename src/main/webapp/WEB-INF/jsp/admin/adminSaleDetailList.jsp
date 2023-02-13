<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<table class="table text-center bg-light">
	<thead>
		<tr>
			<th>ID</th>
			<th>유저ID</th>
			<th>총 금액</th>
			<th>구매일</th>
			<th>비고</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td class="align-middle">${purchaseView.purchase.id}</td>
			<td class="align-middle">${purchaseView.purchaseProductViewList[0].user.loginId}</td>
			<td class="align-middle"><fmt:formatNumber value="${purchaseView.purchase.totalPrice}" type="number" />원</td>
			<td class="align-middle"><fmt:formatDate value="${purchaseView.purchase.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="align-middle">
				<c:choose>
					<c:when test="${purchaseView.purchase.cancellation eq 1}"><span class="text-danger">취소 신청</span></c:when>
					<c:when test="${purchaseView.purchase.cancellation eq 2}"><span class="text-danger">취소 완료</span></c:when>
					<c:when test="${empty purchaseView.purchase.trackingNumber}"><span class="text-success">배송 전</span></c:when>
				</c:choose>
			</td>
		</tr>
	</tbody>
</table>

<table class="table text-center mt-5">
	<thead>
		<tr>
			<th>ID</th>
			<th>카테고리</th>
			<th>이름</th>
			<th>색상</th>
			<th>사이즈</th>
			<th>수량</th>
			<th>비고</th>
			<th>상세 정보</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="purchaseProductView" items="${purchaseView.purchaseProductViewList}">
			<tr>
				<td class="align-middle">${purchaseProductView.purchaseProduct.id}</td>
				<td class="align-middle">${purchaseProductView.product.category}</td>
				<td class="align-middle">${purchaseProductView.product.name}</td>
				<td class="align-middle">${purchaseProductView.productDetail.color}</td>
				<td class="align-middle">${purchaseProductView.productDetail.size}</td>
				<td class="align-middle">${purchaseProductView.purchaseProduct.amount}</td>
				<td class="align-middle">
					<c:choose>
						<c:when test="${purchaseProductView.purchaseProduct.completion eq 1}"><span class="text-success">구매 확정</span></c:when>
						<c:when test="${purchaseProductView.purchaseProduct.refund eq 1}"><span class="text-danger">환불 중</span></c:when>
						<c:when test="${purchaseProductView.purchaseProduct.refund eq 2}"><span class="text-danger">환불 완료</span></c:when>
						<c:when test="${purchaseProductView.purchaseProduct.exchange eq 1}"><span class="text-primary">교환 중</span></c:when>
						<c:when test="${purchaseProductView.purchaseProduct.exchange eq 2}"><span class="text-primary">교환 완료</span></c:when>
					</c:choose>
				</td>
				<td class="align-middle"><a href="/admin/admin_sale_detail_view?purchaseId=${purchaseProductView.purchaseProduct.purchaseId}&purchaseProductId=${purchaseProductView.purchaseProduct.id}" class="btn btn-sm btn-outline-success">정보</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div class="mt-5 mx-3">
	<a href="/admin/admin_sale_list_view" class="btn btn-light">판매 목록</a>
</div>