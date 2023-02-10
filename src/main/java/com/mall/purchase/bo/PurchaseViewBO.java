package com.mall.purchase.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.purchase.model.Purchase;
import com.mall.purchase.model.PurchaseView;

@Service
public class PurchaseViewBO {
	
	@Autowired
	private PurchaseBO purchaseBO;
	
	@Autowired
	private PurchaseProductViewBO purchaseProductViewBO;
	
	// 구매 + 구매 상품 목록
	public List<PurchaseView> generatePurchaseViewList(int userId) {
		List<PurchaseView> purchaseViewList = new ArrayList<>();
		
		// 구매 목록
		List<Purchase> purchaseList = purchaseBO.getPurchaseList(userId);
		
		for (Purchase purchase : purchaseList) {
			PurchaseView purchaseView = new PurchaseView();
			
			purchaseView.setPurchase(purchase);
			
			// 구매 상품 카드 목록
			purchaseView.setPurchaseProductViewList(purchaseProductViewBO.generatePurchaseProductViewList(purchase.getId()));
			
			purchaseViewList.add(purchaseView);
		}
		
		return purchaseViewList;
	}

}
