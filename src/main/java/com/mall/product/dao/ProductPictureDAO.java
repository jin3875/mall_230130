package com.mall.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mall.product.model.ProductPicture;

@Repository
public interface ProductPictureDAO {
	
	// 상품 사진 목록
	public List<ProductPicture> selectProductPictureListByProductId(int productId);
	
	// 상품 사진 추가
	public int insertProductPicture(
			@Param("productId") int productId,
			@Param("imagePath") String imagePath);
	
	// 상품 사진 삭제
	public int deleteProductPicture(int productId);

}
