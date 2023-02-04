package com.mall.productPicture.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPictureDAO {
	
	// 상품 사진 추가
	public int insertProductPicture(
			@Param("productId") int productId,
			@Param("imagePath") String imagePath);

}
