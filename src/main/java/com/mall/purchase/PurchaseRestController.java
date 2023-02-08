package com.mall.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mall.product.bo.ProductDetailBO;
import com.mall.product.model.ProductDetail;
import com.mall.purchase.bo.WishListBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/purchase")
@RestController
public class PurchaseRestController {
	
	@Autowired
	private ProductDetailBO productDetailBO;
	
	@Autowired
	private WishListBO wishListBO;
	
	/**
	 * 장바구니 추가
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
		
		Integer userId = (Integer)session.getAttribute("userId");
		
		if (userId == null) {
			result.put("code", 100);
			result.put("errorMessage", "로그인 후 이용 가능합니다");
			return result;
		}
		
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
	
	// 장바구니 삭제
	@DeleteMapping("/wish_list_delete")
	public Map<String, Object> wishListDelete(
			@RequestParam(value="idList[]") List<Integer> idList,
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

}
