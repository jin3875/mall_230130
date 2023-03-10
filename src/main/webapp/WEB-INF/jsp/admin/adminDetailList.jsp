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
			<td class="align-middle">${productCardView.product.id}</td>
			<td class="align-middle">${productCardView.product.category}</td>
			<td class="align-middle">${productCardView.product.name}</td>
			<td class="align-middle"><fmt:formatNumber value="${productCardView.product.price}" type="number" />원</td>
			<td class="align-middle">
				<c:choose>
					<c:when test="${productCardView.product.state eq 1}">
						<span class="text-success">판매 중</span>
					</c:when>
					<c:otherwise>
						<span class="text-danger">판매 대기</span>
					</c:otherwise>
				</c:choose>
			</td>
			<td class="align-middle"><fmt:formatDate value="${productCardView.product.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="align-middle"><fmt:formatDate value="${productCardView.product.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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
		<c:forEach var="productDetail" items="${productCardView.productDetailList}">
			<tr>
				<td class="align-middle">${productDetail.id}</td>
				<td class="align-middle">${productDetail.color}</td>
				<td class="align-middle">${productDetail.size}</td>
				<td class="align-middle">${productDetail.amount}</td>
				<td class="align-middle"><fmt:formatDate value="${productDetail.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td class="align-middle"><fmt:formatDate value="${productDetail.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><a href="/admin/admin_detail_update_view?productId=${productDetail.productId}&detailId=${productDetail.id}" class="btn btn-sm btn-outline-info">수정</a></td>
				<td><button class="delete-btn btn btn-sm btn-outline-danger" data-detail-id="${productDetail.id}">삭제</button></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div class="d-flex justify-content-between mt-5 mx-3">
	<a href="/admin/admin_product_list_view" class="btn btn-light">상품 목록</a>
	<a href="/admin/admin_detail_create_view?productId=${productCardView.product.id}" class="btn btn-secondary">상품 상세 등록</a>
</div>

<script>
	$(document).ready(function() {
		// 삭제 버튼
		$('.delete-btn').on('click', function() {
			let detailId = $(this).data('detail-id');
			
			if (confirm("상세 상품을 삭제하시겠습니까?")) {
				$.ajax({
					type:"DELETE"
					, url:"/admin/admin_detail_delete"
					, data:{"detailId":detailId}
					
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
			}
		});
	});
</script>