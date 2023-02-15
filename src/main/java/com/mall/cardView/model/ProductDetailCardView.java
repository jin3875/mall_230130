package com.mall.cardView.model;

import com.mall.product.model.Product;
import com.mall.product.model.ProductDetail;
import com.mall.product.model.ProductPicture;

public class ProductDetailCardView {
	
	private Product product;
	private ProductPicture productPicture;
	private ProductDetail productDetail;
	
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
