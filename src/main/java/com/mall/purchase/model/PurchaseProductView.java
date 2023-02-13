package com.mall.purchase.model;

import com.mall.product.model.Product;
import com.mall.product.model.ProductDetail;
import com.mall.product.model.ProductPicture;
import com.mall.user.model.User;

// 구매 상품 + 유저 + 상품 + 상품 사진 + 상품 상세
public class PurchaseProductView {
	
	private PurchaseProduct purchaseProduct;
	private User user;
	private Product product;
	private ProductPicture productPicture;
	private ProductDetail productDetail;
	
	public PurchaseProduct getPurchaseProduct() {
		return purchaseProduct;
	}
	public void setPurchaseProduct(PurchaseProduct purchaseProduct) {
		this.purchaseProduct = purchaseProduct;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
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

}
