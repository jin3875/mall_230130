package com.mall.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.purchase.bo.PurchaseServiceBO;
import com.mall.purchase.model.PurchaseCardView;
import com.mall.user.bo.UserBO;
import com.mall.user.model.User;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private PurchaseServiceBO purchaseServiceBO;
	
	/**
	 * 회원가입 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/sign_up_view")
	public String signUpView(Model model) {
		model.addAttribute("viewName", "user/signUp");
		return "template/layout";
	}
	
	/**
	 * 로그인 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/sign_in_view")
	public String signInView(Model model) {
		model.addAttribute("viewName", "user/signIn");
		return "template/layout";
	}
	
	/**
	 * 로그아웃
	 * @param session
	 * @return
	 */
	@GetMapping("/sign_out")
	public String signOut(HttpSession session) {
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");
		
		return "redirect:/product/product_main_view";
	}
	
	/**
	 * 아이디 찾기 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/search_id_view")
	public String searchIdView(Model model) {
		model.addAttribute("viewName", "user/searchId");
		return "template/layout";
	}
	
	/**
	 * 비밀번호 찾기 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/search_password_view")
	public String searchPasswordView(Model model) {
		model.addAttribute("viewName", "user/searchPassword");
		return "template/layout";
	}
	
	/**
	 * 회원 정보 화면
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/user_info_view")
	public String userInfoView(HttpSession session, Model model) {
		// 유저 조회 (아이디, 비밀번호)
		User user = userBO.getUserByLoginIdOrPassword((String)session.getAttribute("userLoginId"), null);
		model.addAttribute("user", user);
		
		model.addAttribute("viewName", "user/userInfo");
		return "template/layout";
	}
	
	/**
	 * 회원 탈퇴 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/user_sign_out_view")
	public String userSignOutView(Model model) {
		model.addAttribute("viewName", "user/userSignOut");
		return "template/layout";
	}
	
	/**
	 * 구매 목록 화면
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/user_purchase_list_view")
	public String userPurchaseListView(HttpSession session, Model model) {
		// 구매 + 구매 상품 목록
		List<PurchaseCardView> purchaseCardViewList = purchaseServiceBO.generatePurchaseCardViewList((int)session.getAttribute("userId"));
		model.addAttribute("purchaseCardViewList", purchaseCardViewList);
		
		model.addAttribute("viewName", "user/userPurchaseList");
		return "template/layout";
	}
	
	/**
	 * 후기 목록 화면
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/user_review_list_view")
	public String userReviewListView(HttpSession session, Model model) {
		// 구매 + 구매 상품 목록
		List<PurchaseCardView> purchaseCardViewList = purchaseServiceBO.generatePurchaseCardViewList((int)session.getAttribute("userId"));
		model.addAttribute("purchaseCardViewList", purchaseCardViewList);
		
		model.addAttribute("viewName", "user/userReviewList");
		return "template/layout";
	}

}
