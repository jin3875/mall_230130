<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ul class="nav d-flex align-items-center">
	<c:choose>
		<c:when test="${empty userId}">
			<li class="nav-item"><a href="/user/sign_in_view" class="nav-link font-weight-bold">로그인</a></li>
			<li class="nav-item"><a href="/user/sign_up_view" class="nav-link font-weight-bold">회원가입</a></li>
		</c:when>
		
		<c:otherwise>
			<span class="text-secondary font-weight-bold mr-4">${userName}님</span>
			<li class="nav-item"><a href="/user/sign_out" class="nav-link font-weight-bold">로그아웃</a></li>
			<li class="nav-item"><a href="/purchase/wish_list_view" class="nav-link font-weight-bold">장바구니</a></li>
			<li class="nav-item"><a href="/user/user_purchase_list_view" class="nav-link font-weight-bold">마이페이지</a></li>
		</c:otherwise>
	</c:choose>
</ul>