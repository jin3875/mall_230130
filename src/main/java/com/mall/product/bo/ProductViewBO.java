package com.mall.product.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.product.model.Product;
import com.mall.product.model.ProductPicture;
import com.mall.product.model.ProductView;

@Service
public class ProductViewBO {
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private ProductPictureBO productPictureBO;
	
	// 상품 + 상품 사진 조회
	public ProductView generateProductView(int id) {
		ProductView productView = new ProductView();
		
		// 상품 조회
		Product product = productBO.getProductById(id);
		productView.setProduct(product);
		
		// 상품 사진 조회
		List<ProductPicture> productPictureList = productPictureBO.getProductPictureListByProductId(id);
		productView.setProductPictureList(productPictureList);
		
		return productView;
	}

}
