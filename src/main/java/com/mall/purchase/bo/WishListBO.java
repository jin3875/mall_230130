package com.mall.purchase.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.purchase.dao.WishListDAO;
import com.mall.purchase.model.WishList;

@Service
public class WishListBO {
	
	@Autowired
	private WishListDAO wishListDAO;
	
	// 장바구니 목록
	public List<WishList> getWishListList(int userId) {
		return wishListDAO.selectWishListList(userId);
	}
	
	// 장바구니 추가
	public int addWishList(int userId, int productId, int productDetailId, int amount) {
		return wishListDAO.insertWishList(userId, productId, productDetailId, amount);
	}

}
