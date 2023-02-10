package com.mall.purchase.model;

import java.util.List;

// 구매 + 구매 상품
public class PurchaseView {
	
	public Purchase purchase;
	public List<PurchaseProductView> purchaseProductViewList;
	
	public Purchase getPurchase() {
		return purchase;
	}
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	public List<PurchaseProductView> getPurchaseProductViewList() {
		return purchaseProductViewList;
	}
	public void setPurchaseProductViewList(List<PurchaseProductView> purchaseProductViewList) {
		this.purchaseProductViewList = purchaseProductViewList;
	}

}
