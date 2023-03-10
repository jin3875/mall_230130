package com.mall.purchase.model;

import java.util.List;

public class PurchaseCardView {
	
	private Purchase purchase;
	private List<PurchaseProductCardView> purchaseProductCardViewList;
	
	public Purchase getPurchase() {
		return purchase;
	}
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	public List<PurchaseProductCardView> getPurchaseProductCardViewList() {
		return purchaseProductCardViewList;
	}
	public void setPurchaseProductCardViewList(List<PurchaseProductCardView> purchaseProductCardViewList) {
		this.purchaseProductCardViewList = purchaseProductCardViewList;
	}

}
