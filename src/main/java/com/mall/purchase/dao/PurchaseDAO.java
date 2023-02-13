package com.mall.purchase.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mall.purchase.model.Purchase;

@Repository
public interface PurchaseDAO {
	
	// 판매 목록
	public List<Purchase> selectPurchaseListAll();
	
	// 구매 목록
	public List<Purchase> selectPurchaseList(int userId);
	
	// 구매 조회
	public Purchase selectPurchaseById(int id);
	
	// 구매 추가
	public void insertPurchase(Purchase purchase);
	
	// 구매 취소
	public int updatePurchase(int id);

}
