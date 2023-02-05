<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
				<td class="align-middle">${product.id}</td>
				<td class="align-middle">${product.category}</td>
				<td class="align-middle"><a href="#" class="text-primary">${product.name}</a></td>
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
				<td><a href="/admin/admin_product_update_view?productId=${product.id}" class="btn btn-sm btn-info">수정</a></td>
				<td><button class="delete-btn btn btn-sm btn-danger" data-product-id="${product.id}">삭제</button></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<a href="/admin/admin_product_create_view" class="btn btn-secondary mt-4">상품 등록</a>

<script>
	$(document).ready(function() {
		// 삭제 버튼
		$('.delete-btn').on('click', function() {
			let productId = $(this).data('product-id');
			
			$.ajax({
				type:"DELETE"
				, url:"/admin/admin_product_delete"
				, data:{"productId":productId}
				
				, success:function(data) {
					if (data.code == 1) {
						alert("삭제가 완료되었습니다");
						location.reload();
					} else {
						alert(data.errorMessage);
					}
				}
				, error:function(jqXHR, textStatus, errorThrown) {
					let errorMsg = jqXHR.responseJSON.status;
					alert(errorMsg + ":" + textStatus);
				}
			});
		});
	});
</script>