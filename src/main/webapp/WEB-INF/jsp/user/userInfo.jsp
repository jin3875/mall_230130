<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="user-box">
	<h3 class="text-center font-weight-bold">회원 정보</h3>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>이름</span>
		<div class="form-control col-8 bg-light">${user.name}</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-4">
		<span>아이디</span>
		<div class="form-control col-8 bg-light">${user.loginId}</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>비밀번호</span>
		<input type="password" id="password" class="form-control col-8">
	</div>
	
	<div>
		<div id="passwordCheckValidation" class="text-right mr-4 small text-danger d-none">5~20자의 비밀번호만 사용 가능합니다.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>비밀번호 확인</span>
		<input type="password" id="confirmPassword" class="form-control col-8">
	</div>
	
	<div>
		<div id="passwordCheckEqual" class="text-center ml-5 small text-danger d-none">비밀번호가 일치하지 않습니다.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>휴대폰 번호</span>
		<input type="text" id="phoneNumber" class="form-control col-8" value="${user.phoneNumber}">
	</div>
	
	<div>
		<div id="phoneNumberCheckValidation" class="text-center ml-4 small text-danger d-none">숫자만 사용 가능합니다.</div>
		<div id="phoneNumberCheckLength" class="text-center ml-5 small text-danger d-none">휴대폰 번호가 올바르지 않습니다.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>이메일</span>
		<input type="text" id="email" class="form-control col-8" value="${user.email}">
	</div>
	
	<div>
		<div id="emailCheckValidation" class="text-center ml-5 small text-danger d-none">이메일 주소가 올바르지 않습니다.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>우편번호</span>
		<input type="text" id="postcode" class="form-control col-8" value="${user.postcode}" readonly/>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>주소</span>
		<input type="text" id="address" class="form-control col-8" value="${user.address}">
	</div>
	
	<div>
		<div id="addressCheckValidation" class="text-center small text-danger d-none">주소를 확인하세요.</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>상세 주소</span>
		<input type="text" id="detailAddress" class="form-control col-8" value="${user.detailAddress}">
	</div>
	
	<div>
		<div id="detailAddressCheckEmpty" class="text-center ml-4 small text-danger d-none">상세 주소를 입력하세요.</div>
	</div>
	
	<button type="button" id="editBtn" class="btn btn-secondary btn-block mt-3">수정하기</button>
</div>