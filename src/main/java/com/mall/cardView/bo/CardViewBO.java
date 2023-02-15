package com.mall.cardView.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.cardView.model.ProductCardView;
import com.mall.cardView.model.ProductDetailCardView;
import com.mall.cardView.model.PurchaseCardView;
import com.mall.cardView.model.PurchaseProductCardView;
import com.mall.cardView.model.WishCardView;
import com.mall.product.bo.ProductBO;
import com.mall.product.model.Product;
import com.mall.purchase.bo.PurchaseBO;
import com.mall.purchase.model.Purchase;
import com.mall.purchase.model.PurchaseProduct;
import com.mall.user.bo.UserBO;

@Service
public class CardViewBO {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private PurchaseBO purchaseBO;
	
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
	
	// 상품 상세 조회
	public ProductDetailCardView generateProductDetailCardViewByProductIdProductDetailId(int productId, int productDetailId) {
		ProductDetailCardView productDetailCardView = new ProductDetailCardView();
		
		productDetailCardView.setProduct(productBO.getProductById(productId));
		productDetailCardView.setProductPicture(productBO.getProductPictureListByProductId(productId).get(0));
		productDetailCardView.setProductDetail(productBO.getProductDetailById(productDetailId));
		
		return productDetailCardView;
	}
	
	// 구매할 상품 (장바구니)
	public WishCardView generateWishCardViewByDetailId(int productId, int productDetailId, int amount, Integer wishListId) {
		WishCardView wishCardView = new WishCardView();
		
		wishCardView.setProductDetailCardView(generateProductDetailCardViewByProductIdProductDetailId(productId, productDetailId));
		wishCardView.setAmount(amount);
		wishCardView.setWishListId(wishListId);
		
		return wishCardView;
	}
	
	// 구매할 상품 (장바구니 X)
	public WishCardView generateWishCardViewByColorSize(int productId, String color, String size, int amount) {
		WishCardView wishCardView = new WishCardView();
		
		ProductDetailCardView productDetailCardView = new ProductDetailCardView();
		productDetailCardView.setProduct(productBO.getProductById(productId));
		productDetailCardView.setProductPicture(productBO.getProductPictureListByProductId(productId).get(0));
		productDetailCardView.setProductDetail(productBO.getProductDetailByProductIdColorSize(productId, color, size));
		
		wishCardView.setProductDetailCardView(productDetailCardView);
		wishCardView.setAmount(amount);
		
		return wishCardView;
	}
	
	// 구매 상품 목록 (구매 id)
	public List<PurchaseProductCardView> generatePurchaseProductCardViewList(int purchaseId) {
		List<PurchaseProductCardView> purchaseProductCardViewList = new ArrayList<>();
		
		// 구매 상품 목록
		List<PurchaseProduct> purchaseProductList = purchaseBO.getPurchaseProductList(purchaseId);
		
		for (PurchaseProduct purchaseProduct : purchaseProductList) {
			PurchaseProductCardView purchaseProductCardView = new PurchaseProductCardView();
			
			purchaseProductCardView.setUser(userBO.getUserById(purchaseProduct.getUserId()));
			purchaseProductCardView.setPurchaseProduct(purchaseProduct);
			purchaseProductCardView.setProductDetailCardView(generateProductDetailCardViewByProductIdProductDetailId(purchaseProduct.getProductId(), purchaseProduct.getProductDetailId()));
			
			purchaseProductCardViewList.add(purchaseProductCardView);
		}
		
		return purchaseProductCardViewList;
	}
	
	// 구매 상품 목록 (상품 id)
	public List<PurchaseProductCardView> generatePurchaseProductCardViewListByProductId(int productId) {
		List<PurchaseProductCardView> purchaseProductCardViewList = new ArrayList<>();
		
		// 구매 상품 목록 (상품 id)
		List<PurchaseProduct> purchaseProductList = purchaseBO.getPurchaseProductListByProductId(productId);
		
		for (PurchaseProduct purchaseProduct : purchaseProductList) {
			PurchaseProductCardView purchaseProductCardView = new PurchaseProductCardView();
			
			purchaseProductCardView.setUser(userBO.getUserById(purchaseProduct.getUserId()));
			purchaseProductCardView.setPurchaseProduct(purchaseProduct);
			purchaseProductCardView.setProductDetailCardView(generateProductDetailCardViewByProductIdProductDetailId(purchaseProduct.getProductId(), purchaseProduct.getProductDetailId()));
			
			purchaseProductCardViewList.add(purchaseProductCardView);
		}
		
		return purchaseProductCardViewList;
	}
	
	// 구매 상품 조회
	public PurchaseProductCardView generatePurchaseProductCardViewByPurchaseProductId(int purchaseProductId) {
		PurchaseProductCardView purchaseProductCardView = new PurchaseProductCardView();
		
		// 구매 상품 조회
		PurchaseProduct purchaseProduct = purchaseBO.getPurchaseProductById(purchaseProductId);
		
		purchaseProductCardView.setUser(userBO.getUserById(purchaseProduct.getUserId()));
		purchaseProductCardView.setPurchaseProduct(purchaseProduct);
		purchaseProductCardView.setProductDetailCardView(generateProductDetailCardViewByProductIdProductDetailId(purchaseProduct.getProductId(), purchaseProduct.getProductDetailId()));
		
		return purchaseProductCardView;
	}
	
	// 구매 + 구매 상품 목록 전체
	public List<PurchaseCardView> generatePurchaseCardViewListAll() {
		List<PurchaseCardView> purchaseCardViewList = new ArrayList<>();
		
		// 구매 목록 전체
		List<Purchase> purchaseList = purchaseBO.getPurchaseListAll();
		
		for (Purchase purchase : purchaseList) {
			PurchaseCardView purchaseCardView = new PurchaseCardView();
			
			purchaseCardView.setPurchase(purchase);
			purchaseCardView.setPurchaseProductCardViewList(generatePurchaseProductCardViewList(purchase.getId()));
			
			purchaseCardViewList.add(purchaseCardView);
		}
		
		return purchaseCardViewList;
	}
	
	// 구매 + 구매 상품 목록
	public List<PurchaseCardView> generatePurchaseCardViewList(int userId) {
		List<PurchaseCardView> purchaseCardViewList = new ArrayList<>();
		
		// 구매 목록
		List<Purchase> purchaseList = purchaseBO.getPurchaseList(userId);
		
		for (Purchase purchase : purchaseList) {
			PurchaseCardView purchaseCardView = new PurchaseCardView();
			
			purchaseCardView.setPurchase(purchase);
			purchaseCardView.setPurchaseProductCardViewList(generatePurchaseProductCardViewList(purchase.getId()));
			
			purchaseCardViewList.add(purchaseCardView);
		}
		
		return purchaseCardViewList;
	}
	
	// 구매 + 구매 상품 목록 조회
	public PurchaseCardView generatePurchaseCardViewByPurchaseId(int purchaseId) {
		PurchaseCardView purchaseCardView = new PurchaseCardView();
		
		purchaseCardView.setPurchase(purchaseBO.getPurchaseById(purchaseId));
		purchaseCardView.setPurchaseProductCardViewList(generatePurchaseProductCardViewList(purchaseId));
		
		return purchaseCardView;
	}
	
	// 구매 + 구매 상품 조회
	public PurchaseCardView generatePurchaseCardViewByPurchaseIdPurchaseProductId(int purchaseId, int purchaseProductId) {
		PurchaseCardView purchaseCardView = new PurchaseCardView();
		
		purchaseCardView.setPurchase(purchaseBO.getPurchaseById(purchaseId));
		
		List<PurchaseProductCardView> purchaseProductCardViewList = new ArrayList<>();
		purchaseProductCardViewList.add(generatePurchaseProductCardViewByPurchaseProductId(purchaseProductId));
		purchaseCardView.setPurchaseProductCardViewList(purchaseProductCardViewList);
		
		return purchaseCardView;
	}

}
