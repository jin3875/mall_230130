package com.mall.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mall.product.bo.ProductBO;
import com.mall.product.bo.ProductDetailBO;
import com.mall.product.bo.ProductViewBO;
import com.mall.product.model.Product;
import com.mall.product.model.ProductDetail;
import com.mall.product.model.ProductView;
import com.mall.purchase.bo.PurchaseViewBO;
import com.mall.purchase.model.PurchaseView;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private ProductDetailBO productDetailBO;
	
	@Autowired
	private ProductViewBO productViewBO;
	
	@Autowired
	private PurchaseViewBO purchaseViewBO;
	
	/**
	 * 관리자 - 상품 목록 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/admin_product_list_view")
	public String adminProductListView(Model model) {
		// 상품 목록
		List<Product> productList = productBO.getProductList();
		model.addAttribute("productList", productList);
		
		model.addAttribute("title", "상품 목록");
		model.addAttribute("viewName", "admin/adminProductList");
		return "template/layout2";
	}
	
	/**
	 * 관리자 - 상품 등록 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/admin_product_create_view")
	public String adminProductCreateView(Model model) {
		model.addAttribute("title", "상품 등록");
		model.addAttribute("viewName", "admin/adminProductCreate");
		return "template/layout2";
	}
	
	/**
	 * 관리자 - 상품 수정 화면
	 * @param productId
	 * @param model
	 * @return
	 */
	@GetMapping("/admin_product_update_view")
	public String adminProductUpdateView(
			@RequestParam("productId") int productId,
			Model model
	) {
		// 상품 + 상품 사진 + 상품 상세 조회
		ProductView productView = productViewBO.generateProductViewById(productId);
		model.addAttribute("productView", productView);
		
		model.addAttribute("title", "상품 수정");
		model.addAttribute("viewName", "admin/adminProductUpdate");
		return "template/layout2";
	}
	
	/**
	 * 관리자 - 상품 상세 목록 화면
	 * @param productId
	 * @param model
	 * @return
	 */
	@GetMapping("/admin_detail_list_view")
	public String adminDetailListView(
			@RequestParam("productId") int productId,
			Model model
	) {
		// 상품 + 상품 사진 + 상품 상세 조회
		ProductView productView = productViewBO.generateProductViewById(productId);
		model.addAttribute("productView", productView);
		
		model.addAttribute("title", "상품 상세 목록");
		model.addAttribute("viewName", "admin/adminDetailList");
		return "template/layout2";
	}
	
	/**
	 * 관리자 - 상품 상세 등록 화면
	 * @param productId
	 * @param model
	 * @return
	 */
	@GetMapping("/admin_detail_create_view")
	public String adminDetailCreateView(
			@RequestParam("productId") int productId,
			Model model
	) {
		// 상품 조회
		Product product = productBO.getProductById(productId);
		model.addAttribute("product", product);
		
		model.addAttribute("title", "상품 상세 등록");
		model.addAttribute("viewName", "admin/adminDetailCreate");
		return "template/layout2";
	}
	
	/**
	 * 관리자 - 상품 상세 수정 화면
	 * @param productId
	 * @param detailId
	 * @param model
	 * @return
	 */
	@GetMapping("/admin_detail_update_view")
	public String adminDetailUpdateView(
			@RequestParam("productId") int productId,
			@RequestParam("detailId") int detailId,
			Model model
	) {
		// 상품 조회
		Product product = productBO.getProductById(productId);
		model.addAttribute("product", product);
		
		// 상품 상세 조회
		ProductDetail productDetail = productDetailBO.getProductDetailById(detailId);
		model.addAttribute("productDetail", productDetail);
		
		model.addAttribute("title", "상품 상세 수정");
		model.addAttribute("viewName", "admin/adminDetailUpdate");
		return "template/layout2";
	}
	
	/**
	 * 관리자 - 판매 목록 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/admin_sale_list_view")
	public String adminSaleListView(Model model) {
		// 판매 + 판매 상품 목록
		List<PurchaseView> purchaseViewList = purchaseViewBO.generatePurchaseViewListAll();
		model.addAttribute("purchaseViewList", purchaseViewList);
		
		model.addAttribute("title", "판매 목록");
		model.addAttribute("viewName", "admin/adminSaleList");
		return "template/layout2";
	}
	
	/**
	 * 관리자 - 후기 목록 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/admin_review_list_view")
	public String adminReviewListView(Model model) {
		// 판매 + 판매 상품 목록
		List<PurchaseView> purchaseViewList = purchaseViewBO.generatePurchaseViewListAll();
		model.addAttribute("purchaseViewList", purchaseViewList);
		
		model.addAttribute("title", "후기 목록");
		model.addAttribute("viewName", "admin/adminReviewList");
		return "template/layout2";
	}

}
