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

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	/**
	 * 아이디 중복확인 API
	 * @param loginId
	 * @return
	 */
	@GetMapping("/is_duplicated_id")
	public Map<String, Object> isDuplicatedId(@RequestParam("loginId") String loginId) {
		Map<String, Object> result = new HashMap<>();
		
		boolean isDuplicated;
		
		try {
			// loginId 존재 유무
			isDuplicated = userBO.existLoginId(loginId);
		} catch(Exception e) {
			result.put("code", 500);
			result.put("errorMessage", "아이디 중복확인 실패");
			return result;
		}
		
		if (isDuplicated) {
			// 중복
			result.put("code", 1);
			result.put("result", true);
		} else {
			// 사용 가능
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
			@RequestParam("detailAddress") String detailAddress
	) {
		Map<String, Object> result = new HashMap<>();
		
		// 비밀번호 암호화
		String hashedPassword = EncryptUtils.md5(password);
		
		// 유저 추가
		int rowCount = userBO.addUser(loginId, hashedPassword, name, phoneNumber, email, postcode, address, detailAddress);
		
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "success");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "회원가입 실패");
		}
		
		return result;
	}

}
