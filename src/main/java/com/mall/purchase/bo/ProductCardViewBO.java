package com.mall.purchase.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.product.bo.ProductBO;
import com.mall.purchase.model.ProductCardView;

@Service
public class ProductCardViewBO {
	
	@Autowired
	private ProductBO productBO;
	
	// 상품 카드 생성 (상품 상세 id)
	public ProductCardView generateProductCardViewByDetailId(int productId, int detailId, int amount, Integer wishListId) {
		ProductCardView productCardView = new ProductCardView();
		
		productCardView.setProduct(productBO.getProductById(productId));
		productCardView.setProductPicture(productBO.getProductPictureListByProductId(productId).get(0));
		productCardView.setProductDetail(productBO.getProductDetailById(detailId));
		productCardView.setAmount(amount);
		productCardView.setWishListId(wishListId);
		
		return productCardView;
	}
	
	// 상품 카드 생성 (상품 상세 색상, 사이즈)
	public ProductCardView generateProductCardViewByColorSize(int productId, String color, String size, int amount) {
		ProductCardView productCardView = new ProductCardView();
		
		productCardView.setProduct(productBO.getProductById(productId));
		productCardView.setProductPicture(productBO.getProductPictureListByProductId(productId).get(0));
		productCardView.setProductDetail(productBO.getProductDetailByProductIdColorSize(productId, color, size));
		productCardView.setAmount(amount);
		
		return productCardView;
	}

}
