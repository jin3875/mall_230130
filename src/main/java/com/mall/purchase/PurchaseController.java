package com.mall.purchase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.product.bo.ProductBO;
import com.mall.product.bo.ProductDetailBO;
import com.mall.product.bo.ProductPictureBO;
import com.mall.purchase.bo.WishListBO;
import com.mall.purchase.model.PurchaseProductView;
import com.mall.purchase.model.WishList;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/purchase")
@Controller
public class PurchaseController {
	
	@Autowired
	private WishListBO wishListBO;
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private ProductPictureBO productPictureBO;
	
	@Autowired
	private ProductDetailBO productDetailBO;
	
	// 장바구니 화면
	@GetMapping("/wish_list_view")
	public String wishListView(HttpSession session, Model model) {
		// 장바구니 목록
		List<WishList> wishListList = wishListBO.getWishListList((int)session.getAttribute("userId"));
		
		List<PurchaseProductView> purchaseProductViewList = new ArrayList<>();
		
		for (WishList wishList : wishListList) {
			PurchaseProductView purchaseProductView = new PurchaseProductView();
			
			purchaseProductView.setProduct(productBO.getProductById(wishList.getProductId()));
			purchaseProductView.setProductPicture(productPictureBO.getProductPictureListByProductId(wishList.getProductId()).get(0));
			purchaseProductView.setProductDetail(productDetailBO.getProductDetailById(wishList.getProductDetailId()));
			purchaseProductView.setAmount(wishList.getAmount());
			
			purchaseProductViewList.add(purchaseProductView);
		}
		
		model.addAttribute("purchaseProductViewList", purchaseProductViewList);
		
		model.addAttribute("viewName", "purchase/wishList");
		return "template/layout";
	}

}
