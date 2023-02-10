package com.mall.purchase.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.purchase.model.Purchase;

@Repository
public interface PurchaseDAO {
	
	// 구매 목록
	public List<Purchase> selectPurchaseList(int userId);
	
	// 구매 추가
	public void insertPurchase(Purchase purchase);

}
