package com.mall.purchase.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mall.purchase.model.Purchase;
import com.mall.purchase.model.PurchaseProduct;

@Repository
public interface PurchaseDAO {
	
	// 구매 목록 전체
	public List<Purchase> selectPurchaseListAll();
	
	// 구매 목록
	public List<Purchase> selectPurchaseList(int userId);
	
	// 구매 조회
	public Purchase selectPurchaseById(int id);
	
	// 구매 추가
	public void insertPurchase(Purchase purchase);
	
	// 구매 취소
	public int updatePurchase(int id);
	
	// 구매 정보 수정
	public int updatePurchaseById(
			@Param("id") int id,
			@Param("courier") String courier,
			@Param("trackingNumber") String trackingNumber,
			@Param("cancellation") int cancellation);
	
	// 구매 상품 목록
	public List<PurchaseProduct> selectPurchaseProductList(int purchaseId);
	
	// 구매 상품 목록 (상품 id)
	public List<PurchaseProduct> selectPurchaseProductListByProductId(int productId);
	
	// 구매 상품 조회
	public PurchaseProduct selectPurchaseProductById(int id);
	
	// 구매 상품 추가
	public int insertPurchaseProduct(
			@Param("userId") int userId,
			@Param("purchaseId") int purchaseId,
			@Param("productId") int productId,
			@Param("productDetailId") int productDetailId,
			@Param("amount") int amount);
	
	// 구매 상품 환불
	public int updatePurchaseProductRefund(int id);
	
	// 구매 상품 교환
	public int updatePurchaseProductExchange(
			@Param("id") int id,
			@Param("productDetailId") int productDetailId);
	
	// 구매 상품 확정
	public int updatePurchaseProductComplete(int id);
	
	// 구매 상품 후기
	public int updatePurchaseProductReview(
			@Param("id") int id,
			@Param("star") int star,
			@Param("review") String review,
			@Param("imagePath") String imagePath);
	
	// 구매 상품 정보 수정
	public int updatePurchaseProductById(
			@Param("id") int id,
			@Param("refund") int refund,
			@Param("exchange") int exchange,
			@Param("completion") int completion);
	
	// 구매 상품 후기 삭제
	public int updatePurchaseProductReviewNull(int id);

}
