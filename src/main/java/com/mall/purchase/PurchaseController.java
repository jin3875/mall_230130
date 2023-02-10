package com.mall.purchase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mall.purchase.bo.PurchaseProductViewBO;
import com.mall.purchase.bo.WishListBO;
import com.mall.purchase.model.PurchaseProductView;
import com.mall.purchase.model.WishList;
import com.mall.user.bo.UserBO;
import com.mall.user.model.User;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/purchase")
@Controller
public class PurchaseController {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private WishListBO wishListBO;
	
	@Autowired
	private PurchaseProductViewBO beforePurchaseViewBO;
	
	/**
	 * 장바구니 화면
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/wish_list_view")
	public String wishListView(HttpSession session, Model model) {
		List<PurchaseProductView> purchaseProductViewList = new ArrayList<>();
		
		// 장바구니 목록
		List<WishList> wishListList = wishListBO.getWishListList((int)session.getAttribute("userId"));
		
		for (WishList wishList : wishListList) {
			// 구매할 상품 뷰 추가 (상품 상세 id)
			purchaseProductViewList.add(beforePurchaseViewBO.generatePurchaseProductViewByDetailId(wishList.getProductId(), wishList.getProductDetailId(), wishList.getAmount(), wishList.getId()));
		}
		
		model.addAttribute("purchaseProductViewList", purchaseProductViewList);
		
		model.addAttribute("viewName", "purchase/wishList");
		return "template/layout";
	}
	
	/**
	 * 구매하기 화면
	 * @param productId
	 * @param color
	 * @param size
	 * @param amount
	 * @param idList
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/purchase_view")
	public String purchaseView(
			@RequestParam(value="productId", required=false) Integer productId,
			@RequestParam(value="color", required=false) String color,
			@RequestParam(value="size", required=false) String size,
			@RequestParam(value="amount", required=false) Integer amount,
			@RequestParam(value="wishListId", required=false) List<Integer> idList,
			HttpSession session,
			Model model
	) {
		List<PurchaseProductView> purchaseProductViewList = new ArrayList<>();
		
		if (idList == null) {
			// 구매할 상품 뷰 추가 (상품 상세 색상, 사이즈)
			purchaseProductViewList.add(beforePurchaseViewBO.generatePurchaseProductViewByColorSize(productId, color, size, amount));
		} else {
			for (int id : idList) {
				// 장바구니 조회
				WishList wishList = wishListBO.getWishListById(id);
				
				// 구매할 상품 뷰 추가 (상품 상세 id)
				purchaseProductViewList.add(beforePurchaseViewBO.generatePurchaseProductViewByDetailId(wishList.getProductId(), wishList.getProductDetailId(), wishList.getAmount(), id));
			}
		}
		
		model.addAttribute("purchaseProductViewList", purchaseProductViewList);
		
		// 유저 조회
		User user = userBO.getUserById((int)session.getAttribute("userId"));
		model.addAttribute("user", user);
		
		model.addAttribute("viewName", "purchase/purchase");
		return "template/layout";
	}

}
