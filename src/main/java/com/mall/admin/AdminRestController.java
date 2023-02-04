package com.mall.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mall.product.bo.ProductBO;
import com.mall.product.model.Product;
import com.mall.productPicture.bo.ProductPictureBO;

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
				for (MultipartFile file : fileList) {
					// 상품 사진 추가
					int rowCount = productPictureBO.addProductPicture((String)session.getAttribute("userLoginId"), productId, file);
					
					if (rowCount > 0) {
						result.put("code", 1);
						result.put("result", "success");
					} else {
						result.put("code", 500);
						result.put("errorMessage", "상품 사진 등록에 실패했습니다");
					}
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

}
