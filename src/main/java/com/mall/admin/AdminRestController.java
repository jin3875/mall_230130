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
import com.mall.product.bo.ProductPictureBO;
import com.mall.product.model.Product;
import com.mall.product.model.ProductPicture;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin")
@RestController
public class AdminRestController {
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private ProductPictureBO productPictureBO;
	
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
					int rowCount = productPictureBO.addProductPicture((String)session.getAttribute("userLoginId"), productId, file);
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
				
				// 상품 사진 조회
				List<ProductPicture> productPictureList = productPictureBO.getProductPictureListByProductId(productId);
				checkCount += productPictureList.size();
				
				for (ProductPicture productPicture : productPictureList) {
					// 상품 사진 삭제
					int rowCount2 = productPictureBO.deleteProductPicture(productId, productPicture.getImagePath());
					count += rowCount2;
				}
				
				checkCount += fileList.size();
				
				for (MultipartFile file : fileList) {
					// 상품 사진 추가
					int rowCount2 = productPictureBO.addProductPicture((String)session.getAttribute("userLoginId"), productId, file);
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
			// 상품 사진 조회
			List<ProductPicture> productPictureList = productPictureBO.getProductPictureListByProductId(productId);
			
			if (productPictureList != null) {
				int count = 0;
				
				for (ProductPicture productPicture : productPictureList) {
					// 상품 사진 삭제
					int rowCount2 = productPictureBO.deleteProductPicture(productId, productPicture.getImagePath());
					count += rowCount2;
				}
				
				if (count == productPictureList.size()) {
					result.put("code", 1);
					result.put("result", "success");
				} else if (count == 0) {
					result.put("code", 500);
					result.put("errorMessage", "상품 사진 삭제에 전체 실패했습니다");
				} else {
					result.put("code", 500);
					result.put("errorMessage", "상품 사진 삭제에 일부 실패했습니다");
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

}
