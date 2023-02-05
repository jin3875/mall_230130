package com.mall.product.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mall.common.FileManagerService;
import com.mall.product.dao.ProductPictureDAO;
import com.mall.product.model.ProductPicture;

@Service
public class ProductPictureBO {
	
	@Autowired
	private ProductPictureDAO productPictureDAO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	// 상품 사진 조회
	public List<ProductPicture> getProductPictureListByProductId(int productId) {
		return productPictureDAO.selectProductPictureListByProductId(productId);
	}
	
	// 상품 사진 추가
	public int addProductPicture(String userLoginId, int productId, MultipartFile file) {
		String imagePath = fileManagerService.saveFile(userLoginId, file);
		
		return productPictureDAO.insertProductPicture(productId, imagePath);
	}
	
	// 상품 사진 삭제
	public int deleteProductPicture(int productId, String imagePath) {
		fileManagerService.deleteFile(imagePath);
		
		return productPictureDAO.deleteProductPicture(productId);
	}

}
