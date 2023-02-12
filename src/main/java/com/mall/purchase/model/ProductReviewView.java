package com.mall.purchase.model;

import com.mall.user.model.User;

public class ProductReviewView {
	
	private User user;
	private PurchaseProduct purchaseProduct;
	
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

}
