package com.gxcy.dao;


import java.util.List;
import java.util.Map;

import com.gxcy.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDao {

	User get(Long userId);
	
	List<User> list(Map<String, Object> map);
	
	int insert(User user);
	
	int update(User user);

}
