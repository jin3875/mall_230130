package com.mall.purchase.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.purchase.dao.PurchaseDAO;
import com.mall.purchase.model.Purchase;

@Service
public class PurchaseBO {
	
	@Autowired
	private PurchaseDAO purchaseDAO;
	
	// 구매 추가
	public void addPurchase(Purchase purchase) {
		purchaseDAO.insertPurchase(purchase);
	}

}
