package com.abc.service;


import com.abc.entities.UserInfo;

public interface UserService {

    UserInfo findByOpenId(String openId);
}
