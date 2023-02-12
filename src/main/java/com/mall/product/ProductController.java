package com.mall.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mall.product.bo.ProductViewBO;
import com.mall.product.model.ProductView;
import com.mall.purchase.bo.ProductReviewViewBO;
import com.mall.purchase.model.ProductReviewView;

@RequestMapping("/product")
@Controller
public class ProductController {
	
	@Autowired
	private ProductViewBO productViewBO;
	
	@Autowired
	private ProductReviewViewBO productReviewViewBO;
	
	/**
	 * 메인 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/product_main_view")
	public String productMainView(Model model) {
		// 상품 + 상품 사진 + 상품 상세 목록 (판매 중)
		List<ProductView> productViewList = productViewBO.generateProductViewListOnSale();
		model.addAttribute("productViewList", productViewList);
		
		model.addAttribute("viewName", "product/productMain");
		return "template/layout";
	}
	
	/**
	 * 카테고리 화면
	 * @param category
	 * @param model
	 * @return
	 */
	@GetMapping("/product_category_view")
	public String productCategoryView(
			@RequestParam("category") String category,
			Model model
	) {
		// 카테고리 상품 + 상품 사진 + 상품 상세 목록 (판매 중)
		List<ProductView> productViewList = productViewBO.generateProductViewListOnSaleByCategory(category);
		model.addAttribute("productViewList", productViewList);
		
		model.addAttribute("category", category);
		model.addAttribute("viewName", "product/productCategory");
		return "template/layout";
	}
	
	/**
	 * 상품 상세 화면
	 * @param productId
	 * @param model
	 * @return
	 */
	@GetMapping("/product_detail_view/{productId}")
	public String productDetailView(
			@PathVariable("productId") int productId,
			Model model
	) {
		// 상품 + 상품 사진 + 상품 상세 조회
		ProductView productView = productViewBO.generateProductViewById(productId);
		model.addAttribute("productView", productView);
		
		// 후기 목록 (상품 id)
		List<ProductReviewView> productReviewViewList = productReviewViewBO.generateProductReviewViewListByProductId(productId);
		model.addAttribute("productReviewViewList", productReviewViewList);
		
		model.addAttribute("viewName", "product/productDetail");
		return "template/layout";
	}
	
	/**
	 * 검색 화면
	 * @param keyword
	 * @param minPrice
	 * @param maxPrice
	 * @param model
	 * @return
	 */
	@GetMapping("/product_search_view")
	public String productSearchView(
			@RequestParam("keyword") String keyword,
			@RequestParam(value="minPrice", required=false) Integer minPrice,
			@RequestParam(value="maxPrice", required=false) Integer maxPrice,
			Model model
	) {
		// 상품 + 상품 사진 + 상품 상세 검색
		List<ProductView> productViewList = productViewBO.generateProductViewListBySearch(keyword, minPrice, maxPrice);
		model.addAttribute("productViewList", productViewList);
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);
		
		model.addAttribute("viewName", "product/productSearch");
		return "template/layout";
	}

}
