package com.mall.purchase.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mall.purchase.model.WishList;

@Repository
public interface WishListDAO {
	
	// 장바구니 목록
	public List<WishList> selectWishListList(int userId);
	
	// 장바구니 추가
	public int insertWishList(
			@Param("userId") int userId,
			@Param("productId") int productId,
			@Param("productDetailId") int productDetailId,
			@Param("amount") int amount);
	
	// 장바구니 삭제
	public int deleteWishList(
			@Param("userId") int userId,
			@Param("idList") List<Integer> idList);

}
