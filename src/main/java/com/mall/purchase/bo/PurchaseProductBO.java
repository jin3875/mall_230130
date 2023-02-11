package com.mall.purchase.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.purchase.dao.PurchaseProductDAO;
import com.mall.purchase.model.PurchaseProduct;

@Service
public class PurchaseProductBO {
	
	@Autowired
	private PurchaseProductDAO purchaseProductDAO;
	
	// 구매 상품 목록
	public List<PurchaseProduct> getPurchaseProductList(int purchaseId) {
		return purchaseProductDAO.selectPurchaseProductList(purchaseId);
	}
	
	// 구매 상품 조회
	public PurchaseProduct getPurchaseProductById(int id) {
		return purchaseProductDAO.selectPurchaseProductById(id);
	}
	
	// 구매 상품 추가
	public int addPurchaseProduct(int userId, int purchaseId, int productId, int productDetailId, int amount) {
		return purchaseProductDAO.insertPurchaseProduct(userId, purchaseId, productId, productDetailId, amount);
	}
	
	// 구매 상품 환불
	public int updatePurchaseProductRefund(int id) {
		return purchaseProductDAO.updatePurchaseProductRefund(id);
	}
	
	// 구매 상품 교환
	public int updatePurchaseProductExchange(int id, int productDetailId) {
		return purchaseProductDAO.updatePurchaseProductExchange(id, productDetailId);
	}
	
	// 구매 상품 확정
	public int updatePurchaseProductComplete(int id) {
		return purchaseProductDAO.updatePurchaseProductComplete(id);
	}

}
