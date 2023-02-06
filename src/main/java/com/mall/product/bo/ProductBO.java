package com.mall.product.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.product.dao.ProductDAO;
import com.mall.product.model.Product;

@Service
public class ProductBO {
	
	@Autowired
	ProductDAO productDAO;
	
	// 상품 목록
	public List<Product> getProductList() {
		return productDAO.selectProductList();
	}
	
	// 상품 목록 (카테고리)
	public List<Product> getProductListByCategory(String category) {
		return productDAO.selectProductListByCategory(category);
	}
	
	// 상품 조회
	public Product getProductById(int id) {
		return productDAO.selectProductById(id);
	}
	
	// 상품 추가
	public void addProduct(Product product) {
		productDAO.insertProduct(product);
	}
	
	// 상품 수정
	public int updateProduct(int id, String category, String name, int price, String detail, int state) {
		return productDAO.updateProduct(id, category, name, price, detail, state);
	}
	
	// 상품 삭제
	public int deleteProduct(int id) {
		return productDAO.deleteProduct(id);
	}

}
