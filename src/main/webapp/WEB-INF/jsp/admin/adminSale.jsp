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

<div class="input-big-box d-flex justify-content-between mt-5">
	<div class="input-small-box">
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>이름</span>
			<div class="form-control col-8 bg-light">${purchaseView.purchase.name}</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>전화번호</span>
			<div class="form-control col-8 bg-light">${purchaseView.purchase.phoneNumber}</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>우편번호</span>
			<div class="form-control col-8 bg-light">${purchaseView.purchase.postcode}</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>주소</span>
			<div class="form-control col-8 bg-light">${purchaseView.purchase.address}</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>상세 주소</span>
			<div class="form-control col-8 bg-light">${purchaseView.purchase.detailAddress}</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>배송 메세지</span>
			<div class="form-control col-8 bg-light">${purchaseView.purchase.message}&nbsp;</div>
		</div>
	</div>
	
	<div class="input-small-box">
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>택배회사</span>
			<input type="text" id="courier" class="form-control col-8" value="${purchaseView.purchase.courier}">
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>송장번호</span>
			<input type="text" id="trackingNumber" class="form-control col-8" value="${purchaseView.purchase.trackingNumber}">
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>취소 여부</span>
			<select id="cancellation" class="form-control col-8">
				<option value="0" <c:if test="${purchaseView.purchase.cancellation eq 0}">selected</c:if>>해당 없음</option>
				<option value="1" <c:if test="${purchaseView.purchase.cancellation eq 1}">selected</c:if>>취소 신청</option>
				<option value="2" <c:if test="${purchaseView.purchase.cancellation eq 2}">selected</c:if>>취소 완료</option>
			</select>
		</div>
	</div>
</div>

<div class="input-big-box d-flex justify-content-between mt-5">
	<a href="/admin/admin_sale_list_view" class="btn btn-light">판매 목록</a>
	<button id="editBtn" class="btn btn-secondary" data-purchase-id="${purchaseView.purchase.id}">수정하기</button>
</div>