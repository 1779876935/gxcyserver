package com.gxcy.service.impl;

import com.gxcy.dao.UserDao;
import com.gxcy.entity.User;
import com.gxcy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userMapper;

    @Override
    public User get(Long id) {
        User user = userMapper.get(id);
        return user;
    }

    @Override
    public List<User> list(Map<String, Object> map) {
        return null;
    }

    @Override
    public int insert(User user) {
        return 0;
    }

    @Override
    public int update(User user) {
        return 0;
    }
}
