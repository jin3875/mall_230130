package com.mall.purchase.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mall.common.FileManagerService;
import com.mall.product.bo.ProductBO;
import com.mall.product.bo.ProductServiceBO;
import com.mall.purchase.dao.PurchaseDAO;
import com.mall.purchase.model.Purchase;
import com.mall.purchase.model.PurchaseCardView;
import com.mall.purchase.model.PurchaseProduct;
import com.mall.purchase.model.PurchaseProductCardView;
import com.mall.user.bo.UserBO;
import com.mall.wishList.bo.WishListBO;

@Service
public class PurchaseServiceBO {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private ProductBO productBO;
	
	@Autowired
	private ProductServiceBO productServiceBO;
	
	@Autowired
	private WishListBO wishListBO;
	
	@Autowired
	private PurchaseBO purchaseBO;
	
	@Autowired
	private PurchaseDAO purchaseDAO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	// 구매하기
	@Transactional(rollbackFor = {Exception.class})
	public boolean generateAddPurchase(
			List<Integer> wishList, List<String> productList, int userId, int totalPrice, String name,
			String phoneNumber, String postcode, String address, String detailAddress, String message
	) throws Exception {
		Purchase purchase = new Purchase();
		purchase.setUserId(userId);
		purchase.setTotalPrice(totalPrice);
		purchase.setName(name);
		purchase.setPhoneNumber(phoneNumber);
		purchase.setPostcode(postcode);
		purchase.setAddress(address);
		purchase.setDetailAddress(detailAddress);
		purchase.setMessage(message);
		
		// 구매 추가
		purchaseBO.addPurchase(purchase);
		int purchaseId = purchase.getId();
		
		for (String product : productList) {
			int productId = Integer.parseInt(product.split("/")[0]);
			int detailId = Integer.parseInt(product.split("/")[1]);
			int amount = Integer.parseInt(product.split("/")[2]);
			
			// 재고 수량 체크
			if (productBO.getProductDetailById(detailId).getAmount() < amount) {
				throw new Exception();
			}
			
			// 구매 상품 추가
			purchaseBO.addPurchaseProduct(userId, purchaseId, productId, detailId, amount);
			
			// 상품 상세 재고 수량 수정
			productBO.updateProductDetail(detailId, null, null, productBO.getProductDetailById(detailId).getAmount() - amount);
		}
		
		if (wishList != null) {
			// 장바구니 삭제
			wishListBO.deleteWishList(userId, wishList);
		}
		
		return true;
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
			purchaseProductCardView.setProductDetailCardView(productServiceBO.generateProductDetailCardViewByProductIdProductDetailId(purchaseProduct.getProductId(), purchaseProduct.getProductDetailId()));
			
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
			purchaseProductCardView.setProductDetailCardView(productServiceBO.generateProductDetailCardViewByProductIdProductDetailId(purchaseProduct.getProductId(), purchaseProduct.getProductDetailId()));
			
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
		purchaseProductCardView.setProductDetailCardView(productServiceBO.generateProductDetailCardViewByProductIdProductDetailId(purchaseProduct.getProductId(), purchaseProduct.getProductDetailId()));
		
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
	
	// 구매 상품 후기 작성
	public int updatePurchaseProductReview(String userLoginId, int id, int star, String review, MultipartFile file) {
		String imagePath = null;
		
		if (file != null) {
			imagePath = fileManagerService.saveFile(userLoginId, file);
		}
		
		return purchaseDAO.updatePurchaseProductReview(id, star, review, imagePath);
	}
	
	// 구매 상품 후기 수정
	public int updatePurchaseProductReviewAgain(String userLoginId, int id, int star, String review, MultipartFile file) {
		String imagePath = null;
		
		if (file != null) {
			imagePath = fileManagerService.saveFile(userLoginId, file);
			
			if (imagePath != null && purchaseBO.getPurchaseProductById(id).getImagePath() != null) {
				fileManagerService.deleteFile(purchaseBO.getPurchaseProductById(id).getImagePath());
			}
		} else {
			imagePath = purchaseBO.getPurchaseProductById(id).getImagePath();
		}
		
		return purchaseDAO.updatePurchaseProductReview(id, star, review, imagePath);
	}
	
	// 구매 상품 후기 삭제
	public int updatePurchaseProductReviewNull(int id) {
		if (purchaseBO.getPurchaseProductById(id).getImagePath() != null) {
			fileManagerService.deleteFile(purchaseBO.getPurchaseProductById(id).getImagePath());
		}
		
		return purchaseDAO.updatePurchaseProductReviewNull(id);
	}

}
