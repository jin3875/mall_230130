package com.mall.admin;

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

import com.mall.product.bo.ProductBO;
import com.mall.product.bo.ProductServiceBO;
import com.mall.purchase.bo.PurchaseBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin")
@RestController
public class AdminRestController {
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private ProductServiceBO productServiceBO;
	
	@Autowired
	private PurchaseBO purchaseBO;
	
	/**
	 * 상품 등록 API
	 * @param category
	 * @param name
	 * @param price
	 * @param detail
	 * @param state
	 * @param fileList
	 * @param session
	 * @return
	 */
	@PostMapping("/admin_product_create")
	public Map<String, Object> adminProductCreate(
			@RequestParam("category") String category,
			@RequestParam("name") String name,
			@RequestParam("price") int price,
			@RequestParam(value="detail", required=false) String detail,
			@RequestParam("state") int state,
			@RequestParam(value="fileList", required=false) List<MultipartFile> fileList,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		boolean process = false;
		
		// 상품 추가
		process = productServiceBO.generateAddProduct((String)session.getAttribute("userLoginId"), category, name, price, detail, state, fileList);
		
		if (process) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "상품 등록에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 상품 수정 API
	 * @param productId
	 * @param category
	 * @param name
	 * @param price
	 * @param detail
	 * @param state
	 * @param fileList
	 * @param session
	 * @return
	 */
	@PutMapping("/admin_product_update")
	public Map<String, Object> adminProductUpdate(
			@RequestParam("productId") int productId,
			@RequestParam("category") String category,
			@RequestParam("name") String name,
			@RequestParam("price") int price,
			@RequestParam(value="detail", required=false) String detail,
			@RequestParam("state") int state,
			@RequestParam(value="fileList", required=false) List<MultipartFile> fileList,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		boolean process = false;
		
		// 상품 수정
		process = productServiceBO.generateUpdateProduct((String)session.getAttribute("userLoginId"), productId, category, name, price, detail, state, fileList);
		
		if (process) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "상품 수정에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 상품 삭제 API
	 * @param productId
	 * @return
	 */
	@DeleteMapping("/admin_product_delete")
	public Map<String, Object> adminProductDelete(@RequestParam("productId") int productId) {
		Map<String, Object> result = new HashMap<>();
		
		boolean process = false;
		
		// 상품 삭제
		process = productServiceBO.generateDeleteProduct(productId);
		
		if (process) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "상품 삭제에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 상품 상세 등록 API
	 * @param productId
	 * @param color
	 * @param size
	 * @param amount
	 * @return
	 */
	@PostMapping("/admin_detail_create")
	public Map<String, Object> adminDetailCreate(
			@RequestParam("productId") int productId,
			@RequestParam("color") String color,
			@RequestParam("size") String size,
			@RequestParam("amount") int amount
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 상품 상세 추가
		int rowCount = productBO.addProductDetail(productId, color, size, amount);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "상품 상세 등록에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 상품 상세 수정 API
	 * @param detailId
	 * @param color
	 * @param size
	 * @param amount
	 * @return
	 */
	@PutMapping("/admin_detail_update")
	public Map<String, Object> adminDetailUpdate(
			@RequestParam("detailId") int detailId,
			@RequestParam("color") String color,
			@RequestParam("size") String size,
			@RequestParam("amount") int amount
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 상품 상세 수정
		int rowCount = productBO.updateProductDetail(detailId, color, size, amount);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "상품 상세 수정에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 상품 상세 삭제 API
	 * @param detailId
	 * @return
	 */
	@DeleteMapping("/admin_detail_delete")
	public Map<String, Object> adminDetailDelete(@RequestParam("detailId") int detailId) {
		Map<String, Object> result = new HashMap<>();
		
		// 상품 상세 삭제
		int rowCount = productBO.deleteProductDetail(detailId);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "상품 상세 삭제에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 판매 정보 수정 API
	 * @param purchaseId
	 * @param courier
	 * @param trackingNumber
	 * @param cancellation
	 * @return
	 */
	@PutMapping("/admin_sale_update")
	public Map<String, Object> adminSaleUpdate(
			@RequestParam("purchaseId") int purchaseId,
			@RequestParam("courier") String courier,
			@RequestParam("trackingNumber") String trackingNumber,
			@RequestParam("cancellation") int cancellation
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 구매 정보 수정
		int rowCount = purchaseBO.updatePurchaseById(purchaseId, courier, trackingNumber, cancellation);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "판매 정보 수정에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 판매 상품 정보 수정 API
	 * @param purchaseProductId
	 * @param refund
	 * @param exchange
	 * @param completion
	 * @return
	 */
	@PutMapping("/admin_sale_detail_update")
	public Map<String, Object> adminSaleDetailUpdate(
			@RequestParam("purchaseProductId") int purchaseProductId,
			@RequestParam("refund") int refund,
			@RequestParam("exchange") int exchange,
			@RequestParam("completion") int completion
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 구매 상품 정보 수정
		int rowCount = purchaseBO.updatePurchaseProductById(purchaseProductId, refund, exchange, completion);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "판매 상품 정보 수정에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 후기 삭제 API
	 * @param purchaseProductId
	 * @return
	 */
	@PutMapping("/admin_review_delete")
	public Map<String, Object> adminReviewDelete(@RequestParam("purchaseProductId") int purchaseProductId) {
		Map<String, Object> result = new HashMap<>();
		
		// 구매 상품 후기 삭제
		int rowCount = purchaseBO.updatePurchaseProductReviewNull(purchaseProductId);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "후기 삭제에 실패했습니다");
		}
		
		return result;
	}

}
