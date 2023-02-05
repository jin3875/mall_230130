package com.mall.product.model;

import java.util.List;

public class ProductView {
	
	private Product product;
	private List<ProductPicture> productPictureList;
	
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

}
