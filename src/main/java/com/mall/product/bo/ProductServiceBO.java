package com.mall.product.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mall.common.FileManagerService;
import com.mall.product.dao.ProductDAO;
import com.mall.product.model.Product;
import com.mall.product.model.ProductCardView;
import com.mall.product.model.ProductDetail;
import com.mall.product.model.ProductDetailCardView;
import com.mall.product.model.ProductPicture;

@Service
public class ProductServiceBO {
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	// 상품 목록 (판매 중)
	public List<ProductCardView> generateProductCardViewListOnSale() {
		List<ProductCardView> productCardViewList = new ArrayList<>();
		
		// 상품 목록 (판매 중)
		List<Product> productList = productBO.getProductListOnSale();
		
		for (Product product : productList) {
			ProductCardView productCardView = new ProductCardView();
			
			productCardView.setProduct(product);
			productCardView.setProductPictureList(productBO.getProductPictureListByProductId(product.getId()));
			productCardView.setProductDetailList(productBO.getProductDetailList(product.getId()));
			
			productCardViewList.add(productCardView);
		}
		
		return productCardViewList;
	}
	
	// 카테고리 상품 목록 (판매 중)
	public List<ProductCardView> generateProductCardViewListOnSaleByCategory(String category) {
		List<ProductCardView> productCardViewList = new ArrayList<>();
		
		// 카테고리 상품 목록 (판매 중)
		List<Product> productList = productBO.getProductListOnSaleByCategory(category);
		
		for (Product product : productList) {
			ProductCardView productCardView = new ProductCardView();
			
			productCardView.setProduct(product);
			productCardView.setProductPictureList(productBO.getProductPictureListByProductId(product.getId()));
			productCardView.setProductDetailList(productBO.getProductDetailList(product.getId()));
			
			productCardViewList.add(productCardView);
		}
		
		return productCardViewList;
	}
	
	// 상품 조회
	public ProductCardView generateProductCardViewByProductId(int productId) {
		ProductCardView productCardView = new ProductCardView();
		
		productCardView.setProduct(productBO.getProductById(productId));
		productCardView.setProductPictureList(productBO.getProductPictureListByProductId(productId));
		productCardView.setProductDetailList(productBO.getProductDetailList(productId));
		
		return productCardView;
	}
	
	// 상품 검색
	public List<ProductCardView> generateProductCardViewListBySearch(String keyword, Integer minPrice, Integer maxPrice) {
		List<ProductCardView> productCardViewList = new ArrayList<>();
		
		if (minPrice == null && maxPrice == null) {
			// 상품 목록 (판매 중)
			List<Product> productList = productBO.getProductListOnSale();
			
			for (Product product : productList) {
				if (product.getName().contains(keyword)) {
					ProductCardView productCardView = new ProductCardView();
					
					productCardView.setProduct(product);
					productCardView.setProductPictureList(productBO.getProductPictureListByProductId(product.getId()));
					productCardView.setProductDetailList(productBO.getProductDetailList(product.getId()));
					
					productCardViewList.add(productCardView);
				}
			}
		} else {
			// 상품 목록 (판매 중 & 가격대)
			List<Product> productList = productBO.getProductListOnSaleByMinPriceMaxPrice(minPrice, maxPrice);
			
			for (Product product : productList) {
				if (product.getName().contains(keyword)) {
					ProductCardView productCardView = new ProductCardView();
					
					productCardView.setProduct(product);
					productCardView.setProductPictureList(productBO.getProductPictureListByProductId(product.getId()));
					productCardView.setProductDetailList(productBO.getProductDetailList(product.getId()));
					
					productCardViewList.add(productCardView);
				}
			}
		}
		
		return productCardViewList;
	}
	
	// 상품 추가
	@Transactional
	public boolean generateAddProduct(String loginId, String category, String name, int price, String detail, int state, List<MultipartFile> fileList) {
		Product product = new Product();
		product.setCategory(category);
		product.setName(name);
		product.setPrice(price);
		product.setDetail(detail);
		product.setState(state);
		
		// 상품 추가
		productBO.addProduct(product);
		int productId = product.getId();
		
		if (fileList != null) {
			for (MultipartFile file : fileList) {
				// 상품 사진 추가
				addProductPicture(loginId, productId, file);
			}
		}
		
		return true;
	}
	
	// 상품 수정
	@Transactional
	public boolean generateUpdateProduct(String loginId, int productId, String category, String name, int price, String detail, int state, List<MultipartFile> fileList) {
		// 상품 수정
		productBO.updateProduct(productId, category, name, price, detail, state);
		
		if (fileList != null) {
			// 상품 사진 목록
			List<ProductPicture> productPictureList = productBO.getProductPictureListByProductId(productId);
			
			for (ProductPicture productPicture : productPictureList) {
				// 상품 사진 삭제
				deleteProductPicture(productId, productPicture.getImagePath());
			}
			
			for (MultipartFile file : fileList) {
				// 상품 사진 추가
				addProductPicture(loginId, productId, file);
			}
		}
		
		return true;
	}
	
	// 상품 삭제
	@Transactional
	public boolean generateDeleteProduct(int productId) {
		// 상품 삭제
		productBO.deleteProduct(productId);
		
		// 상품 사진 목록
		List<ProductPicture> productPictureList = productBO.getProductPictureListByProductId(productId);
		
		for (ProductPicture productPicture : productPictureList) {
			// 상품 사진 삭제
			deleteProductPicture(productId, productPicture.getImagePath());
		}
		
		// 상품 상세 목록
		List<ProductDetail> productDetailList = productBO.getProductDetailList(productId);
		
		for (ProductDetail productDetail : productDetailList) {
			// 상품 상세 삭제
			productBO.deleteProductDetail(productDetail.getId());
		}
		
		return true;
	}
	
	// 상품 사진 추가
	public int addProductPicture(String userLoginId, int productId, MultipartFile file) {
		String imagePath = fileManagerService.saveFile(userLoginId, file);
		
		return productDAO.insertProductPicture(productId, imagePath);
	}
	
	// 상품 사진 삭제
	public int deleteProductPicture(int productId, String imagePath) {
		fileManagerService.deleteFile(imagePath);
		
		return productDAO.deleteProductPicture(productId);
	}
	
	// 상품 상세 조회
	public ProductDetailCardView generateProductDetailCardViewByProductIdProductDetailId(int productId, int productDetailId) {
		ProductDetailCardView productDetailCardView = new ProductDetailCardView();
		
		productDetailCardView.setProduct(productBO.getProductById(productId));
		productDetailCardView.setProductPicture(productBO.getProductPictureListByProductId(productId).get(0));
		productDetailCardView.setProductDetail(productBO.getProductDetailById(productDetailId));
		
		return productDetailCardView;
	}

}
