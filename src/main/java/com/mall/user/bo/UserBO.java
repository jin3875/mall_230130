package com.mall.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.user.dao.UserDAO;
import com.mall.user.model.User;

@Service
public class UserBO {
	
	@Autowired
	private UserDAO userDAO;
	
	// 아이디 존재 유무
	public boolean existLoginId(String LoginId) {
		return userDAO.existLoginId(LoginId);
	}
	
	// 유저 추가
	public int addUser(String loginId, String password, String name, String phoneNumber,
			String email, int postcode, String address, String detailAddress) {
		return userDAO.insertUser(loginId, password, name, phoneNumber,
				email, postcode, address, detailAddress);
	}
	
	// 유저 검색 (로그인)
	public User getUserByLoginIdPassword(String loginId, String password) {
		return userDAO.selectUserByLoginIdPassword(loginId, password);
	}
	
	// 유저 검색 (아이디/비밀번호)
	public User getUserByNamePhoneNumberOrLoginId(String loginId, String name, String phoneNumber) {
		return userDAO.selectUserByNamePhoneNumberOrLoginId(loginId, name, phoneNumber);
	}
	
	// 비밀번호 변경
	public int updateUserPasswordById(int id, String password) {
		return userDAO.updateUserPasswordById(id, password);
	}

}
