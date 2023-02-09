package com.mall.purchase.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.purchase.dao.PurchaseProductDAO;

@Service
public class PurchaseProductBO {
	
	@Autowired
	private PurchaseProductDAO purchaseProductDAO;
	
	// 구매 상품 추가
	public int addPurchaseProduct(int userId, int purchaseId, int productId, int productDetailId, int amount) {
		return purchaseProductDAO.insertPurchaseProduct(userId, purchaseId, productId, productDetailId, amount);
	}

}
