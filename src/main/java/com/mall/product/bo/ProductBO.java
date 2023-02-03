package com.mall.product.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.product.dao.ProductDAO;
import com.mall.product.model.Product;

@Service
public class ProductBO {
	
	@Autowired
	ProductDAO productDAO;
	
	// 상품 목록
	public List<Product> getProductList() {
		return productDAO.selectProductList();
	}

}
