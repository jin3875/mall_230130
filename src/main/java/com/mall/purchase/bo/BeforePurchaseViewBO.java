package com.mall.purchase.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.product.bo.ProductBO;
import com.mall.product.bo.ProductDetailBO;
import com.mall.product.bo.ProductPictureBO;
import com.mall.purchase.model.BeforePurchaseView;

@Service
public class BeforePurchaseViewBO {
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private ProductPictureBO productPictureBO;
	
	@Autowired
	private ProductDetailBO productDetailBO;
	
	// 구매할 상품 뷰 추가 (상품 상세 id)
	public BeforePurchaseView addBeforePurchaseViewByDetailId(int productId, int detailId, int amount, int wishListId) {
		BeforePurchaseView beforePurchaseView = new BeforePurchaseView();
		
		beforePurchaseView.setProduct(productBO.getProductById(productId));
		beforePurchaseView.setProductPicture(productPictureBO.getProductPictureListByProductId(productId).get(0));
		beforePurchaseView.setProductDetail(productDetailBO.getProductDetailById(detailId));
		beforePurchaseView.setAmount(amount);
		beforePurchaseView.setWishListId(wishListId);
		
		return beforePurchaseView;
	}
	
	// 구매할 상품 뷰 추가 (상품 상세 색상, 사이즈)
	public BeforePurchaseView addBeforePurchaseViewByColorSize(int productId, String color, String size, int amount) {
		BeforePurchaseView beforePurchaseView = new BeforePurchaseView();
		
		beforePurchaseView.setProduct(productBO.getProductById(productId));
		beforePurchaseView.setProductPicture(productPictureBO.getProductPictureListByProductId(productId).get(0));
		beforePurchaseView.setProductDetail(productDetailBO.getProductDetailByProductIdColorSize(productId, color, size));
		beforePurchaseView.setAmount(amount);
		
		return beforePurchaseView;
	}

}
