package com.mall.purchase.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.product.bo.ProductDetailBO;
import com.mall.purchase.model.Purchase;

@Service
public class AddPurchaseBO {
	
	@Autowired
	private ProductDetailBO productDetailBO;
	
	@Autowired
	private WishListBO wishListBO;
	
	@Autowired
	private PurchaseBO purchaseBO;
	
	@Autowired
	private PurchaseProductBO purchaseProductBO;
	
	// 구매하기
	@Transactional(rollbackFor = {Exception.class})
	public boolean generateAddPurchase(
			List<Integer> wishList, List<String> productList, int userId, int totalPrice, String name,
			String phoneNumber, String postcode, String address, String detailAddress, String message
	) throws Exception {
		Purchase purchase = new Purchase();
		purchase.setUserId(userId);
		purchase.setTotalPrice(totalPrice);
		purchase.setName(name);
		purchase.setPhoneNumber(phoneNumber);
		purchase.setPostcode(postcode);
		purchase.setAddress(address);
		purchase.setDetailAddress(detailAddress);
		purchase.setMessage(message);
		
		// 구매 추가
		purchaseBO.addPurchase(purchase);
		int purchaseId = purchase.getId();
		
		for (String product : productList) {
			int productId = Integer.parseInt(product.split("/")[0]);
			int detailId = Integer.parseInt(product.split("/")[1]);
			int amount = Integer.parseInt(product.split("/")[2]);
			
			// 재고 수량 체크
			if (productDetailBO.getProductDetailById(detailId).getAmount() < amount) {
				throw new Exception();
			}
			
			// 구매 상품 추가
			purchaseProductBO.addPurchaseProduct(userId, purchaseId, productId, detailId, amount);
			
			// 상품 상세 재고 수량 수정
			productDetailBO.updateProductDetail(detailId, null, null, productDetailBO.getProductDetailById(detailId).getAmount() - amount);
		}
		
		if (wishList != null) {
			// 장바구니 삭제
			wishListBO.deleteWishList(userId, wishList);
		}
		
		return true;
	}

}
