package com.mall.product.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.product.dao.ProductDAO;
import com.mall.product.model.Product;
import com.mall.product.model.ProductDetail;
import com.mall.product.model.ProductPicture;

@Service
public class ProductBO {
	
	@Autowired
	ProductDAO productDAO;
	
	// 상품 목록
	public List<Product> getProductList() {
		return productDAO.selectProductList();
	}
	
	// 상품 목록 (판매 중)
	public List<Product> getProductListOnSale() {
		return productDAO.selectProductListOnSale();
	}
	
	// 상품 목록 (판매 중 & 가격대)
	public List<Product> getProductListOnSaleByMinPriceMaxPrice(Integer minPrice, Integer maxPrice) {
		return productDAO.selectProductListOnSaleByMinPriceMaxPrice(minPrice, maxPrice);
	}
	
	// 카테고리 상품 목록 (판매 중)
	public List<Product> getProductListOnSaleByCategory(String category) {
		return productDAO.selectProductListOnSaleByCategory(category);
	}
	
	// 상품 조회
	public Product getProductById(int id) {
		return productDAO.selectProductById(id);
	}
	
	// 상품 추가
	public void addProduct(Product product) {
		productDAO.insertProduct(product);
	}
	
	// 상품 수정
	public int updateProduct(int id, String category, String name, int price, String detail, int state) {
		return productDAO.updateProduct(id, category, name, price, detail, state);
	}
	
	// 상품 삭제
	public int deleteProduct(int id) {
		return productDAO.deleteProduct(id);
	}
	
	// 상품 사진 목록
	public List<ProductPicture> getProductPictureListByProductId(int productId) {
		return productDAO.selectProductPictureListByProductId(productId);
	}
	
	// 상품 상세 목록
	public List<ProductDetail> getProductDetailList(int productId) {
		return productDAO.selectProductDetailList(productId);
	}
	
	// 상품 상세 조회
	public ProductDetail getProductDetailById(int id) {
		return productDAO.selectProductDetailById(id);
	}
	
	// 상품 상세 조회 (색상, 사이즈)
	public ProductDetail getProductDetailByProductIdColorSize(int productId, String color, String size) {
		return productDAO.selectProductDetailByProductIdColorSize(productId, color, size);
	}
	
	// 상품 상세 추가
	public int addProductDetail(int productId, String color, String size, int amount) {
		return productDAO.insertProductDetail(productId, color, size, amount);
	}
	
	// 상품 상세 수정
	public int updateProductDetail(int id, String color, String size, int amount) {
		return productDAO.updateProductDetail(id, color, size, amount);
	}
	
	// 상품 상세 삭제
	public int deleteProductDetail(int id) {
		return productDAO.deleteProductDetail(id);
	}

}
