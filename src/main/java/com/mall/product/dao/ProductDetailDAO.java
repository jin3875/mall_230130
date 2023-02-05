package com.mall.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mall.product.model.ProductDetail;

@Repository
public interface ProductDetailDAO {
	
	// 상품 상세 목록
	public List<ProductDetail> selectProductDetailList(int productId);
	
	// 상품 상세 조회
	
	
	// 상품 상세 추가
	public int insertProductDetail(
			@Param("productId") int productId,
			@Param("color") String color,
			@Param("size") String size,
			@Param("amount") int amount);

}
