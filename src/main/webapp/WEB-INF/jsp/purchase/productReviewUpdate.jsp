<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="d-flex">
	<div class="my-page-box mt-4">
		<h4 class="text-secondary">마이페이지</h4>
		<nav class="mt-4">
			<ul class="nav">
				<li class="nav-item"><a href="/user/user_purchase_list_view" class="nav-link font-weight-bold pl-0">구매 목록</a></li>
				<li class="nav-item"><a href="/user/user_review_list_view" class="nav-link font-weight-bold pl-0 mt-2">후기 목록</a></li>
				<li class="nav-item"><a href="/user/user_info_view" class="nav-link font-weight-bold pl-0 mt-2">회원 정보</a></li>
				<li class="nav-item"><a href="/user/user_sign_out_view" class="nav-link font-weight-bold pl-0 mt-2">회원 탈퇴</a></li>
			</ul>
		</nav>
	</div>
	
	<div class="w-100 ml-5">
		<h3 class="font-weight-bold">후기 수정</h3>
		
		<div class="purchase-box d-flex align-items-center mt-5 mr-5 bg-light">
			<div class="col-3">
				<img src="${purchaseProductView.productPicture.imagePath}" alt="product" width="150" height="150">
			</div>
			<div class="col-7">
				<big class="font-weight-bold">${purchaseProductView.product.name}</big>
				<div class="text-secondary mt-3">
					<div class="col-4 d-flex justify-content-between p-0">
						<span>가격</span>
						<span><fmt:formatNumber value="${purchaseProductView.product.price}" type="number" />원</span>
					</div>
					
					<div class="col-4 d-flex justify-content-between p-0">
						<span>색상</span>
						<span>${purchaseProductView.productDetail.color}</span>
					</div>
					
					<div class="col-4 d-flex justify-content-between p-0">
						<span>사이즈</span>
						<span>${purchaseProductView.productDetail.size}</span>
					</div>
					
					<div class="col-4 d-flex justify-content-between p-0">
						<span>수량</span>
						<span>${purchaseProductView.purchaseProduct.amount}</span>
					</div>
				</div>
			</div>
			<h4 class="col-2"><fmt:formatNumber value="${purchaseProductView.product.price * purchaseProductView.purchaseProduct.amount}" type="number" />원</h4>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-5 mr-5">
			<h4 class="font-weight-bold m-0">별점</h4>
			<select id="star" class="form-control col-3">
				<option value="" selected>-- 별점 --</option>
				<option value="5" <c:if test="${purchaseProductView.purchaseProduct.star == 5}">selected</c:if>>★★★★★</option>
				<option value="4" <c:if test="${purchaseProductView.purchaseProduct.star == 4}">selected</c:if>>★★★★</option>
				<option value="3" <c:if test="${purchaseProductView.purchaseProduct.star == 3}">selected</c:if>>★★★</option>
				<option value="2" <c:if test="${purchaseProductView.purchaseProduct.star == 2}">selected</c:if>>★★</option>
				<option value="1" <c:if test="${purchaseProductView.purchaseProduct.star == 1}">selected</c:if>>★</option>
			</select>
		</div>
		
		<div class="d-flex mt-5 mr-5">
			<div class="col-5 p-0">
				<h4 class="font-weight-bold m-0">후기 사진</h4>
				<div class="small text-secondary mt-2">* 후기 사진은 최대 1장만 가능합니다 *</div>
				<div class="small text-secondary ">* 변경할 사진을 선택하지 않으면 기존 사진이 유지됩니다 *</div>
			</div>
			<div class="col-2 p-0 d-flex justify-content-center align-items-center">
				<c:if test="${not empty purchaseProductView.purchaseProduct.imagePath}">
					<a href="${purchaseProductView.purchaseProduct.imagePath}" target="_blank" class="text-primary">기존 후기 사진</a>
				</c:if>
			</div>
			<div class="col-5 p-0 d-flex justify-content-end align-items-center">
				<span id="fileName" class="mr-4"></span>
				<input type="file" id="file" class="d-none" accept=".gif, .png, .jpg, .jpeg">
				<a href="#" id="fileUploadBtn"><img src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-image-512.png" alt="fileUpload" width="30"></a>
			</div>
		</div>
		
		<div class="d-flex justify-content-between mt-5 mr-5">
			<h4 class="font-weight-bold m-0">후기</h4>
			<textarea id="review" class="form-control col-10" rows="5">${purchaseProductView.purchaseProduct.review}</textarea>
		</div>
		
		<div class="d-flex justify-content-end mt-5 mr-5">
			<button type="button" id="reviewEditBtn" class="btn btn-secondary" data-purchase-product-id="${purchaseProductView.purchaseProduct.id}">수정하기</button>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 파일 업로드 아이콘
		$('#fileUploadBtn').on('click', function(e) {
			e.preventDefault();
			$('#file').click();
		});
		
		// 파일 선택
		$('#file').on('change', function(e) {
			let fileName = e.target.files[0].name;
			let ext = fileName.split(".").pop().toLowerCase();
			
			if ($.inArray(ext, ['jpg', 'jpeg', 'png', 'gif']) > -1) {
				$('#fileName').text(fileName);
			} else {
				alert("이미지 파일만 가능합니다");
				$('#file').val('');
				$('#fileName').text('');
				return;
			}
		});
		
		// 수정하기 버튼
		$('#reviewEditBtn').on('click', function() {
			let purchaseProductId = $(this).data('purchase-product-id');
			let star = $('#star').val();
			let review = $('#review').val();
			
			if (star == '') {
				alert('별점을 선택하세요');
				return;
			}
			
			let formData = new FormData();
			formData.append("purchaseProductId", purchaseProductId);
			formData.append("star", star);
			formData.append("review", review);
			formData.append("file", $('#file')[0].files[0]);
			
			$.ajax({
				type:"PUT"
				, url:"/purchase/product_review_update"
				, data:formData
				// 파일 업로드를 위한 필수 설정
				, enctype:"multipart/form-data"
				, processData:false
				, contentType:false
				
				, success:function(data) {
					if (data.code == 1) {
						alert("후기 수정이 완료되었습니다");
						location.href="/user/user_review_list_view";
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