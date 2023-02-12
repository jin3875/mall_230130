package com.mall.purchase.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.purchase.model.ProductReviewView;
import com.mall.purchase.model.PurchaseProduct;
import com.mall.user.bo.UserBO;

@Service
public class ProductReviewViewBO {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private PurchaseProductBO purchaseProductBO;
	
	// 후기 목록 (상품 id)
	public List<ProductReviewView> generateProductReviewViewListByProductId(int productId) {
		List<ProductReviewView> productReviewViewList = new ArrayList<>();
		
		// 구매 상품 목록 (상품 id)
		List<PurchaseProduct> purchaseProductList = purchaseProductBO.getPurchaseProductListByProductId(productId);
		
		for (PurchaseProduct purchaseProduct : purchaseProductList) {
			ProductReviewView productReviewView = new ProductReviewView();
			
			productReviewView.setPurchaseProduct(purchaseProduct);
			productReviewView.setUser(userBO.getUserById(purchaseProduct.getUserId()));
			
			productReviewViewList.add(productReviewView);
		}
		
		return productReviewViewList;
	}

}
