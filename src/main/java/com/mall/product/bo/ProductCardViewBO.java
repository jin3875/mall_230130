package com.mall.product.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.product.model.Product;
import com.mall.product.model.ProductCardView;
import com.mall.product.model.ProductDetail;
import com.mall.product.model.ProductPicture;

@Service
public class ProductCardViewBO {
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private ProductPictureBO productPictureBO;
	
	@Autowired
	private ProductDetailBO productDetailBO;
	
	// 카테고리 상품 + 상품 사진 목록 (판매 중)
	public List<ProductCardView> generateProductCardViewListByCategory(String category) {
		List<ProductCardView> productCardViewList = new ArrayList<>();
		
		// 카테고리 상품 목록 (판매 중)
		List<Product> productList = productBO.getProductListOnSaleByCategory(category);
		
		for (Product product : productList) {
			ProductCardView productCardView = new ProductCardView();
			
			productCardView.setProduct(product);
			
			// 상품 사진 목록
			List<ProductPicture> productPictureList = productPictureBO.getProductPictureListByProductId(product.getId());
			productCardView.setProductPictureList(productPictureList);
			
			// 상품 상세 목록
			List<ProductDetail> productDetailList = productDetailBO.getProductDetailList(product.getId());
			productCardView.setProductDetailList(productDetailList);
			
			productCardViewList.add(productCardView);
		}
		
		return productCardViewList;
	}

}
