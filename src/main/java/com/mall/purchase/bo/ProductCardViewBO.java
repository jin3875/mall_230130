package com.mall.purchase.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.product.bo.ProductBO;
import com.mall.product.bo.ProductDetailBO;
import com.mall.product.bo.ProductPictureBO;
import com.mall.purchase.model.ProductCardView;

@Service
public class ProductCardViewBO {
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private ProductPictureBO productPictureBO;
	
	@Autowired
	private ProductDetailBO productDetailBO;
	
	// 상품 카드 생성 (상품 상세 id)
	public ProductCardView generateProductCardViewByDetailId(int productId, int detailId, int amount, Integer wishListId) {
		ProductCardView productCardView = new ProductCardView();
		
		productCardView.setProduct(productBO.getProductById(productId));
		productCardView.setProductPicture(productPictureBO.getProductPictureListByProductId(productId).get(0));
		productCardView.setProductDetail(productDetailBO.getProductDetailById(detailId));
		productCardView.setAmount(amount);
		productCardView.setWishListId(wishListId);
		
		return productCardView;
	}
	
	// 상품 카드 생성 (상품 상세 색상, 사이즈)
	public ProductCardView generateProductCardViewByColorSize(int productId, String color, String size, int amount) {
		ProductCardView productCardView = new ProductCardView();
		
		productCardView.setProduct(productBO.getProductById(productId));
		productCardView.setProductPicture(productPictureBO.getProductPictureListByProductId(productId).get(0));
		productCardView.setProductDetail(productDetailBO.getProductDetailByProductIdColorSize(productId, color, size));
		productCardView.setAmount(amount);
		
		return productCardView;
	}

}
