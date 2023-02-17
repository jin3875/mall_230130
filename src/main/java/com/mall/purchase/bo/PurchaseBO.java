package com.mall.purchase.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.purchase.dao.PurchaseDAO;
import com.mall.purchase.model.Purchase;
import com.mall.purchase.model.PurchaseProduct;

@Service
public class PurchaseBO {
	
	@Autowired
	private PurchaseDAO purchaseDAO;
	
	// 구매 목록 전체
	public List<Purchase> getPurchaseListAll() {
		return purchaseDAO.selectPurchaseListAll();
	}
	
	// 구매 목록
	public List<Purchase> getPurchaseList(int userId, String startDate, String endDate) {
		return purchaseDAO.selectPurchaseList(userId, startDate, endDate);
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
	
	// 구매 정보 수정
	public int updatePurchaseById(int id, String courier, String trackingNumber, int cancellation) {
		return purchaseDAO.updatePurchaseById(id, courier, trackingNumber, cancellation);
	}
	
	// 구매 상품 목록
	public List<PurchaseProduct> getPurchaseProductList(int purchaseId) {
		return purchaseDAO.selectPurchaseProductList(purchaseId);
	}
	
	// 구매 상품 목록 (상품 id)
	public List<PurchaseProduct> getPurchaseProductListByProductId(int productId) {
		return purchaseDAO.selectPurchaseProductListByProductId(productId);
	}
	
	// 구매 상품 조회
	public PurchaseProduct getPurchaseProductById(int id) {
		return purchaseDAO.selectPurchaseProductById(id);
	}
	
	// 구매 상품 추가
	public int addPurchaseProduct(int userId, int purchaseId, int productId, int productDetailId, int amount) {
		return purchaseDAO.insertPurchaseProduct(userId, purchaseId, productId, productDetailId, amount);
	}
	
	// 구매 상품 환불
	public int updatePurchaseProductRefund(int id) {
		return purchaseDAO.updatePurchaseProductRefund(id);
	}
	
	// 구매 상품 교환
	public int updatePurchaseProductExchange(int id, int productDetailId) {
		return purchaseDAO.updatePurchaseProductExchange(id, productDetailId);
	}
	
	// 구매 상품 확정
	public int updatePurchaseProductComplete(int id) {
		return purchaseDAO.updatePurchaseProductComplete(id);
	}
	
	// 구매 상품 정보 수정
	public int updatePurchaseProductById(int id, int refund, int exchange, int completion) {
		return purchaseDAO.updatePurchaseProductById(id, refund, exchange, completion);
	}

}
