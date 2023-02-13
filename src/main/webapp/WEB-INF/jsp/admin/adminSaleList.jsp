<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table class="table text-center">
	<thead>
		<tr>
			<th>구매ID</th>
			<th>구매상세ID</th>
			<th>카테고리</th>
			<th>이름</th>
			<th>유저ID</th>
			<th>색상</th>
			<th>사이즈</th>
			<th>수량</th>
			<th>현재 상태</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="purchaseView" items="${purchaseViewList}">
			<c:choose>
				<c:when test="${empty purchaseView.purchase.trackingNumber}">
					<tr>
						<td class="align-middle">${purchaseView.purchase.id}</td>
						<td class="align-middle"></td>
						<td class="align-middle"></td>
						<td class="align-middle">${purchaseView.purchaseProductViewList[0].product.name} 등</td>
						<td class="align-middle">${purchaseView.purchaseProductViewList[0].user.loginId}</td>
						<td class="align-middle"></td>
						<td class="align-middle"></td>
						<td class="align-middle"></td>
						<td class="align-middle"><a href="" class="text-primary">
							<c:choose>
								<c:when test="${purchaseView.purchase.cancellation eq 0}">구매 완료</c:when>
								<c:when test="${purchaseView.purchase.cancellation eq 1}">취소 신청</c:when>
								<c:otherwise>취소 완료</c:otherwise>
							</c:choose>
						</a></td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="purchaseProductView" items="${purchaseView.purchaseProductViewList}">
						<tr>
							<td class="align-middle">${purchaseProductView.purchaseProduct.purchaseId}</td>
							<td class="align-middle">${purchaseProductView.purchaseProduct.id}</td>
							<td class="align-middle">${purchaseProductView.product.category}</td>
							<td class="align-middle">${purchaseProductView.product.name}</td>
							<td class="align-middle">${purchaseProductView.user.loginId}</td>
							<td class="align-middle">${purchaseProductView.productDetail.color}</td>
							<td class="align-middle">${purchaseProductView.productDetail.size}</td>
							<td class="align-middle">${purchaseProductView.purchaseProduct.amount}</td>
							<td class="align-middle"><a href="" class="text-primary">
								<c:choose>
									<c:when test="${purchaseProductView.purchaseProduct.completion eq 1}">구매 확정</c:when>
									<c:when test="${purchaseProductView.purchaseProduct.refund eq 1}">환불 신청</c:when>
									<c:when test="${purchaseProductView.purchaseProduct.refund eq 2}">환불 완료</c:when>
									<c:when test="${purchaseProductView.purchaseProduct.exchange eq 1}">교환 신청</c:when>
									<c:when test="${purchaseProductView.purchaseProduct.exchange eq 2}">교환 완료</c:when>
								</c:choose>
							</a></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</tbody>
</table>