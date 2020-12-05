package com.gxcy.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gxcy.entity.User;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {
	User get(Long id);

	List<User> list(Map<String, Object> map);

	int insert(User user);

	int update(User user);



}
