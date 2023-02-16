package com.mall.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mall.common.EncryptUtils;
import com.mall.common.MakePassword;
import com.mall.user.bo.UserBO;
import com.mall.user.model.User;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	/**
	 * 아이디 중복 확인 API
	 * @param loginId
	 * @return
	 */
	@GetMapping("/is_duplicated_id")
	public Map<String, Object> isDuplicatedId(@RequestParam("loginId") String loginId) {
		Map<String, Object> result = new HashMap<>();
		
		boolean isDuplicated = false;
		
		try {
			// 아이디 존재 유무
			isDuplicated = userBO.existLoginId(loginId);
		} catch(Exception e) {
			result.put("code", 500);
			result.put("errorMessage", "아이디 중복확인에 실패했습니다");
			return result;
		}
		
		if (isDuplicated) {
			result.put("code", 1);
			result.put("result", true);
		} else {
			result.put("code", 1);
			result.put("result", false);
		}
		
		return result;
	}
	
	/**
	 * 회원가입 API
	 * @param loginId
	 * @param password
	 * @param name
	 * @param phoneNumber
	 * @param email
	 * @param postcode
	 * @param address
	 * @param detailAddress
	 * @param session
	 * @return
	 */
	@PostMapping("/sign_up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("email") String email,
			@RequestParam("postcode") String postcode,
			@RequestParam("address") String address,
			@RequestParam("detailAddress") String detailAddress,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 유저 추가
		int rowCount = userBO.addUser(loginId, EncryptUtils.md5(password), name, phoneNumber, email, postcode, address, detailAddress);
		
		if (rowCount > 0) {
			// 유저 조회 (아이디, 비밀번호)
			User user = userBO.getUserByLoginIdOrPassword(loginId, EncryptUtils.md5(password));
			
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
			
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "회원가입에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 로그인 API
	 * @param loginId
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("/sign_in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 유저 조회 (아이디, 비밀번호)
		User user = userBO.getUserByLoginIdOrPassword(loginId, EncryptUtils.md5(password));
		
		if (user != null) {
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
			
			if (user.getType() == 0) {
				result.put("type", "user");
			} else {
				result.put("type", "admin");
			}
			
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다");
		}
		
		return result;
	}
	
	/**
	 * 아이디 찾기 API
	 * @param name
	 * @param phoneNumber
	 * @return
	 */
	@PostMapping("/search_id")
	public Map<String, Object> searchId(
			@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 유저 조회 (아이디, 이름, 휴대폰 번호)
		User user = userBO.getUserByNamePhoneNumberOrLoginId(null, name, phoneNumber);
		
		if (user != null) {
			result.put("code", 1);
			result.put("result", "success");
			result.put("userName", user.getName());
			result.put("userLoginId", user.getLoginId());
		} else {
			result.put("code", 500);
			result.put("errorMessage", "해당 정보를 가진 유저가 없습니다");
		}
		
		return result;
	}
	
	/**
	 * 임시 비밀번호 발급 API
	 * @param loginId
	 * @param name
	 * @param phoneNumber
	 * @return
	 */
	@PostMapping("/search_password")
	public Map<String, Object> searchPassword(
			@RequestParam("loginId") String loginId,
			@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 유저 조회 (아이디, 이름, 휴대폰 번호)
		User user = userBO.getUserByNamePhoneNumberOrLoginId(loginId, name, phoneNumber);
		
		if (user != null) {
			// 임시 비밀번호 생성
			String tempPassword = MakePassword.randomPassword(10);
			
			// 비밀번호 수정
			int rowCount = userBO.updateUserPasswordById(user.getId(), EncryptUtils.md5(tempPassword));
			
			if (rowCount > 0) {
				result.put("code", 1);
				result.put("result", "success");
				result.put("userName", user.getName());
				result.put("userPassword", tempPassword);
			} else {
				result.put("code", 500);
				result.put("errorMessage", "임시 비밀번호 생성에 실패했습니다");
			}
		} else {
			result.put("code", 500);
			result.put("errorMessage", "해당 정보를 가진 유저가 없습니다");
		}
		
		return result;
	}
	
	/**
	 * 비밀번호 수정 API
	 * @param password
	 * @param session
	 * @return
	 */
	@PutMapping("/user_password_update")
	public Map<String, Object> userPasswordUpdate(
			@RequestParam("password") String password,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 비밀번호 수정
		int rowCount = userBO.updateUserPasswordById((int)session.getAttribute("userId"), EncryptUtils.md5(password));
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "비밀번호 변경에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 회원 정보 수정 API
	 * @param phoneNumber
	 * @param email
	 * @param postcode
	 * @param address
	 * @param detailAddress
	 * @param session
	 * @return
	 */
	@PutMapping("/user_info_update")
	public Map<String, Object> userInfoUpdate(
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("email") String email,
			@RequestParam("postcode") String postcode,
			@RequestParam("address") String address,
			@RequestParam("detailAddress") String detailAddress,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 회원 정보 수정
		int rowCount = userBO.updateUserInfoById((int)session.getAttribute("userId"), phoneNumber, email, postcode, address, detailAddress);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "회원 정보 수정에 실패했습니다");
		}
		
		return result;
	}
	
	/**
	 * 회원 탈퇴 API
	 * @param loginId
	 * @param password
	 * @param session
	 * @return
	 */
	@DeleteMapping("/user_sign_out")
	public Map<String, Object> userSignOut(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 회원 탈퇴
		int rowCount = userBO.deleteUserByIdLoginIdPassword((int)session.getAttribute("userId"), loginId, EncryptUtils.md5(password));
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
			
			session.removeAttribute("userId");
			session.removeAttribute("userLoginId");
			session.removeAttribute("userName");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다");
		}
		
		return result;
	}

}
