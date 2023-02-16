package com.mall.wishList.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.product.bo.ProductBO;
import com.mall.product.bo.ProductServiceBO;
import com.mall.product.model.ProductDetailCardView;
import com.mall.wishList.model.WishList;
import com.mall.wishList.model.WishListCardView;

@Service
public class WishListServiceBO {
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private WishListBO wishListBO;
	
	@Autowired
	private ProductServiceBO productServiceBO;
	
	// 구매할 상품 (장바구니)
	public WishListCardView generateWishListCardViewByDetailId(int productId, int productDetailId, int amount, Integer wishListId) {
		WishListCardView wishListCardView = new WishListCardView();
		
		wishListCardView.setProductDetailCardView(productServiceBO.generateProductDetailCardViewByProductIdProductDetailId(productId, productDetailId));
		wishListCardView.setAmount(amount);
		wishListCardView.setWishListId(wishListId);
		
		return wishListCardView;
	}
	
	// 구매할 상품 (장바구니 X)
	public WishListCardView generateWishListCardViewByColorSize(int productId, String color, String size, int amount) {
		WishListCardView wishListCardView = new WishListCardView();
		
		ProductDetailCardView productDetailCardView = new ProductDetailCardView();
		productDetailCardView.setProduct(productBO.getProductById(productId));
		productDetailCardView.setProductPicture(productBO.getProductPictureListByProductId(productId).get(0));
		productDetailCardView.setProductDetail(productBO.getProductDetailByProductIdColorSize(productId, color, size));
		
		wishListCardView.setProductDetailCardView(productDetailCardView);
		wishListCardView.setAmount(amount);
		
		return wishListCardView;
	}
	
	// 장바구니 목록
	public List<WishListCardView> generateWishListCardViewListForWishList(int userId) {
		List<WishListCardView> wishListCardViewList = new ArrayList<>();
		
		// 장바구니 목록
		List<WishList> wishListList = wishListBO.getWishListList(userId);
		
		for (WishList wishList : wishListList) {
			// 구매할 상품 (장바구니)
			wishListCardViewList.add(generateWishListCardViewByDetailId(wishList.getProductId(), wishList.getProductDetailId(), wishList.getAmount(), wishList.getId()));
		}
		
		return wishListCardViewList;
	}
	
	// 구매하기 목록
	public List<WishListCardView> generateWishListCardViewListForPurchase(Integer productId, String color, String size, Integer amount, List<Integer> idList) {
		List<WishListCardView> wishListCardViewList = new ArrayList<>();
		
		if (idList == null) {
			// 구매할 상품 (장바구니 X)
			wishListCardViewList.add(generateWishListCardViewByColorSize(productId, color, size, amount));
		} else {
			for (int id : idList) {
				// 장바구니 조회
				WishList wishList = wishListBO.getWishListById(id);
				
				// 구매할 상품 (장바구니)
				wishListCardViewList.add(generateWishListCardViewByDetailId(wishList.getProductId(), wishList.getProductDetailId(), wishList.getAmount(), id));
			}
		}
		
		return wishListCardViewList;
	}

}
