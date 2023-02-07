package com.mall.purchase.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.purchase.dao.WishListDAO;

@Service
public class WishListBO {
	
	@Autowired
	private WishListDAO wishListDAO;

}
