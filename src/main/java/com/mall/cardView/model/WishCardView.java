package com.mall.cardView.model;

public class WishCardView {
	
	private ProductDetailCardView productDetailCardView;
	private int amount;
	private Integer wishListId;
	
	public ProductDetailCardView getProductDetailCardView() {
		return productDetailCardView;
	}
	public void setProductDetailCardView(ProductDetailCardView productDetailCardView) {
		this.productDetailCardView = productDetailCardView;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Integer getWishListId() {
		return wishListId;
	}
	public void setWishListId(Integer wishListId) {
		this.wishListId = wishListId;
	}

}
