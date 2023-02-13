package com.mall.purchase.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.purchase.dao.PurchaseDAO;
import com.mall.purchase.model.Purchase;

@Service
public class PurchaseBO {
	
	@Autowired
	private PurchaseDAO purchaseDAO;
	
	// 판매 목록
	public List<Purchase> getPurchaseListAll() {
		return purchaseDAO.selectPurchaseListAll();
	}
	
	// 구매 목록
	public List<Purchase> getPurchaseList(int userId) {
		return purchaseDAO.selectPurchaseList(userId);
	}
	
	// 구매 조회
	public Purchase getPurchaseById(int id) {
		return purchaseDAO.selectPurchaseById(id);
	}
	
	// 구매 추가
	public void addPurchase(Purchase purchase) {
		purchaseDAO.insertPurchase(purchase);
	}
	
	// 구매 취소
	public int updatePurchase(int id) {
		return purchaseDAO.updatePurchase(id);
	}

}
