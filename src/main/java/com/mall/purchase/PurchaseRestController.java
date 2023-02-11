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

import com.mall.product.bo.ProductDetailBO;
import com.mall.product.model.ProductDetail;
import com.mall.purchase.bo.PurchaseBO;
import com.mall.purchase.bo.PurchaseProductBO;
import com.mall.purchase.bo.WishListBO;
import com.mall.purchase.model.Purchase;

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
		
		Purchase purchase = new Purchase();
		purchase.setUserId((int)session.getAttribute("userId"));
		purchase.setTotalPrice(totalPrice);
		purchase.setName(name);
		purchase.setPhoneNumber(phoneNumber);
		purchase.setPostcode(postcode);
		purchase.setAddress(address);
		purchase.setDetailAddress(detailAddress);
		purchase.setMessage(message);
		
		// 구매 추가
		purchaseBO.addPurchase(purchase);
		int purchaseId = purchase.getId();
		
		if (purchaseId > 0) {
			int count = 0;
			
			for (String product : productList) {
				int productId = Integer.parseInt(product.split("/")[0]);
				int detailId = Integer.parseInt(product.split("/")[1]);
				int amount = Integer.parseInt(product.split("/")[2]);
				
				// 구매 상품 추가
				int rowCount = purchaseProductBO.addPurchaseProduct((int)session.getAttribute("userId"), purchaseId, productId, detailId, amount);
				count += rowCount;
			}
			
			if (count == productList.size()) {
				if (wishList != null) {
					// 장바구니 삭제
					wishListBO.deleteWishList((int)session.getAttribute("userId"), wishList);
				}
				
				result.put("code", 1);
				result.put("result", "success");
			} else if (count == 0) {
				result.put("code", 500);
				result.put("errorMessage", "상품 구매에 전체 실패했습니다");
			} else {
				result.put("code", 500);
				result.put("errorMessage", "상품 구매에 일부 실패했습니다");
			}
		} else {
			result.put("code", 500);
			result.put("errorMessage", "구매에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 구매 취소 API
	 * @param purchaseId
	 * @param session
	 * @return
	 */
	@PutMapping("/purchase_cancel")
	public Map<String, Object> purchaseCancel(
			@RequestParam("purchaseId") int purchaseId,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 구매 취소
		int rowCount = purchaseBO.updatePurchase(purchaseId, (int)session.getAttribute("userId"));
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "구매 취소에 실패했습니다");
		}
		
		return result;
	}

}
