package com.mall.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mall.product.bo.ProductDetailBO;
import com.mall.product.model.ProductDetail;
import com.mall.purchase.bo.AddPurchaseBO;
import com.mall.purchase.bo.PurchaseBO;
import com.mall.purchase.bo.PurchaseProductBO;
import com.mall.purchase.bo.WishListBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/purchase")
@RestController
public class PurchaseRestController {
	
	@Autowired
	private ProductDetailBO productDetailBO;
	
	@Autowired
	private WishListBO wishListBO;
	
	@Autowired
	private PurchaseBO purchaseBO;
	
	@Autowired
	private PurchaseProductBO purchaseProductBO;
	
	@Autowired
	private AddPurchaseBO addPurchaseBO;
	
	/**
	 * 장바구니 추가 API
	 * @param productId
	 * @param color
	 * @param size
	 * @param amount
	 * @param session
	 * @return
	 */
	@PostMapping("/wish_list_create")
	public Map<String, Object> wishListCreate(
			@RequestParam("productId") int productId,
			@RequestParam("color") String color,
			@RequestParam("size") String size,
			@RequestParam("amount") int amount,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		int userId = (int)session.getAttribute("userId");
		
		// 상품 상세 조회 (색상, 사이즈)
		ProductDetail productDetail = productDetailBO.getProductDetailByProductIdColorSize(productId, color, size);
		
		// 장바구니 추가
		int rowCount = wishListBO.addWishList(userId, productId, productDetail.getId(), amount);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "장바구니 추가에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 장바구니 삭제 API
	 * @param idList
	 * @param session
	 * @return
	 */
	@DeleteMapping("/wish_list_delete")
	public Map<String, Object> wishListDelete(
			@RequestParam("idList[]") List<Integer> idList,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		int userId = (int)session.getAttribute("userId");
		
		// 장바구니 삭제
		int rowCount = wishListBO.deleteWishList(userId, idList);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "장바구니 삭제에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 구매하기 API
	 * @param wishList
	 * @param productList
	 * @param totalPrice
	 * @param name
	 * @param phoneNumber
	 * @param postcode
	 * @param address
	 * @param detailAddress
	 * @param message
	 * @param session
	 * @return
	 */
	@PostMapping("/purchase")
	public Map<String, Object> purchase(
			@RequestParam(value="wishList[]", required=false) List<Integer> wishList,
			@RequestParam("productList[]") List<String> productList,
			@RequestParam("totalPrice") int totalPrice,
			@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("postcode") String postcode,
			@RequestParam("address") String address,
			@RequestParam("detailAddress") String detailAddress,
			@RequestParam(value="message", required=false) String message,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		boolean process = false;
		
		try {
			process = addPurchaseBO.generateAddPurchase(wishList, productList, (int)session.getAttribute("userId"),
					totalPrice, name, phoneNumber, postcode, address, detailAddress, message);
		} catch (Exception e) {
			result.put("code", 500);
			result.put("errorMessage", "재고 수량이 부족합니다. 판매자에게 문의해주세요");
			
			return result;
		}
		
		if (process) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "구매에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 구매 취소 API
	 * @param purchaseId
	 * @return
	 */
	@PutMapping("/purchase_cancel")
	public Map<String, Object> purchaseCancel(@RequestParam("purchaseId") int purchaseId) {
		Map<String, Object> result = new HashMap<>();
		
		// 구매 취소
		int rowCount = purchaseBO.updatePurchase(purchaseId);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "구매 취소에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 환불 신청 API
	 * @param purchaseProductId
	 * @return
	 */
	@PutMapping("/product_refund")
	public Map<String, Object> productRefund(@RequestParam("purchaseProductId") int purchaseProductId) {
		Map<String, Object> result = new HashMap<>();
		
		// 구매 상품 환불
		int rowCount = purchaseProductBO.updatePurchaseProductRefund(purchaseProductId);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "환불 신청에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 교환 신청 API
	 * @param purchaseProductId
	 * @param productId
	 * @param color
	 * @param size
	 * @return
	 */
	@PutMapping("/product_exchange")
	public Map<String, Object> productExchange(
			@RequestParam("purchaseProductId") int purchaseProductId,
			@RequestParam("productId") int productId,
			@RequestParam("color") String color,
			@RequestParam("size") String size
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 상품 상세 조회 (색상, 사이즈)
		ProductDetail productDetail = productDetailBO.getProductDetailByProductIdColorSize(productId, color, size);
		
		// 구매 상품 교환
		int rowCount = purchaseProductBO.updatePurchaseProductExchange(purchaseProductId, productDetail.getId());
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "교환 신청에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 구매 확정 API
	 * @param purchaseProductId
	 * @return
	 */
	@PutMapping("/product_complete")
	public Map<String, Object> productComplete(@RequestParam("purchaseProductId") int purchaseProductId) {
		Map<String, Object> result = new HashMap<>();
		
		// 구매 상품 확정
		int rowCount = purchaseProductBO.updatePurchaseProductComplete(purchaseProductId);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "구매 확정에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 후기 작성 API
	 * @param purchaseProductId
	 * @param star
	 * @param review
	 * @param file
	 * @param session
	 * @return
	 */
	@PutMapping("/product_review_create")
	public Map<String, Object> productReviewCreate(
			@RequestParam("purchaseProductId") int purchaseProductId,
			@RequestParam("star") int star,
			@RequestParam(value="review", required=false) String review,
			@RequestParam(value="file", required=false) MultipartFile file,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 구매 상품 후기 작성
		int rowCount = purchaseProductBO.updatePurchaseProductReview((String)session.getAttribute("userLoginId"), purchaseProductId, star, review, file);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "후기 작성에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 후기 수정 API
	 * @param purchaseProductId
	 * @param star
	 * @param review
	 * @param file
	 * @param session
	 * @return
	 */
	@PutMapping("/product_review_update")
	public Map<String, Object> productReviewUpdate(
			@RequestParam("purchaseProductId") int purchaseProductId,
			@RequestParam("star") int star,
			@RequestParam(value="review", required=false) String review,
			@RequestParam(value="file", required=false) MultipartFile file,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 구매 상품 후기 수정
		int rowCount = purchaseProductBO.updatePurchaseProductReviewAgain((String)session.getAttribute("userLoginId"), purchaseProductId, star, review, file);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "후기 수정에 실패했습니다");
		}
		
		return result;
	}

}
