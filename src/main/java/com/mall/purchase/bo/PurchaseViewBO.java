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
	
	// 판매 + 판매 상품 목록
	public List<PurchaseView> generatePurchaseViewListAll() {
		List<PurchaseView> purchaseViewList = new ArrayList<>();
		
		// 판매 목록
		List<Purchase> purchaseList = purchaseBO.getPurchaseListAll();
		
		for (Purchase purchase : purchaseList) {
			PurchaseView purchaseView = new PurchaseView();
			
			purchaseView.setPurchase(purchase);
			
			// 구매 상품 카드 목록
			purchaseView.setPurchaseProductViewList(purchaseProductViewBO.generatePurchaseProductViewList(purchase.getId()));
			
			purchaseViewList.add(purchaseView);
		}
		
		return purchaseViewList;
	}
	
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
	
	// 구매 + 구매 상품 목록 조회
	public PurchaseView generatePurchaseViewByPurchaseId(int purchaseId, int userId) {
		PurchaseView purchaseView = new PurchaseView();
		
		// 구매 조회
		purchaseView.setPurchase(purchaseBO.getPurchaseById(purchaseId, userId));
		
		// 구매 상품 카드 목록
		purchaseView.setPurchaseProductViewList(purchaseProductViewBO.generatePurchaseProductViewList(purchaseId));
		
		return purchaseView;
	}

}
