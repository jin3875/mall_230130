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
	
	// 상품 뷰 목록
	public List<PurchaseProductView> generatePurchaseProductViewList(int purchaseId) {
		List<PurchaseProductView> purchaseProductViewList = new ArrayList<>();
		
		// 구매 상품 목록
		List<PurchaseProduct> purchaseProductList = purchaseProductBO.getPurchaseProductList(purchaseId);
		
		for (PurchaseProduct purchaseProduct : purchaseProductList) {
			// 상품 뷰 추가 (상품 상세 id)
			purchaseProductViewList.add(generatePurchaseProductViewByDetailId(purchaseProduct.getProductId(), purchaseProduct.getProductDetailId(), purchaseProduct.getAmount(), null));
			
		}
		
		return purchaseProductViewList;
	}
	
	// 상품 뷰 추가 (상품 상세 id)
	public PurchaseProductView generatePurchaseProductViewByDetailId(int productId, int detailId, int amount, Integer wishListId) {
		PurchaseProductView purchaseProductView = new PurchaseProductView();
		
		purchaseProductView.setProduct(productBO.getProductById(productId));
		purchaseProductView.setProductPicture(productPictureBO.getProductPictureListByProductId(productId).get(0));
		purchaseProductView.setProductDetail(productDetailBO.getProductDetailById(detailId));
		purchaseProductView.setAmount(amount);
		purchaseProductView.setWishListId(wishListId);
		
		return purchaseProductView;
	}
	
	// 상품 뷰 추가 (상품 상세 색상, 사이즈)
	public PurchaseProductView generatePurchaseProductViewByColorSize(int productId, String color, String size, int amount) {
		PurchaseProductView purchaseProductView = new PurchaseProductView();
		
		purchaseProductView.setProduct(productBO.getProductById(productId));
		purchaseProductView.setProductPicture(productPictureBO.getProductPictureListByProductId(productId).get(0));
		purchaseProductView.setProductDetail(productDetailBO.getProductDetailByProductIdColorSize(productId, color, size));
		purchaseProductView.setAmount(amount);
		
		return purchaseProductView;
	}

}
