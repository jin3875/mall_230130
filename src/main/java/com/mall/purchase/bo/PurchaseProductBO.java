package com.mall.purchase.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mall.common.FileManagerService;
import com.mall.purchase.dao.PurchaseProductDAO;
import com.mall.purchase.model.PurchaseProduct;

@Service
public class PurchaseProductBO {
	
	@Autowired
	private PurchaseProductDAO purchaseProductDAO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	// 구매 상품 목록
	public List<PurchaseProduct> getPurchaseProductList(int purchaseId) {
		return purchaseProductDAO.selectPurchaseProductList(purchaseId);
	}
	
	// 구매 상품 목록 (상품 id)
	public List<PurchaseProduct> getPurchaseProductListByProductId(int productId) {
		return purchaseProductDAO.selectPurchaseProductListByProductId(productId);
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
	
	// 구매 상품 후기 작성
	public int updatePurchaseProductReview(String userLoginId, int id, int star, String review, MultipartFile file) {
		String imagePath = null;
		
		if (file != null) {
			imagePath = fileManagerService.saveFile(userLoginId, file);
		}
		
		return purchaseProductDAO.updatePurchaseProductReview(id, star, review, imagePath);
	}
	
	// 구매 상품 후기 수정
	public int updatePurchaseProductReviewAgain(String userLoginId, int id, int star, String review, MultipartFile file) {
		String imagePath = null;
		
		if (file != null) {
			imagePath = fileManagerService.saveFile(userLoginId, file);
			
			if (imagePath != null && getPurchaseProductById(id).getImagePath() != null) {
				fileManagerService.deleteFile(getPurchaseProductById(id).getImagePath());
			}
		}
		
		return purchaseProductDAO.updatePurchaseProductReview(id, star, review, imagePath);
	}
	
	// 구매 상품 정보 수정
	public int updatePurchaseProductById(int id, int refund, int exchange, int completion) {
		return purchaseProductDAO.updatePurchaseProductById(id, refund, exchange, completion);
	}
	
	// 구매 상품 후기 삭제
	public int updatePurchaseProductReviewNull(int id) {
		if (getPurchaseProductById(id).getImagePath() != null) {
			fileManagerService.deleteFile(getPurchaseProductById(id).getImagePath());
		}
		
		return purchaseProductDAO.updatePurchaseProductReviewNull(id);
	}

}
