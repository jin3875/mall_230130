<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="input-big-box d-flex justify-content-between">
	<div class="input-small-box">
		<div class="d-flex justify-content-between align-items-center">
			<span>카테고리</span>
			<select id="category" class="form-control col-8">
				<option value="">-- 카테고리 --</option>
				<option <c:if test="${productView.product.category == 'Outer'}">selected</c:if>>Outer</option>
				<option <c:if test="${productView.product.category == 'Top'}">selected</c:if>>Top</option>
				<option <c:if test="${productView.product.category == 'Pants'}">selected</c:if>>Pants</option>
				<option <c:if test="${productView.product.category == 'Skirt'}">selected</c:if>>Skirt</option>
				<option <c:if test="${productView.product.category == 'Shoes'}">selected</c:if>>Shoes</option>
			</select>
		</div>
		
		<div>
			<div id="categoryCheckEmpty" class="text-center ml-4 small text-danger d-none">카테고리를 선택하세요.</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>이름</span>
			<input type="text" id="name" class="form-control col-8" value="${productView.product.name}">
		</div>
		
		<div>
			<div id="nameCheckEmpty" class="text-center small text-danger d-none">이름을 입력하세요.</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>가격</span>
			<input type="text" id="price" class="form-control col-8" value="${productView.product.price}">
		</div>
		
		<div>
			<div id="priceCheckEmpty" class="text-center small text-danger d-none">가격을 입력하세요.</div>
			<div id="priceCheckValidation" class="text-center small text-danger d-none">숫자만 가능합니다.</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>판매 여부</span>
			<select id="state" class="form-control col-8">
				<option value="">-- 판매 여부 --</option>
				<option value="1" <c:if test="${productView.product.state == 1}">selected</c:if>>판매 중</option>
				<option value="0" <c:if test="${productView.product.state == 0}">selected</c:if>>판매 대기</option>
			</select>
		</div>
		
		<div>
			<div id="stateCheckEmpty" class="text-center ml-5 small text-danger d-none">판매 여부를 선택하세요.</div>
		</div>
	</div>
	
	<div class="input-small-box">
		<div class="d-flex justify-content-between align-items-center">
			<span>상품 사진</span>
			<input type="file" id="file" class="d-none" accept=".gif, .png, .jpg, .jpeg">
			<a href="#" id="fileUploadBtn"><img src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-image-512.png" alt="fileUpload" width="30"></a>
		</div>
		
		<small class="text-danger">* 변경할 사진을 선택하지 않으면 기존 사진이 유지됩니다 *</small>
		
		<div class="mt-3">
			<div>기존 사진</div>
			<c:forEach var="productPicture" items="${productView.productPictureList}">
				<div class="ml-3">
					<a href="${productPicture.imagePath}" target="_blank" class="text-primary">${fn:split(productPicture.imagePath, "/")[2]}</a>
				</div>
			</c:forEach>
		</div>
		
		<div id="fileList" class="mt-3">
			<div>변경할 사진</div>
		</div>
		
		<div class="d-flex justify-content-between mt-4">
			<span class="mt-2">상품 상세 설명</span>
			<textarea id="detail" class="form-control col-8" rows="4">${productView.product.detail}</textarea>
		</div>
	</div>
</div>

<div class="input-big-box d-flex justify-content-between mt-5">
	<a href="/admin/admin_product_list_view" class="btn btn-light">상품 목록</a>
	<button id="editBtn" class="btn btn-secondary" data-product-id="${productView.product.id}">상품 수정</button>
</div>

<script>
	let fileNo = 0;
	let filesArr = [];
	
	// 파일 삭제
	function deleteFile(i) {
		$('#file' + i).remove();
		delete filesArr[i];
	}
	
	$(document).ready(function() {
		// 가격 체크
		$('#price').on('change', function() {
			$('#priceCheckEmpty').addClass('d-none');
			$('#priceCheckValidation').addClass('d-none');
			
			let price = $('#price').val().trim();
			
			if (isNaN(price)) {
				$('#priceCheckValidation').removeClass('d-none');
				return;
			}
		});
		
		// 파일 업로드 아이콘
		$('#fileUploadBtn').on('click', function(e) {
			e.preventDefault();
			$('#file').click();
		});
		
		// 파일 선택
		$('#file').on('change', function(e) {
			let file = e.target.files[0];
			let ext = file.name.split(".").pop().toLowerCase();
			
			if ($.inArray(ext, ['jpg', 'jpeg', 'png', 'gif']) > -1) {
				filesArr.push(file);
				
				let data = '';
				data += '<div id="file' + fileNo + '" class="d-flex col-11 justify-content-between">';
				data += '	<div>' + file.name + '</div>';
				data += '	<a href="#" onclick="deleteFile(' + fileNo + ');"><img src="https://cdn-icons-png.flaticon.com/512/657/657059.png" alt="delete" width="10"></a>';
				data += '</div>';
				
				$('#fileList').append(data);
				fileNo++;
			} else {
				alert("이미지 파일만 가능합니다");
				return;
			}
			
			$('#file').val('');
		});
		
		// 상품 수정 버튼
		$('#editBtn').on('click', function() {
			$('#categoryCheckEmpty').addClass('d-none');
			$('#nameCheckEmpty').addClass('d-none');
			$('#priceCheckEmpty').addClass('d-none');
			$('#stateCheckEmpty').addClass('d-none');
			
			let productId = $(this).data('product-id');
			let category = $('#category').val();
			let name = $('#name').val().trim();
			let price = $('#price').val().trim();
			let state = $('#state').val();
			let detail = $('#detail').val();
			
			if (category == '') {
				$('#categoryCheckEmpty').removeClass('d-none');
				$('#category').focus();
				return;
			}
			
			if (name == '') {
				$('#nameCheckEmpty').removeClass('d-none');
				$('#name').focus();
				return;
			}
			
			if (price == '') {
				$('#priceCheckEmpty').removeClass('d-none');
				$('#price').focus();
				return;
			}
			
			if ($('#priceCheckValidation').hasClass('d-none') == false) {
				$('#price').focus();
				return;
			}
			
			if (state == '') {
				$('#stateCheckEmpty').removeClass('d-none');
				$('#state').focus();
				return;
			}
			
			let formData = new FormData();
			formData.append("productId", productId);
			formData.append("category", category);
			formData.append("name", name);
			formData.append("price", price);
			formData.append("state", state);
			formData.append("detail", detail);
			for (var i = 0; i < filesArr.length; i++) {
				if (filesArr[i] !== undefined) {
					formData.append("fileList", filesArr[i]);
				}
			}
			
			$.ajax({
				type:"PUT"
				, url:"/admin/admin_product_update"
				, data:formData
				// 파일 업로드를 위한 필수 설정
				, enctype:"multipart/form-data"
				, processData:false
				, contentType:false
				
				, success:function(data) {
					if (data.code == 1) {
						alert("수정이 완료되었습니다");
						location.href="/admin/admin_product_list_view";
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