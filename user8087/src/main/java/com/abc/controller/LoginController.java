package com.abc.controller;

import com.abc.constant.CookieConstant;
import com.abc.constant.RedisConstant;
import com.abc.entities.UserInfo;
import com.abc.enums.ResultEnum;
import com.abc.enums.RoleEnum;
import com.abc.service.UserService;
import com.abc.utils.CookieUtil;
import com.abc.utils.ResultVOUtil;
import com.abc.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 买家登录
     * @param openId
     * @param response
     * @return
     */
    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openId")String openId,
                          HttpServletResponse response){
        //1.openid和数据库里的数据是否匹配
        UserInfo userInfo = userService.findByOpenId(openId);
        if(userInfo == null ){
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2.判断角色
        if(RoleEnum.BUYER.getCode() .equals(userInfo.getRole())){
            return ResultVOUtil.error(ResultEnum.ROLR_ERROR);
        }
        //3.cookie里设置openid=abc
        CookieUtil.set(response, CookieConstant.OPENID,openId,CookieConstant.expire);
        return ResultVOUtil.success();

    }




    /**
     * 卖家登录
     * @param openId
     * @param response
     * @return
     */
    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openId")String openId,
                           HttpServletRequest request,
                           HttpServletResponse response){
        //判断是否已登录
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);

        if(cookie != null &&
                !StringUtils.isEmpty(stringRedisTemplate.opsForValue().
                        get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))){
            return ResultVOUtil.success();
        }
        //1.openid和数据库里的数据是否匹配
        UserInfo userInfo = userService.findByOpenId(openId);
        if(userInfo == null ){
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2.判断角色
        if(RoleEnum.SELLER.getCode() .equals(userInfo.getRole())){
            return ResultVOUtil.error(ResultEnum.ROLR_ERROR);
        }

        //3.redis设置key=UUID,value=xyz
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.expire;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE,openId),
                token,
                expire,
                TimeUnit.SECONDS);
        //4.cookie里设置openid=abc
        CookieUtil.set(response, CookieConstant.TOKEN,openId,CookieConstant.expire);
        return ResultVOUtil.success();
    }
}
