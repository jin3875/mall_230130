package com.mall.user.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {
	
	// TEST
	public List<Map<String, Object>> selectUserListTEST();

}
