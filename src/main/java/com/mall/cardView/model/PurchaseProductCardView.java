package com.mall.cardView.model;

import com.mall.purchase.model.PurchaseProduct;
import com.mall.user.model.User;

public class PurchaseProductCardView {
	
	private User user;
	private PurchaseProduct purchaseProduct;
	private ProductDetailCardView productDetailCardView;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public PurchaseProduct getPurchaseProduct() {
		return purchaseProduct;
	}
	public void setPurchaseProduct(PurchaseProduct purchaseProduct) {
		this.purchaseProduct = purchaseProduct;
	}
	public ProductDetailCardView getProductDetailCardView() {
		return productDetailCardView;
	}
	public void setProductDetailCardView(ProductDetailCardView productDetailCardView) {
		this.productDetailCardView = productDetailCardView;
	}

}
