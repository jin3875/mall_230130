package com.mall.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mall.product.model.Product;

@Repository
public interface ProductDAO {
	
	// 상품 목록
	public List<Product> selectProductList();
	
	// 상품 목록 (판매 중)
	public List<Product> selectProductListOnSale();
	
	// 상품 목록 (판매 중 & 가격대)
	public List<Product> selectProductListOnSaleByMinPriceMaxPrice(
			@Param("minPrice") Integer minPrice,
			@Param("maxPrice") Integer maxPrice);
	
	// 카테고리 상품 목록 (판매 중)
	public List<Product> selectProductListOnSaleByCategory(String category);
	
	// 상품 조회
	public Product selectProductById(int id);
	
	// 상품 추가
	public void insertProduct(Product product);
	
	// 상품 수정
	public int updateProduct(
			@Param("id") int id,
			@Param("category") String category,
			@Param("name") String name,
			@Param("price") int price,
			@Param("detail") String detail,
			@Param("state") int state);
	
	// 상품 삭제
	public int deleteProduct(int id);

}
