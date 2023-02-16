package com.mall.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mall.product.bo.ProductServiceBO;
import com.mall.product.model.ProductCardView;
import com.mall.purchase.bo.PurchaseServiceBO;
import com.mall.purchase.model.PurchaseProductCardView;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/product")
@Controller
public class ProductController {
	
	@Autowired
	private ProductServiceBO productServiceBO;
	
	@Autowired
	private PurchaseServiceBO purchaseServiceBO;
	
	/**
	 * 메인 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/product_main_view")
	public String productMainView(Model model) {
		// 상품 목록 (판매 중)
		List<ProductCardView> productCardViewList = productServiceBO.generateProductCardViewListOnSale();
		model.addAttribute("productCardViewList", productCardViewList);
		
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
		// 카테고리 상품 목록 (판매 중)
		List<ProductCardView> productCardViewList = productServiceBO.generateProductCardViewListOnSaleByCategory(category);
		model.addAttribute("productCardViewList", productCardViewList);
		
		model.addAttribute("category", category);
		model.addAttribute("viewName", "product/productCategory");
		return "template/layout";
	}
	
	// 최근 본 상품 목록
	List<Map<String, Object>> recentList = new ArrayList<>();
	
	/**
	 * 상품 상세 화면
	 * @param productId
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/product_detail_view/{productId}")
	public String productDetailView(
			@PathVariable("productId") int productId,
			HttpSession session,
			Model model
	) {
		// 상품 조회
		ProductCardView productCardView = productServiceBO.generateProductCardViewByProductId(productId);
		model.addAttribute("productCardView", productCardView);
		
		// 구매 상품 목록 (상품 id)
		List<PurchaseProductCardView> purchaseProductCardViewList = purchaseServiceBO.generatePurchaseProductCardViewListByProductId(productId);
		model.addAttribute("purchaseProductCardViewList", purchaseProductCardViewList);
		
		// 최근 본 상품 목록 추가
		Map<String, Object> recent = new HashMap<>();
		recent.put("imagePath", productCardView.getProductPictureList().get(0).getImagePath());
		recent.put("productId", productId);
		recentList.add(0, recent);
		
		for (int i = 1; i < recentList.size(); i++) {
			if (recentList.get(i).get("productId") == recentList.get(0).get("productId")) {
				recentList.remove(i);
				break;
			}
		}
		
		session.setAttribute("recentList", recentList);
		
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
		// 상품 검색
		List<ProductCardView> productCardViewList = productServiceBO.generateProductCardViewListBySearch(keyword, minPrice, maxPrice);
		model.addAttribute("productCardViewList", productCardViewList);
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);
		
		model.addAttribute("viewName", "product/productSearch");
		return "template/layout";
	}

}
