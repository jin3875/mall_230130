<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table class="table text-center">
	<thead>
		<tr>
			<th>ID</th>
			<th>카테고리</th>
			<th>이름</th>
			<th>유저ID</th>
			<th>별점</th>
			<th>후기</th>
			<th>사진</th>
			<th>삭제</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="purchaseView" items="${purchaseViewList}">
			<c:forEach var="purchaseProductView" items="${purchaseView.purchaseProductViewList}">
				<c:if test="${not empty purchaseProductView.purchaseProduct.star}">
					<tr>
						<td class="align-middle">${purchaseProductView.purchaseProduct.id}</td>
						<td class="align-middle">${purchaseProductView.product.category}</td>
						<td class="align-middle">${purchaseProductView.product.name}</td>
						<td class="align-middle">${purchaseProductView.user.loginId}</td>
						<td class="align-middle">${purchaseProductView.purchaseProduct.star}</td>
						<td class="align-middle col-5">${purchaseProductView.purchaseProduct.review}</td>
						<td class="align-middle">
							<c:if test="${not empty purchaseProductView.purchaseProduct.imagePath}">
								<a href="${purchaseProductView.purchaseProduct.imagePath}" target="_blank" class="text-primary">사진</a>
							</c:if>
						</td>
						<td class="align-middle"><button class="delete-btn btn btn-sm btn-outline-danger" data-purchase-product-id="${purchaseProductView.purchaseProduct.id}">삭제</button></td>
					</tr>
				</c:if>
			</c:forEach>
		</c:forEach>
	</tbody>
</table>

<script>
	$(document).ready(function() {
		// 삭제 버튼
		$('.delete-btn').on('click', function() {
			let purchaseProductId = $(this).data('purchase-product-id');
			
			if (confirm("후기를 삭제하시겠습니까?")) {
				$.ajax({
					type:"PUT"
					, url:"/admin/admin_review_delete"
					, data:{"purchaseProductId":purchaseProductId}
					
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