package com.mall.wishList.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.wishList.dao.WishListDAO;
import com.mall.wishList.model.WishList;

@Service
public class WishListBO {
	
	@Autowired
	private WishListDAO wishListDAO;
	
	// 장바구니 목록
	public List<WishList> getWishListList(int userId) {
		return wishListDAO.selectWishListList(userId);
	}
	
	// 장바구니 조회
	public WishList getWishListById(int id) {
		return wishListDAO.selectWishListById(id);
	}
	
	// 장바구니 추가
	public int addWishList(int userId, int productId, int productDetailId, int amount) {
		return wishListDAO.insertWishList(userId, productId, productDetailId, amount);
	}
	
	// 장바구니 삭제
	public int deleteWishList(int userId, List<Integer> idList) {
		return wishListDAO.deleteWishList(userId, idList);
	}

}
