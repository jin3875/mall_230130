<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- logo -->
<div class="col-3">
	<a href="/product/product_main_view" class="logo"><span class="display-4">GirlFit</span></a>
</div>

<!-- category -->
<nav class="col-7">
	<ul class="nav">
		<li class="nav-item"><a href="/product/product_category_view?category=Outer" class="nav-link font-weight-bold">Outer</a></li>
		<li class="nav-item"><a href="/product/product_category_view?category=Top" class="nav-link font-weight-bold">Top</a></li>
		<li class="nav-item"><a href="/product/product_category_view?category=Pants" class="nav-link font-weight-bold">Pants</a></li>
		<li class="nav-item"><a href="/product/product_category_view?category=Skirt" class="nav-link font-weight-bold">Skirt</a></li>
		<li class="nav-item"><a href="/product/product_category_view?category=Shoes" class="nav-link font-weight-bold">Shoes</a></li>
	</ul>
</nav>

<!-- search -->
<div class="col-2 d-flex align-items-center">
	<input type="text" id="keyword" class="col-9 border-dark border-top-0 border-left-0 border-right-0 mr-3">
	<a href="#" id="searchBtn"><img src="https://cdn-icons-png.flaticon.com/512/622/622669.png" alt="search" width="20" height="20"></a>
</div>

<script>
	$(document).ready(function() {
		// 엔터키 작동
		$("#keyword").on('keydown', function(e) {
			if (e.keyCode == 13) {
				$('#searchBtn').trigger('click');
			}
		});
		
		// 검색 버튼
		$('#searchBtn').on('click', function() {
			let keyword = $('#keyword').val().trim();
			
			if (keyword == '') {
				alert('검색어를 입력하세요');
				return;
			}
			
			location.href="/product/product_search_view?keyword=" + keyword;
		});
	});
</script>