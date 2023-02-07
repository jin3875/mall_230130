package com.mall.purchase.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.purchase.dao.PurchaseDAO;

@Service
public class PurchaseBO {
	
	@Autowired
	private PurchaseDAO purchaseDAO;

}
