<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h3 class="font-weight-bold">구매하기</h3>

<c:set var="totalPrice" value="0" />
<c:forEach var="wishCardView" items="${wishCardViewList}" varStatus="status">
	<div class="purchase-box d-flex align-items-center mt-5 mr-5 bg-light" data-product-id="${wishCardView.productDetailCardView.product.id}" data-detail-id="${wishCardView.productDetailCardView.productDetail.id}" data-amount="${wishCardView.amount}">
		<div class="wish-list-id d-none">${wishCardView.wishListId}</div>
		<h3 class="col-1 d-flex justify-content-center">${status.count}</h3>
		<div class="col-3">
			<img src="${wishCardView.productDetailCardView.productPicture.imagePath}" alt="product" width="150" height="150">
		</div>
		<div class="col-6">
			<big class="font-weight-bold">${wishCardView.productDetailCardView.product.name}</big>
			<div class="text-secondary mt-3">
				<div class="col-4 d-flex justify-content-between p-0">
					<span>가격</span>
					<span><fmt:formatNumber value="${wishCardView.productDetailCardView.product.price}" type="number" />원</span>
				</div>
				
				<div class="col-4 d-flex justify-content-between p-0">
					<span>색상</span>
					<span>${wishCardView.productDetailCardView.productDetail.color}</span>
				</div>
				
				<div class="col-4 d-flex justify-content-between p-0">
					<span>사이즈</span>
					<span>${wishCardView.productDetailCardView.productDetail.size}</span>
				</div>
				
				<div class="col-4 d-flex justify-content-between p-0">
					<span>수량</span>
					<span>${wishCardView.amount}</span>
				</div>
			</div>
		</div>
		<h4 class="col-2"><fmt:formatNumber value="${wishCardView.productDetailCardView.product.price * wishCardView.amount}" type="number" />원</h4>
		<c:set var="totalPrice" value="${totalPrice + wishCardView.productDetailCardView.product.price * wishCardView.amount}" />
	</div>
</c:forEach>

<div class="d-flex justify-content-end mt-4 mr-5">
	<h2 id="totalPrice" class="font-weight-bold mr-5" data-total-price="${totalPrice}">총 <fmt:formatNumber value="${totalPrice}" type="number" />원</h2>
</div>

<hr class="mt-5">

<div class="input-box mt-5">
	<h3 class="text-center font-weight-bold">배송 정보</h3>
	
	<button type="button" id="checkBtn" class="btn btn-sm btn-light mt-3">구매자 정보와 동일</button>
	
	<div id="newInfo">
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>받는 사람</span>
			<input type="text" id="name" class="form-control col-8">
		</div>
		
		<div>
			<div id="nameCheckEmpty" class="text-center small text-danger d-none">이름을 입력해주세요.</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>휴대폰 번호</span>
			<input type="text" id="phoneNumber" class="form-control col-8">
		</div>
		
		<div>
			<div id="phoneNumberCheckValidation" class="text-center ml-3 small text-danger d-none">숫자만 사용 가능합니다.</div>
			<div id="phoneNumberCheckLength" class="text-center ml-5 small text-danger d-none">휴대폰 번호가 올바르지 않습니다.</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>우편번호</span>
			<input type="text" id="postcode" class="form-control col-8" readonly/>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>주소</span>
			<input type="text" id="address" class="form-control col-8">
		</div>
		
		<div>
			<div id="addressCheckValidation" class="text-center mr-3 small text-danger d-none">주소를 확인하세요.</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>상세 주소</span>
			<input type="text" id="detailAddress" class="form-control col-8">
		</div>
		
		<div>
			<div id="detailAddressCheckEmpty" class="text-center ml-3 small text-danger d-none">상세 주소를 입력하세요.</div>
		</div>
	</div>
	
	<div id="userInfo" class="d-none">
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>받는 사람</span>
			<div id="userName" class="form-control col-8 bg-light">${user.name}</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>휴대폰 번호</span>
			<div id="userPhoneNumber" class="form-control col-8 bg-light">${user.phoneNumber}</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>우편번호</span>
			<div id="userPostcode" class="form-control col-8 bg-light">${user.postcode}</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>주소</span>
			<div id="userAddress" class="form-control col-8 bg-light">${user.address}</div>
		</div>
		
		<div class="d-flex justify-content-between align-items-center mt-3">
			<span>상세 주소</span>
			<div id="userDetailAddress" class="form-control col-8 bg-light">${user.detailAddress}</div>
		</div>
	</div>
	
	<div class="d-flex justify-content-between align-items-center mt-3">
		<span>배송 메세지</span>
		<textarea id="message" class="form-control col-8"></textarea>
	</div>
	
	<button type="button" id="purchaseBtn" class="btn btn-secondary btn-block mt-4">구매하기</button>
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	$(document).ready(function() {
		// 카카오 주소찾기 API
		$('#address').on('click', function() {
			new daum.Postcode({
				oncomplete: function(data) {
					$('#addressCheckValidation').addClass('d-none');
					$('#postcode').val(data.zonecode);
					$('#address').val(data.address);
					$('#detailAddress').focus();
				}
			}).open();
		});
		
		// 구매자 정보와 동일 버튼
		$('#checkBtn').on('click', function() {
			if ($('#newInfo').hasClass('d-none')) {
				$('#userInfo').addClass('d-none');
				$('#newInfo').removeClass('d-none');
			} else {
				$('#newInfo').addClass('d-none');
				$('#userInfo').removeClass('d-none');
			}
		});
		
		// 이름 체크
		$('#name').on('change', function() {
			$('#nameCheckEmpty').addClass('d-none');
			
			let name = $('#name').val().trim();
			
			if (name == '') {
				$('#nameCheckEmpty').removeClass('d-none');
				return;
			}
		});
		
		// 휴대폰 번호 체크
		$('#phoneNumber').on('change', function() {
			$('#phoneNumberCheckValidation').addClass('d-none');
			$('#phoneNumberCheckLength').addClass('d-none');
			
			let phoneNumber = $('#phoneNumber').val().trim();
			let phoneNumberFormat = /^(010)[0-9]{8}$/;
			
			if (phoneNumber == '') {
				$('#phoneNumberCheckValidation').removeClass('d-none');
				return;
			}
			
			if (isNaN(phoneNumber)) {
				$('#phoneNumberCheckValidation').removeClass('d-none');
				return;
			}
			
			if (phoneNumberFormat.test(phoneNumber) == false) {
				$('#phoneNumberCheckLength').removeClass('d-none');
				return;
			}
		});
		
		// 주소 체크
		$('#address').on('change', function() {
			$('#addressCheckValidation').removeClass('d-none');
			return;
		});
		
		// 상세 주소 체크
		$('#detailAddress').on('change', function() {
			$('#detailAddressCheckEmpty').addClass('d-none');
			
			let detailAddress = $('#detailAddress').val().trim();
			
			if (detailAddress == '') {
				$('#detailAddressCheckEmpty').removeClass('d-none');
				return;
			}
		});
		
		// 구매하기 버튼
		$('#purchaseBtn').on('click', function() {
			let wishList = [];
			let productList = [];
			let totalPrice = $('#totalPrice').data('total-price');
			let name = "";
			let phoneNumber = "";
			let postcode = "";
			let address = "";
			let detailAddress = "";
			let message = $('#message').val();
			
			$('.wish-list-id').each(function() {
				if ($(this).text() != '') {
					wishList.push($(this).text());
				}
			});
			
			$('.purchase-box').each(function() {
				let productId = $(this).data('product-id');
				let detailId = $(this).data('detail-id');
				let amount = $(this).data('amount');
				
				let product = productId + "/" + detailId + "/" + amount;
				productList.push(product);
			});
			
			if ($('#userInfo').hasClass('d-none')) {
				name = $('#name').val().trim();
				phoneNumber = $('#phoneNumber').val().trim();
				postcode = $('#postcode').val().trim();
				address = $('#address').val().trim();
				detailAddress = $('#detailAddress').val().trim();
				
				if (name == '') {
					$('#nameCheckEmpty').removeClass('d-none');
					$('#name').focus();
					return;
				}
				
				if (phoneNumber == '') {
					$('#phoneNumberCheckLength').addClass('d-none');
					$('#phoneNumberCheckValidation').removeClass('d-none');
					$('#phoneNumber').focus();
					return;
				}
				
				if ($('#phoneNumberCheckValidation').hasClass('d-none') == false || $('#phoneNumberCheckLength').hasClass('d-none') == false) {
					$('#phoneNumber').focus();
					return;
				}
				
				if (address == '') {
					$('#addressCheckValidation').removeClass('d-none');
					$('#address').focus();
					return;
				}
				
				if ($('#addressCheckValidation').hasClass('d-none') == false) {
					$('#address').focus();
					return;
				}
				
				if (detailAddress == '') {
					$('#detailAddressCheckEmpty').removeClass('d-none');
					$('#detailAddress').focus();
					return;
				}
				
				if ($('#detailAddressCheckEmpty').hasClass('d-none') == false) {
					$('#detailAddress').focus();
					return;
				}
			} else {
				name = $('#userName').text();
				phoneNumber = $('#userPhoneNumber').text();
				postcode = $('#userPostcode').text();
				address = $('#userAddress').text();
				detailAddress = $('#userDetailAddress').text();
			}
			
			$.ajax({
				type:"POST"
				, url:"/purchase/purchase"
				, data:{"wishList":wishList, "productList":productList, "totalPrice":totalPrice,
					"name":name, "phoneNumber":phoneNumber, "postcode":postcode,
					"address":address, "detailAddress":detailAddress, "message":message}
				
				, success:function(data) {
					if (data.code == 1) {
						alert('구매가 완료되었습니다');
						location.href="/user/user_purchase_list_view";
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