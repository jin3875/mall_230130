package com.mall.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mall.product.model.Product;
import com.mall.product.model.ProductDetail;
import com.mall.product.model.ProductPicture;

@Repository
public interface ProductDAO {
	
	// 상품 목록
	public List<Product> selectProductList();
	
	// 상품 목록 (판매 중)
	public List<Product> selectProductListOnSale();
	
	// 상품 목록 (판매 중 & 가격대)
	public List<Product> selectProductListOnSaleByMinPriceMaxPrice(
			@Param("minPrice") Integer minPrice,
			@Param("maxPrice") Integer maxPrice);
	
	// 카테고리 상품 목록 (판매 중)
	public List<Product> selectProductListOnSaleByCategory(String category);
	
	// 상품 조회
	public Product selectProductById(int id);
	
	// 상품 추가
	public void insertProduct(Product product);
	
	// 상품 수정
	public int updateProduct(
			@Param("id") int id,
			@Param("category") String category,
			@Param("name") String name,
			@Param("price") int price,
			@Param("detail") String detail,
			@Param("state") int state);
	
	// 상품 삭제
	public int deleteProduct(int id);
	
	// 상품 사진 목록
	public List<ProductPicture> selectProductPictureListByProductId(int productId);
	
	// 상품 사진 추가
	public int insertProductPicture(
			@Param("productId") int productId,
			@Param("imagePath") String imagePath);
	
	// 상품 사진 삭제
	public int deleteProductPicture(int productId);
	
	// 상품 상세 목록
	public List<ProductDetail> selectProductDetailList(int productId);
	
	// 상품 상세 조회
	public ProductDetail selectProductDetailById(int id);
	
	// 상품 상세 조회 (색상, 사이즈)
	public ProductDetail selectProductDetailByProductIdColorSize(
			@Param("productId") int productId,
			@Param("color") String color,
			@Param("size") String size);
	
	// 상품 상세 추가
	public int insertProductDetail(
			@Param("productId") int productId,
			@Param("color") String color,
			@Param("size") String size,
			@Param("amount") int amount);
	
	// 상품 상세 수정
	public int updateProductDetail(
			@Param("id") int id,
			@Param("color") String color,
			@Param("size") String size,
			@Param("amount") int amount);
	
	// 상품 상세 삭제
	public int deleteProductDetail(int id);

}
