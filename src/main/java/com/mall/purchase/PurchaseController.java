package com.mall.purchase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	private ProductBO productBO;
	
	@Autowired
	private ProductPictureBO productPictureBO;
	
	@Autowired
	private ProductDetailBO productDetailBO;
	
	@Autowired
	private WishListBO wishListBO;
	
	/**
	 * 장바구니 화면
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/wish_list_view")
	public String wishListView(HttpSession session, Model model) {
		int userId = (int)session.getAttribute("userId");
		
		List<PurchaseProductView> purchaseProductViewList = new ArrayList<>();
		
		// 장바구니 목록
		List<WishList> wishListList = wishListBO.getWishListList(userId);
		
		for (WishList wishList : wishListList) {
			PurchaseProductView purchaseProductView = new PurchaseProductView();
			
			purchaseProductView.setProduct(productBO.getProductById(wishList.getProductId()));
			purchaseProductView.setProductPicture(productPictureBO.getProductPictureListByProductId(wishList.getProductId()).get(0));
			purchaseProductView.setProductDetail(productDetailBO.getProductDetailById(wishList.getProductDetailId()));
			purchaseProductView.setAmount(wishList.getAmount());
			purchaseProductView.setWishListId(wishList.getId());
			
			purchaseProductViewList.add(purchaseProductView);
		}
		
		model.addAttribute("purchaseProductViewList", purchaseProductViewList);
		
		model.addAttribute("viewName", "purchase/wishList");
		return "template/layout";
	}
	
	/**
	 * 구매하기 화면
	 * @param productId
	 * @param color
	 * @param size
	 * @param amount
	 * @param idList
	 * @param model
	 * @return
	 */
	@GetMapping("/purchase_view")
	public String purchaseView(
			@RequestParam(value="productId", required=false) Integer productId,
			@RequestParam(value="color", required=false) String color,
			@RequestParam(value="size", required=false) String size,
			@RequestParam(value="amount", required=false) Integer amount,
			@RequestParam(value="wishListId", required=false) List<Integer> idList,
			Model model
	) {
		List<PurchaseProductView> purchaseProductViewList = new ArrayList<>();
		
		if (idList == null) {
			PurchaseProductView purchaseProductView = new PurchaseProductView();
			
			purchaseProductView.setProduct(productBO.getProductById(productId));
			purchaseProductView.setProductPicture(productPictureBO.getProductPictureListByProductId(productId).get(0));
			purchaseProductView.setProductDetail(productDetailBO.getProductDetailByProductIdColorSize(productId, color, size));
			purchaseProductView.setAmount(amount);
			
			purchaseProductViewList.add(purchaseProductView);
		} else {
			for (int id : idList) {
				PurchaseProductView purchaseProductView = new PurchaseProductView();
				
				// 장바구니 조회
				WishList wishList = wishListBO.getWishListById(id);
				
				purchaseProductView.setProduct(productBO.getProductById(wishList.getProductId()));
				purchaseProductView.setProductPicture(productPictureBO.getProductPictureListByProductId(wishList.getProductId()).get(0));
				purchaseProductView.setProductDetail(productDetailBO.getProductDetailById(wishList.getProductDetailId()));
				purchaseProductView.setAmount(wishList.getAmount());
				
				purchaseProductViewList.add(purchaseProductView);
			}
		}
		
		model.addAttribute("purchaseProductViewList", purchaseProductViewList);
		
		model.addAttribute("viewName", "purchase/purchase");
		return "template/layout";
	}

}
