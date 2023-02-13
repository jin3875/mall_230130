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

<table class="table text-center bg-light mt-5">
	<thead>
		<tr>
			<th>ID</th>
			<th>카테고리</th>
			<th>이름</th>
			<th>색상</th>
			<th>사이즈</th>
			<th>수량</th>
			<th>비고</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td class="align-middle">${purchaseView.purchaseProductViewList[0].purchaseProduct.id}</td>
			<td class="align-middle">${purchaseView.purchaseProductViewList[0].product.category}</td>
			<td class="align-middle">${purchaseView.purchaseProductViewList[0].product.name}</td>
			<td class="align-middle">${purchaseView.purchaseProductViewList[0].productDetail.color}</td>
			<td class="align-middle">${purchaseView.purchaseProductViewList[0].productDetail.size}</td>
			<td class="align-middle">${purchaseView.purchaseProductViewList[0].purchaseProduct.amount}</td>
			<td class="align-middle">
				<c:choose>
					<c:when test="${purchaseView.purchaseProductViewList[0].purchaseProduct.completion eq 1}"><span class="text-success">구매 확정</span></c:when>
					<c:when test="${purchaseView.purchaseProductViewList[0].purchaseProduct.refund eq 1}"><span class="text-danger">환불 중</span></c:when>
					<c:when test="${purchaseView.purchaseProductViewList[0].purchaseProduct.refund eq 2}"><span class="text-danger">환불 완료</span></c:when>
					<c:when test="${purchaseView.purchaseProductViewList[0].purchaseProduct.exchange eq 1}"><span class="text-primary">교환 중</span></c:when>
					<c:when test="${purchaseView.purchaseProductViewList[0].purchaseProduct.exchange eq 2}"><span class="text-primary">교환 완료</span></c:when>
				</c:choose>
			</td>
		</tr>
	</tbody>
</table>

<div class="input-big-box d-flex justify-content-between mt-5">
	<div class="col-4 d-flex justify-content-between align-items-center pl-0 pr-4">
		<span>환불 여부</span>
		<select id="refund" class="form-control col-7">
			<option value="0" <c:if test="${purchaseView.purchaseProductViewList[0].purchaseProduct.refund eq 0}">selected</c:if>>해당 없음</option>
			<option value="1" <c:if test="${purchaseView.purchaseProductViewList[0].purchaseProduct.refund eq 1}">selected</c:if>>환불 신청</option>
			<option value="2" <c:if test="${purchaseView.purchaseProductViewList[0].purchaseProduct.refund eq 2}">selected</c:if>>환불 완료</option>
		</select>
	</div>
	
	<div class="col-4 d-flex justify-content-between align-items-center px-4">
		<span>취소 여부</span>
		<select id="exchange" class="form-control col-7">
			<option value="0" <c:if test="${purchaseView.purchaseProductViewList[0].purchaseProduct.exchange eq 0}">selected</c:if>>해당 없음</option>
			<option value="1" <c:if test="${purchaseView.purchaseProductViewList[0].purchaseProduct.exchange eq 1}">selected</c:if>>교환 신청</option>
			<option value="2" <c:if test="${purchaseView.purchaseProductViewList[0].purchaseProduct.exchange eq 2}">selected</c:if>>교환 완료</option>
		</select>
	</div>
	
	<div class="col-4 d-flex justify-content-between align-items-center pl-4 pr-0">
		<span>확정 여부</span>
		<select id="completion" class="form-control col-7">
			<option value="0" <c:if test="${purchaseView.purchaseProductViewList[0].purchaseProduct.completion eq 0}">selected</c:if>>해당 없음</option>
			<option value="1" <c:if test="${purchaseView.purchaseProductViewList[0].purchaseProduct.completion eq 1}">selected</c:if>>구매 확정</option>
		</select>
	</div>
</div>

<div class="input-big-box d-flex justify-content-between mt-5">
	<a href="/admin/admin_sale_detail_list_view?purchaseId=${purchaseView.purchase.id}" class="btn btn-light">판매 상세 목록</a>
	<button id="editBtn" class="btn btn-secondary" data-purchase-product-id="${purchaseView.purchaseProductViewList[0].purchaseProduct.id}">수정하기</button>
</div>