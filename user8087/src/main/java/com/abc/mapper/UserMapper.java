package com.abc.mapper;


import com.abc.entities.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user_info where openid =#{openid}")
    UserInfo findByOpenid(String openId);
}
