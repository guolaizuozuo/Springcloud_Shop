package com.abc.service.impl;


import com.abc.entities.UserInfo;
import com.abc.mapper.UserMapper;
import com.abc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper repository;

    @Override
    public UserInfo findByOpenId(String openId) {
        return repository.findByOpenid(openId);
    }
}
