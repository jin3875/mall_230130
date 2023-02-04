package com.mall.productPicture.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mall.common.FileManagerService;
import com.mall.productPicture.dao.ProductPictureDAO;

@Service
public class ProductPictureBO {
	
	@Autowired
	private ProductPictureDAO productPictureDAO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	// 상품 사진 추가
	public int addProductPicture(String userLoginId, int productId, MultipartFile file) {
		String imagePath = fileManagerService.saveFile(userLoginId, file);
		
		return productPictureDAO.insertProductPicture(productId, imagePath);
	}

}
