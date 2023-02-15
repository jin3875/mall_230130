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
import com.mall.product.model.Product;
import com.mall.product.model.ProductDetail;
import com.mall.product.model.ProductPicture;
import com.mall.purchase.bo.PurchaseBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin")
@RestController
public class AdminRestController {
	
	@Autowired
	private ProductBO productBO;
	
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
		
		Product product = new Product();
		product.setCategory(category);
		product.setName(name);
		product.setPrice(price);
		product.setDetail(detail);
		product.setState(state);
		
		// 상품 추가
		productBO.addProduct(product);
		int productId = product.getId();
		
		if (productId > 0) {
			if (fileList != null) {
				int count = 0;
				
				for (MultipartFile file : fileList) {
					// 상품 사진 추가
					int rowCount = productBO.addProductPicture((String)session.getAttribute("userLoginId"), productId, file);
					count += rowCount;
				}
				
				if (count == fileList.size()) {
					result.put("code", 1);
					result.put("result", "success");
				} else if (count == 0) {
					result.put("code", 500);
					result.put("errorMessage", "상품 사진 등록에 전체 실패했습니다");
				} else {
					result.put("code", 500);
					result.put("errorMessage", "상품 사진 등록에 일부 실패했습니다");
				}
			} else {
				result.put("code", 1);
				result.put("result", "success");
			}
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
		
		// 상품 수정
		int rowCount = productBO.updateProduct(productId, category, name, price, detail, state);
		
		if (rowCount > 0) {
			if (fileList != null) {
				int checkCount = 0;
				int count = 0;
				
				// 상품 사진 목록
				List<ProductPicture> productPictureList = productBO.getProductPictureListByProductId(productId);
				checkCount += productPictureList.size();
				
				for (ProductPicture productPicture : productPictureList) {
					// 상품 사진 삭제
					int rowCount2 = productBO.deleteProductPicture(productId, productPicture.getImagePath());
					count += rowCount2;
				}
				
				checkCount += fileList.size();
				
				for (MultipartFile file : fileList) {
					// 상품 사진 추가
					int rowCount2 = productBO.addProductPicture((String)session.getAttribute("userLoginId"), productId, file);
					count += rowCount2;
				}
				
				if (count == checkCount) {
					result.put("code", 1);
					result.put("result", "success");
				} else if (count == 0) {
					result.put("code", 500);
					result.put("errorMessage", "기존 상품 사진 삭제 및 변경된 상품 사진 등록에 전체 실패했습니다");
				} else {
					result.put("code", 500);
					result.put("errorMessage", "기존 상품 사진 삭제 및 변경된 상품 사진 등록에 일부 실패했습니다");
				}
			} else {
				result.put("code", 1);
				result.put("result", "success");
			}
		} else {
			result.put("code", 500);
			result.put("errorMessage", "상품 수정에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 상품 삭제 API
	 * @param productId
	 * @param session
	 * @return
	 */
	@DeleteMapping("/admin_product_delete")
	public Map<String, Object> adminProductDelete(
			@RequestParam("productId") int productId,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 상품 삭제
		int rowCount = productBO.deleteProduct(productId);
		
		if (rowCount > 0) {
			// 상품 사진 목록
			List<ProductPicture> productPictureList = productBO.getProductPictureListByProductId(productId);
			
			// 상품 상세 목록
			List<ProductDetail> productDetailList = productBO.getProductDetailList(productId);
			
			if (productPictureList != null || productDetailList != null) {
				int checkCount = 0;
				int count = 0;
				
				checkCount += productPictureList.size();
				checkCount += productDetailList.size();
				
				for (ProductPicture productPicture : productPictureList) {
					// 상품 사진 삭제
					int rowCount2 = productBO.deleteProductPicture(productId, productPicture.getImagePath());
					count += rowCount2;
				}
				
				for (ProductDetail productDetail : productDetailList) {
					// 상품 상세 삭제
					int rowCount2 = productBO.deleteProductDetail(productDetail.getId());
					count += rowCount2;
				}
				
				if (count == checkCount) {
					result.put("code", 1);
					result.put("result", "success");
				} else if (count == 0) {
					result.put("code", 500);
					result.put("errorMessage", "상품 사진 삭제와 상품 상세 삭제에 전체 실패했습니다");
				} else {
					result.put("code", 500);
					result.put("errorMessage", "상품 사진 삭제와 상품 상세 삭제에 일부 실패했습니다");
				}
			} else {
				result.put("code", 1);
				result.put("result", "success");
			}
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
