package com.mall.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.product.model.Product;

@Repository
public interface ProductDAO {
	
	// 상품 목록
	public List<Product> selectProductList();
	
	// 상품 추가
	public void insertProduct(Product product);

}
