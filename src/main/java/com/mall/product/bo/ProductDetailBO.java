package com.mall.product.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.product.dao.ProductDetailDAO;
import com.mall.product.model.ProductDetail;

@Service
public class ProductDetailBO {
	
	@Autowired
	private ProductDetailDAO productDetailDAO;
	
	// 상품 상세 목록
	public List<ProductDetail> getProductDetailList(int productId) {
		return productDetailDAO.selectProductDetailList(productId);
	}
	
	// 상품 상세 조회
	public ProductDetail getProductDetailById(int id) {
		return productDetailDAO.selectProductDetailById(id);
	}
	
	// 상품 상세 추가
	public int addProductDetail(int productId, String color, String size, int amount) {
		return productDetailDAO.insertProductDetail(productId, color, size, amount);
	}
	
	// 상품 상세 수정
	public int updateProductDetail(int id, String color, String size, int amount) {
		return productDetailDAO.updateProductDetail(id, color, size, amount);
	}
	
	// 상품 상세 삭제
	public int deleteProductDetail(int id) {
		return productDetailDAO.deleteProductDetail(id);
	}

}
