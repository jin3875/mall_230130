package com.mall.purchase;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mall.product.bo.ProductBO;
import com.mall.product.model.ProductDetail;
import com.mall.purchase.bo.PurchaseServiceBO;
import com.mall.purchase.model.PurchaseCardView;
import com.mall.purchase.model.PurchaseProductCardView;
import com.mall.user.bo.UserBO;
import com.mall.user.model.User;
import com.mall.wishList.bo.WishListServiceBO;
import com.mall.wishList.model.WishListCardView;

@RequestMapping("/purchase")
@Controller
public class PurchaseController {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private WishListServiceBO wishListServiceBO;
	
	@Autowired
	private PurchaseServiceBO purchaseServiceBO;
	
	/**
	 * 장바구니 화면
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/wish_list_view")
	public String wishListView(HttpSession session, Model model) {
		// 장바구니 목록
		List<WishListCardView> wishListCardViewList = wishListServiceBO.generateWishListCardViewListForWishList((int)session.getAttribute("userId"));
		model.addAttribute("wishListCardViewList", wishListCardViewList);
		
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
		// 구매하기 목록
		List<WishListCardView> wishListCardViewList = wishListServiceBO.generateWishListCardViewListForPurchase(productId, color, size, amount, idList);
		model.addAttribute("wishListCardViewList", wishListCardViewList);
		
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
		PurchaseCardView purchaseCardView = purchaseServiceBO.generatePurchaseCardViewByPurchaseId(purchaseId);
		model.addAttribute("purchaseCardView", purchaseCardView);
		
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
		// 구매 상품 조회
		PurchaseProductCardView purchaseProductCardView = purchaseServiceBO.generatePurchaseProductCardViewByPurchaseProductId(purchaseProductId);
		model.addAttribute("purchaseProductCardView", purchaseProductCardView);
		
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
		PurchaseProductCardView purchaseProductCardView = purchaseServiceBO.generatePurchaseProductCardViewByPurchaseProductId(purchaseProductId);
		model.addAttribute("purchaseProductCardView", purchaseProductCardView);
		
		// 상품 상세 목록
		List<ProductDetail> productDetailList = productBO.getProductDetailList(purchaseProductCardView.getProductDetailCardView().getProduct().getId());
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
		// 구매 상품 조회
		PurchaseProductCardView purchaseProductCardView = purchaseServiceBO.generatePurchaseProductCardViewByPurchaseProductId(purchaseProductId);
		model.addAttribute("purchaseProductCardView", purchaseProductCardView);
		
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
		// 구매 상품 조회
		PurchaseProductCardView purchaseProductCardView = purchaseServiceBO.generatePurchaseProductCardViewByPurchaseProductId(purchaseProductId);
		model.addAttribute("purchaseProductCardView", purchaseProductCardView);
		
		model.addAttribute("viewName", "purchase/productReviewUpdate");
		return "template/layout";
	}

}
