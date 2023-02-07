package com.mall.product.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.product.model.Product;
import com.mall.product.model.ProductDetail;
import com.mall.product.model.ProductPicture;
import com.mall.product.model.ProductView;

@Service
public class ProductViewBO {
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private ProductPictureBO productPictureBO;
	
	@Autowired
	private ProductDetailBO productDetailBO;
	
	// 상품 + 상품 사진 + 상품 상세 목록 (판매 중)
	public List<ProductView> generateProductViewListOnSale() {
		List<ProductView> productViewList = new ArrayList<>();
		
		// 상품 목록 (판매 중)
		List<Product> productList = productBO.getProductListOnSale();
		
		for (Product product : productList) {
			ProductView productView = new ProductView();
			
			productView.setProduct(product);
			
			// 상품 사진 목록
			List<ProductPicture> productPictureList = productPictureBO.getProductPictureListByProductId(product.getId());
			productView.setProductPictureList(productPictureList);
			
			// 상품 상세 목록
			List<ProductDetail> productDetailList = productDetailBO.getProductDetailList(product.getId());
			productView.setProductDetailList(productDetailList);
			
			productViewList.add(productView);
		}
		
		return productViewList;
	}
	
	// 카테고리 상품 + 상품 사진 + 상품 상세 목록 (판매 중)
	public List<ProductView> generateProductViewListOnSaleByCategory(String category) {
		List<ProductView> productViewList = new ArrayList<>();
		
		// 카테고리 상품 목록 (판매 중)
		List<Product> productList = productBO.getProductListOnSaleByCategory(category);
		
		for (Product product : productList) {
			ProductView productView = new ProductView();
			
			productView.setProduct(product);
			
			// 상품 사진 목록
			List<ProductPicture> productPictureList = productPictureBO.getProductPictureListByProductId(product.getId());
			productView.setProductPictureList(productPictureList);
			
			// 상품 상세 목록
			List<ProductDetail> productDetailList = productDetailBO.getProductDetailList(product.getId());
			productView.setProductDetailList(productDetailList);
			
			productViewList.add(productView);
		}
		
		return productViewList;
	}
	
	// 상품 + 상품 사진 + 상품 상세 조회
	public ProductView generateProductViewById(int id) {
		ProductView productView = new ProductView();
		
		// 상품 조회
		Product product = productBO.getProductById(id);
		productView.setProduct(product);
		
		// 상품 사진 목록
		List<ProductPicture> productPictureList = productPictureBO.getProductPictureListByProductId(id);
		productView.setProductPictureList(productPictureList);
		
		// 상품 상세 목록
		List<ProductDetail> productDetailList = productDetailBO.getProductDetailList(id);
		productView.setProductDetailList(productDetailList);
		
		return productView;
	}

}
