package com.mall.purchase.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseProductDAO {
	
	// 구매 상품 추가
	public int insertPurchaseProduct(
			@Param("userId") int userId,
			@Param("purchaseId") int purchaseId,
			@Param("productId") int productId,
			@Param("productDetailId") int productDetailId,
			@Param("amount") int amount);

}
