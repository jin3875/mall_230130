<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="admin-box d-flex justify-content-between">
	<div class="admin-small-box">
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>카테고리</span>
			<div class="form-control col-8 bg-light">${product.category}</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>이름</span>
			<div class="form-control col-8 bg-light">${product.name}</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>가격</span>
			<div class="form-control col-8 bg-light">${product.price}</div>
		</div>
	</div>
	
	<div class="admin-small-box">
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>색상</span>
			<input type="text" id="color" class="form-control col-8">
		</div>
		
		<div>
			<div id="colorCheckEmpty" class="text-center small text-danger d-none">색상을 입력하세요.</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>사이즈</span>
			<input type="text" id="size" class="form-control col-8">
		</div>
		
		<div>
			<div id="sizeCheckEmpty" class="text-center ml-3 small text-danger d-none">사이즈를 입력하세요.</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>재고 수량</span>
			<input type="text" id="amount" class="form-control col-8">
		</div>
		
		<div>
			<div id="amountCheckEmpty" class="text-center ml-4 small text-danger d-none">재고 수량을 입력하세요.</div>
			<div id="amountCheckValidation" class="text-center small text-danger d-none">숫자만 가능합니다.</div>
		</div>
	</div>
</div>

<div class="admin-box d-flex justify-content-between mt-5">
	<a href="/admin/admin_detail_list_view?productId=${product.id}" class="btn btn-light">상품 상세 목록</a>
	<button id="createBtn" class="btn btn-secondary" data-product-id="${product.id}">상품 상세 등록</button>
</div>

<script>
	$(document).ready(function() {
		// 재고 수량 체크
		$('#amount').on('change', function() {
			$('#amountCheckEmpty').addClass('d-none');
			$('#amountCheckValidation').addClass('d-none');
			
			let amount = $('#amount').val().trim();
			
			if (isNaN(amount)) {
				$('#amountCheckValidation').removeClass('d-none');
				return;
			}
		});
		
		// 상품 상세 등록 버튼
		$('#createBtn').on('click', function() {
			$('#colorCheckEmpty').addClass('d-none');
			$('#sizeCheckEmpty').addClass('d-none');
			$('#amountCheckEmpty').addClass('d-none');
			
			let productId = $(this).data('product-id');
			let color = $('#color').val().trim();
			let size = $('#size').val().trim();
			let amount = $('#amount').val().trim();
			
			if (color == '') {
				$('#colorCheckEmpty').removeClass('d-none');
				$('#color').focus();
				return;
			}
			
			if (size == '') {
				$('#sizeCheckEmpty').removeClass('d-none');
				$('#size').focus();
				return;
			}
			
			if (amount == '') {
				$('#amountCheckEmpty').removeClass('d-none');
				$('#amount').focus();
				return;
			}
			
			if ($('#amountCheckValidation').hasClass('d-none') == false) {
				$('#amount').focus();
				return;
			}
			
			$.ajax({
				type:"POST"
				, url:"/admin/admin_detail_create"
				, data:{"productId":productId, "color":color, "size":size, "amount":amount}
				
				, success:function(data) {
					if (data.code == 1) {
						alert("상세 등록이 완료되었습니다");
						location.href="/admin/admin_detail_list_view?productId=" + productId;
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