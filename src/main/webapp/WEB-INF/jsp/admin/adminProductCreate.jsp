<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="admin-box d-flex justify-content-between">
	<div class="admin-small-box">
		<div class="d-flex justify-content-between align-items-center">
			<span>카테고리</span>
			<select id="" class="form-control col-8">
				<option value="" selected>카테고리를 선택하세요</option>
				<option>Outer</option>
				<option>Top</option>
				<option>Pants</option>
				<option>Skirt</option>
				<option>Shoes</option>
			</select>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>이름</span>
			<input type="text" id="" class="form-control col-8">
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>가격</span>
			<input type="text" id="" class="form-control col-8">
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>판매 여부</span>
			<select id="" class="form-control col-8">
				<option value="" selected>판매 여부를 선택하세요</option>
				<option value="y">판매 중</option>
				<option value="n">판매 대기</option>
			</select>
		</div>
	</div>
	
	<div class="admin-small-box">
		<div class="d-flex justify-content-between align-items-center">
			<div class="d-flex">
				<span class="mr-5">상품 사진</span>
				<div id="fileName"></div>
			</div>
			<input type="file" id="file" class="d-none" accept=".gif, .jpg, .png, .jpeg">
			<a href="#" id="fileUploadBtn"><img src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-image-512.png" width="30"></a>
		</div>
		
		<div class="d-flex justify-content-between mt-3">
			<span class="mt-2">상품 상세 설명</span>
			<textarea id="" class="form-control col-8" rows="4"></textarea>
		</div>
	</div>
</div>

<div class="admin-box d-flex justify-content-between mt-4">
	<a href="/admin/admin_product_list_view" class="btn btn-light">상품 목록</a>
	<button id="createBtn" class="btn btn-secondary">상품 등록</button>
</div>