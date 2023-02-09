package com.mall.purchase.dao;

import org.springframework.stereotype.Repository;

import com.mall.purchase.model.Purchase;

@Repository
public interface PurchaseDAO {
	
	// 구매 추가
	public void insertPurchase(Purchase purchase);

}
