<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<table class="table text-center bg-light">
	<thead>
		<tr>
			<th>ID</th>
			<th>카테고리</th>
			<th>이름</th>
			<th>가격</th>
			<th>판매 여부</th>
			<th>생성일</th>
			<th>수정일</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td class="align-middle">${product.id}</td>
			<td class="align-middle">${product.category}</td>
			<td class="align-middle">${product.name}</td>
			<td class="align-middle">${product.price}원</td>
			<td class="align-middle">
				<c:choose>
					<c:when test="${product.state eq 1}">
						<span class="text-success">판매 중</span>
					</c:when>
					<c:otherwise>
						<span class="text-danger">판매 대기</span>
					</c:otherwise>
				</c:choose>
			</td>
			<td class="align-middle"><fmt:formatDate value="${product.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="align-middle"><fmt:formatDate value="${product.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
	</tbody>
</table>

<table class="table text-center mt-5">
	<thead>
		<tr>
			<th>ID</th>
			<th>색상</th>
			<th>사이즈</th>
			<th>재고 수량</th>
			<th>생성일</th>
			<th>수정일</th>
			<th>수정</th>
			<th>삭제</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="productDetail" items="${productDetailList}">
			<tr>
				<td class="align-middle">${productDetail.id}</td>
				<td class="align-middle">${productDetail.color}</td>
				<td class="align-middle">${productDetail.size}</td>
				<td class="align-middle">${productDetail.amount}</td>
				<td class="align-middle"><fmt:formatDate value="${productDetail.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td class="align-middle"><fmt:formatDate value="${productDetail.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><a href="#" class="btn btn-sm btn-info">수정</a></td>
				<td><button class="delete-btn btn btn-sm btn-danger">삭제</button></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div class="d-flex justify-content-between mt-5 mx-3">
	<a href="/admin/admin_product_list_view" class="btn btn-light">상품 목록</a>
	<a href="/admin/admin_detail_create_view?productId=${product.id}" class="btn btn-secondary">상품 상세 등록</a>
</div>