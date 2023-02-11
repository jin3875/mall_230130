package com.mall.purchase.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mall.purchase.model.PurchaseProduct;

@Repository
public interface PurchaseProductDAO {
	
	// 구매 상품 목록
	public List<PurchaseProduct> selectPurchaseProductList(int purchaseId);
	
	// 구매 상품 조회
	public PurchaseProduct selectPurchaseProductById(int id);
	
	// 구매 상품 추가
	public int insertPurchaseProduct(
			@Param("userId") int userId,
			@Param("purchaseId") int purchaseId,
			@Param("productId") int productId,
			@Param("productDetailId") int productDetailId,
			@Param("amount") int amount);
	
	// 구매 상품 환불
	public int updatePurchaseProductRefund(int id);
	
	// 구매 상품 교환
	public int updatePurchaseProductExchange(
			@Param("id") int id,
			@Param("productDetailId") int productDetailId);
	
	// 구매 상품 확정
	public int updatePurchaseProductComplete(int id);

}
