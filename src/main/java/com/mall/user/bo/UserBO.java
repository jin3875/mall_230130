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
	public boolean existLoginId(String loginId) {
		return userDAO.existLoginId(loginId);
	}
	
	// 유저 조회
	public User getUserById(int id) {
		return userDAO.selectUserById(id);
	}
	
	// 유저 조회 (아이디, 비밀번호)
	public User getUserByLoginIdOrPassword(String loginId, String password) {
		return userDAO.selectUserByLoginIdOrPassword(loginId, password);
	}
	
	// 유저 조회 (아이디, 이름, 휴대폰 번호)
	public User getUserByNamePhoneNumberOrLoginId(String loginId, String name, String phoneNumber) {
		return userDAO.selectUserByNamePhoneNumberOrLoginId(loginId, name, phoneNumber);
	}
	
	// 유저 추가
	public int addUser(String loginId, String password, String name, String phoneNumber, String email, String postcode, String address, String detailAddress) {
		return userDAO.insertUser(loginId, password, name, phoneNumber, email, postcode, address, detailAddress);
	}
	
	// 비밀번호 수정
	public int updateUserPasswordById(int id, String password) {
		return userDAO.updateUserPasswordById(id, password);
	}
	
	// 회원 정보 수정
	public int updateUserInfoById(int id, String phoneNumber, String email, String postcode, String address, String detailAddress) {
		return userDAO.updateUserInfoById(id, phoneNumber, email, postcode, address, detailAddress);
	}
	
	// 회원 탈퇴
	public int deleteUserByIdLoginIdPassword(int id, String loginId, String password) {
		return userDAO.deleteUserByIdLoginIdPassword(id, loginId, password);
	}

}
