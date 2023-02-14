package com.mall.interceptor;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mall.user.bo.UserBO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserBO userBO;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		// 요청 url
		String uri = request.getRequestURI();
		logger.info("[##### preHandle - uri : {}]", uri);
		
		// 세션
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		
		// 비로그인 + ("/user/user" or "/purchase") -> 로그인 화면
		if (userId == null && (uri.startsWith("/user/user") || uri.startsWith("/purchase"))) {
			response.sendRedirect("/user/sign_in_view");
			return false;
		}
		
		// 로그인 + ("/user/sign" or "/user/search") -> 메인 화면
		if (userId != null && (uri.startsWith("/user/sign") || uri.startsWith("/user/search"))) {
			response.sendRedirect("/product/product_main_view");
			return false;
		}
		
		// (비로그인 or 비관리자) + "/admin" -> 로그인 화면
		if ((userId == null || userBO.getUserById(userId).getType() != 1) && uri.startsWith("/admin")) {
			response.sendRedirect("/user/sign_in_view");
			return false;
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) {
		logger.info("[$$$$$ postHandle]");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		logger.info("[@@@@@ afterCompletion]");
	}

}
