package com.mall.purchase.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.product.bo.ProductBO;
import com.mall.product.bo.ProductDetailBO;
import com.mall.product.bo.ProductPictureBO;
import com.mall.purchase.model.PurchaseProduct;
import com.mall.purchase.model.PurchaseProductView;

@Service
public class PurchaseProductViewBO {
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private ProductPictureBO productPictureBO;
	
	@Autowired
	private ProductDetailBO productDetailBO;
	
	@Autowired
	private PurchaseProductBO purchaseProductBO;
	
	// 구매 상품 카드 목록
	public List<PurchaseProductView> generatePurchaseProductViewList(int purchaseId) {
		List<PurchaseProductView> purchaseProductViewList = new ArrayList<>();
		
		// 구매 상품 목록
		List<PurchaseProduct> purchaseProductList = purchaseProductBO.getPurchaseProductList(purchaseId);
		
		for (PurchaseProduct purchaseProduct : purchaseProductList) {
			PurchaseProductView purchaseProductView = new PurchaseProductView();
			
			purchaseProductView.setPurchaseProduct(purchaseProduct);
			purchaseProductView.setProduct(productBO.getProductById(purchaseProduct.getProductId()));
			purchaseProductView.setProductPicture(productPictureBO.getProductPictureListByProductId(purchaseProduct.getProductId()).get(0));
			purchaseProductView.setProductDetail(productDetailBO.getProductDetailById(purchaseProduct.getProductDetailId()));
			
			purchaseProductViewList.add(purchaseProductView);
		}
		
		return purchaseProductViewList;
	}
	
	// 구매 상품 카드 조회
	public PurchaseProductView generatePurchaseProductViewById(int purchaseProductId) {
		PurchaseProductView purchaseProductView = new PurchaseProductView();
		
		// 구매 상품 조회
		PurchaseProduct purchaseProduct = purchaseProductBO.getPurchaseProductById(purchaseProductId);
		
		purchaseProductView.setPurchaseProduct(purchaseProduct);
		purchaseProductView.setProduct(productBO.getProductById(purchaseProduct.getProductId()));
		purchaseProductView.setProductPicture(productPictureBO.getProductPictureListByProductId(purchaseProduct.getProductId()).get(0));
		purchaseProductView.setProductDetail(productDetailBO.getProductDetailById(purchaseProduct.getProductDetailId()));
		
		return purchaseProductView;
	}

}
