package com.mall.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mall.product.bo.ProductViewBO;
import com.mall.product.model.ProductView;

@RequestMapping("/product")
@Controller
public class ProductController {
	
	@Autowired
	private ProductViewBO productViewBO;
	
	/**
	 * 메인 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/product_main_view")
	public String productMainView(Model model) {
		// 상품 + 상품 사진 목록 (판매 중)
		List<ProductView> productViewList = productViewBO.generateProductViewList();
		model.addAttribute("productViewList", productViewList);
		
		model.addAttribute("viewName", "product/productMain");
		return "template/layout";
	}
	
	// 카테고리 화면
	@GetMapping("/product_category_view")
	public String productCategoryView(
			@RequestParam("category") String category,
			Model model
	) {
		// 카테고리 상품 + 상품 사진 목록 (판매 중)
		List<ProductView> productViewList = productViewBO.generateProductViewListByCategory(category);
		model.addAttribute("productViewList", productViewList);
		
		model.addAttribute("category", category);
		model.addAttribute("viewName", "product/productCategory");
		return "template/layout";
	}

}
