package com.mall.purchase.model;

import com.mall.product.model.Product;
import com.mall.product.model.ProductDetail;
import com.mall.product.model.ProductPicture;

// 구매할 상품 + 상품 사진 + 상품 상세 + 수량 (+ 장바구니 번호)
public class BeforePurchaseView {
	
	private Product product;
	private ProductPicture productPicture;
	private ProductDetail productDetail;
	private int amount;
	public Integer wishListId;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public ProductPicture getProductPicture() {
		return productPicture;
	}
	public void setProductPicture(ProductPicture productPicture) {
		this.productPicture = productPicture;
	}
	public ProductDetail getProductDetail() {
		return productDetail;
	}
	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
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
