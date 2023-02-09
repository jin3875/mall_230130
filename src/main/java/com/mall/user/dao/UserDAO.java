package com.mall.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mall.user.model.User;

@Repository
public interface UserDAO {
	
	// TEST
	public List<Map<String, Object>> selectUserListTEST();
	
	// 아이디 존재 유무
	public boolean existLoginId(String LoginId);
	
	// 유저 조회
	public User selectUserById(int id);
	
	// 유저 조회 (아이디, 비밀번호)
	public User selectUserByLoginIdOrPassword(
			@Param("loginId") String loginId,
			@Param("password") String password);
	
	// 유저 조회 (아이디, 이름, 휴대폰 번호)
	public User selectUserByNamePhoneNumberOrLoginId(
			@Param("loginId") String loginId,
			@Param("name") String name,
			@Param("phoneNumber") String phoneNumber);
	
	// 유저 추가
	public int insertUser(
			@Param("loginId") String loginId,
			@Param("password") String password,
			@Param("name") String name,
			@Param("phoneNumber") String phoneNumber,
			@Param("email") String email,
			@Param("postcode") String postcode,
			@Param("address") String address,
			@Param("detailAddress") String detailAddress);
	
	// 비밀번호 수정
	public int updateUserPasswordById(
			@Param("id") int id,
			@Param("password") String password);
	
	// 회원 정보 수정
	public int updateUserInfoById(
			@Param("id") int id,
			@Param("phoneNumber") String phoneNumber,
			@Param("email") String email,
			@Param("postcode") String postcode,
			@Param("address") String address,
			@Param("detailAddress") String detailAddress);
	
	// 회원 탈퇴
	public int deleteUserByIdLoginIdPassword(
			@Param("id") int id,
			@Param("loginId") String loginId,
			@Param("password") String password);

}
