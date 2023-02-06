package com.mall.product.model;

import java.util.List;

public class ProductCardView {
	
	private Product product;
	private List<ProductPicture> productPictureList;
	private List<ProductDetail> productDetailList;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public List<ProductPicture> getProductPictureList() {
		return productPictureList;
	}
	public void setProductPictureList(List<ProductPicture> productPictureList) {
		this.productPictureList = productPictureList;
	}
	public List<ProductDetail> getProductDetailList() {
		return productDetailList;
	}
	public void setProductDetailList(List<ProductDetail> productDetailList) {
		this.productDetailList = productDetailList;
	}

}
