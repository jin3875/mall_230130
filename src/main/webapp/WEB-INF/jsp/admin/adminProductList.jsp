<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table class="table text-center">
	<thead>
		<tr>
			<th>ID</th>
			<th>카테고리</th>
			<th>이름</th>
			<th>가격</th>
			<th>판매 여부</th>
			<th>생성일</th>
			<th>수정일</th>
			<th>수정</th>
			<th>삭제</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="product" items="${productList}">
			<tr>
				<td>${product.id}</td>
				<td>${product.category}</td>
				<td><a href="#" class="text-primary">${product.name}</a></td>
				<td>${product.price}</td>
				<td>${product.state}</td>
				<td>${product.createdAt}</td>
				<td>${product.updatedAt}</td>
				<td><button class="edit-btn btn btn-sm btn-info">수정</button></td>
				<td><button class="delete-btn btn btn-sm btn-danger">삭제</button></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<a href="/admin/admin_product_create_view" class="btn btn-secondary">상품 등록</a>