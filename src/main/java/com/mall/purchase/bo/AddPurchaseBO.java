package com.mall.purchase.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.product.bo.ProductDetailBO;

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

}
