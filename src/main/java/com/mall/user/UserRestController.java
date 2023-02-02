package com.mall.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mall.common.EncryptUtils;
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
		
		boolean isDuplicated;
		
		try {
			// 아이디 존재 유무
			isDuplicated = userBO.existLoginId(loginId);
		} catch(Exception e) {
			result.put("code", 500);
			result.put("errorMessage", "아이디 중복확인 실패");
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
			@RequestParam("postcode") int postcode,
			@RequestParam("address") String address,
			@RequestParam("detailAddress") String detailAddress,
			HttpSession session
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 비밀번호 암호화
		String hashedPassword = EncryptUtils.md5(password);
		
		// 유저 추가
		int rowCount = userBO.addUser(loginId, hashedPassword, name, phoneNumber,
				email, postcode, address, detailAddress);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
			
			// 유저 검색
			User user = userBO.getUserByLoginIdPassword(loginId, hashedPassword);
			
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
		} else {
			result.put("code", 500);
			result.put("errorMessage", "회원가입 실패");
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
		
		// 비밀번호 암호화
		String hashedPassword = EncryptUtils.md5(password);
		
		// 유저 검색
		User user = userBO.getUserByLoginIdPassword(loginId, hashedPassword);
		
		if (user != null) {
			result.put("code", 1);
			result.put("result", "success");
			
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
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
		
		// 아이디 검색
		User user = userBO.getUserByNamePhoneNumber(name, phoneNumber);
		
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

}
