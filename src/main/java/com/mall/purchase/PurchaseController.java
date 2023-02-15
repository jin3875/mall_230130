package com.mall.purchase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mall.product.bo.ProductBO;
import com.mall.product.model.ProductDetail;
import com.mall.purchase.bo.ProductCardViewBO;
import com.mall.purchase.bo.PurchaseProductViewBO;
import com.mall.purchase.bo.PurchaseViewBO;
import com.mall.purchase.bo.WishListBO;
import com.mall.purchase.model.ProductCardView;
import com.mall.purchase.model.PurchaseProductView;
import com.mall.purchase.model.PurchaseView;
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
	private ProductBO productBO;
	
	@Autowired
	private WishListBO wishListBO;
	
	@Autowired
	private ProductCardViewBO productCardViewBO;
	
	@Autowired
	private PurchaseProductViewBO purchaseProductViewBO;
	
	@Autowired
	private PurchaseViewBO purchaseViewBO;
	
	/**
	 * 장바구니 화면
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/wish_list_view")
	public String wishListView(HttpSession session, Model model) {
		List<ProductCardView> productCardViewList = new ArrayList<>();
		
		// 장바구니 목록
		List<WishList> wishListList = wishListBO.getWishListList((int)session.getAttribute("userId"));
		
		for (WishList wishList : wishListList) {
			// 상품 카드 생성 (상품 상세 id)
			productCardViewList.add(productCardViewBO.generateProductCardViewByDetailId(wishList.getProductId(), wishList.getProductDetailId(), wishList.getAmount(), wishList.getId()));
		}
		
		model.addAttribute("productCardViewList", productCardViewList);
		
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
		List<ProductCardView> productCardViewList = new ArrayList<>();
		
		if (idList == null) {
			// 상품 카드 생성 (상품 상세 색상, 사이즈)
			productCardViewList.add(productCardViewBO.generateProductCardViewByColorSize(productId, color, size, amount));
		} else {
			for (int id : idList) {
				// 장바구니 조회
				WishList wishList = wishListBO.getWishListById(id);
				
				// 상품 카드 생성 (상품 상세 id)
				productCardViewList.add(productCardViewBO.generateProductCardViewByDetailId(wishList.getProductId(), wishList.getProductDetailId(), wishList.getAmount(), id));
			}
		}
		
		model.addAttribute("productCardViewList", productCardViewList);
		
		// 유저 조회
		User user = userBO.getUserById((int)session.getAttribute("userId"));
		model.addAttribute("user", user);
		
		model.addAttribute("viewName", "purchase/purchase");
		return "template/layout";
	}
	
	/**
	 * 구매 취소 화면
	 * @param purchaseId
	 * @param model
	 * @return
	 */
	@GetMapping("/purchase_cancel_view/{purchaseId}")
	public String purchaseCancelView(
			@PathVariable("purchaseId") int purchaseId,
			Model model
	) {
		// 구매 + 구매 상품 목록 조회
		PurchaseView purchaseView = purchaseViewBO.generatePurchaseViewByPurchaseId(purchaseId);
		model.addAttribute("purchaseView", purchaseView);
		
		model.addAttribute("viewName", "purchase/purchaseCancel");
		return "template/layout";
	}
	
	/**
	 * 환불 신청 화면
	 * @param purchaseProductId
	 * @param model
	 * @return
	 */
	@GetMapping("/product_refund_view/{purchaseProductId}")
	public String productRefundView(
			@PathVariable("purchaseProductId") int purchaseProductId,
			Model model
	) {
		// 구매 상품 카드 조회
		PurchaseProductView purchaseProductView = purchaseProductViewBO.generatePurchaseProductViewById(purchaseProductId);
		model.addAttribute("purchaseProductView", purchaseProductView);
		
		model.addAttribute("viewName", "purchase/productRefund");
		return "template/layout";
	}
	
	/**
	 * 교환 신청 화면
	 * @param purchaseProductId
	 * @param model
	 * @return
	 */
	@GetMapping("/product_exchange_view/{purchaseProductId}")
	public String productExchangeView(
			@PathVariable("purchaseProductId") int purchaseProductId,
			Model model
	) {
		// 구매 상품 카드 조회
		PurchaseProductView purchaseProductView = purchaseProductViewBO.generatePurchaseProductViewById(purchaseProductId);
		model.addAttribute("purchaseProductView", purchaseProductView);
		
		// 상품 상세 목록
		List<ProductDetail> productDetailList = productBO.getProductDetailList(purchaseProductView.getProduct().getId());
		model.addAttribute("productDetailList", productDetailList);
		
		model.addAttribute("viewName", "purchase/productExchange");
		return "template/layout";
	}
	
	/**
	 * 후기 작성 화면
	 * @param purchaseProductId
	 * @param model
	 * @return
	 */
	@GetMapping("/product_review_create_view/{purchaseProductId}")
	public String productReviewCreateView(
			@PathVariable("purchaseProductId") int purchaseProductId,
			Model model
	) {
		// 구매 상품 카드 조회
		PurchaseProductView purchaseProductView = purchaseProductViewBO.generatePurchaseProductViewById(purchaseProductId);
		model.addAttribute("purchaseProductView", purchaseProductView);
		
		model.addAttribute("viewName", "purchase/productReviewCreate");
		return "template/layout";
	}
	
	/**
	 * 후기 수정 화면
	 * @param purchaseProductId
	 * @param model
	 * @return
	 */
	@GetMapping("/product_review_update_view/{purchaseProductId}")
	public String productReviewUpdateView(
			@PathVariable("purchaseProductId") int purchaseProductId,
			Model model
	) {
		// 구매 상품 카드 조회
		PurchaseProductView purchaseProductView = purchaseProductViewBO.generatePurchaseProductViewById(purchaseProductId);
		model.addAttribute("purchaseProductView", purchaseProductView);
		
		model.addAttribute("viewName", "purchase/productReviewUpdate");
		return "template/layout";
	}

}
