package com.mall.purchase.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.purchase.dao.PurchaseProductDAO;

@Service
public class PurchaseProductBO {
	
	@Autowired
	private PurchaseProductDAO purchaseProductDAO;

}