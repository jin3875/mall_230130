<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table class="table text-center">
	<thead>
		<tr>
			<th>ID</th>
			<th>유저ID</th>
			<th>총 금액</th>
			<th>이름</th>
			<th>전화번호</th>
			<th>비고</th>
			<th>상품 목록</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="purchaseView" items="${purchaseViewList}">
			<tr>
				<td class="align-middle">${purchaseView.purchase.id}</td>
				<td class="align-middle">${purchaseView.purchaseProductViewList[0].user.loginId}</td>
				<td class="align-middle">${purchaseView.purchase.totalPrice}</td>
				<td class="align-middle">${purchaseView.purchase.name}</td>
				<td class="align-middle">${purchaseView.purchase.phoneNumber}</td>
				<td class="align-middle">
					<c:choose>
						<c:when test="${purchaseView.purchase.cancellation eq 1}"><span class="text-danger">취소 신청</span></c:when>
						<c:when test="${purchaseView.purchase.cancellation eq 2}"><span class="text-danger">취소 완료</span></c:when>
						<c:when test="${empty purchaseView.purchase.trackingNumber}"><span class="text-success">배송 전</span></c:when>
					</c:choose>
				</td>
				<td><a href="/admin/admin_detail_list_view?purchaseId=${purchaseView.purchase.id}" class="btn btn-sm btn-outline-info">목록</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>