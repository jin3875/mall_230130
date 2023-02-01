package com.mall.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {
	
	// TEST
	public List<Map<String, Object>> selectUserListTEST();
	
	// loginId 존재 유무
	public boolean existLoginId(String LoginId);
	
	// 유저 추가
	public int insertUser(
			@Param("loginId") String loginId,
			@Param("password") String password,
			@Param("name") String name,
			@Param("phoneNumber") String phoneNumber,
			@Param("email") String email,
			@Param("postcode") int postcode,
			@Param("address") String address,
			@Param("detailAddress") String detailAddress);

}
