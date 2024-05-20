package com.jianxu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jianxu.pojo.User;
import com.jianxu.service.UserService;
import com.jianxu.mapper.UserMapper;
import com.jianxu.utils.JwtHelper;
import com.jianxu.utils.MD5Util;
import com.jianxu.utils.Result;
import com.jianxu.utils.ResultCodeEnum;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author Now Now
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2024-03-06 13:04:06
*/

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtHelper jwtHelper;

    @Override
    public Result login(User user) {
        //1.查询用户是否存在 否 返回 code 501
        //2 用户名密码有误 返回503
        //3 登录成功  code 200, data

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);
        if (loginUser == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        //判断密码
        if (!StringUtils.isEmpty(user.getUserPwd()) &&
                loginUser.getUserPwd().equals(MD5Util.encrypt(user.getUserPwd()))){
            // 密码正确，根据用户唯一标识生成token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            HashMap<String, Object> data = new HashMap<>();
            data.put("token", token);
            return Result.ok(data);
        }

        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     * 查询用户的具体信息
     * 1.token是否过期
     * 2.若不过期，返回数据封装成loginUser
     * @param token
     * @return
     */
    @Override
    public Result getUserInfo(String token) {
        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        Long userId = jwtHelper.getUserId(token);
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setUserPwd("");
            Map<String, Object> data = new HashMap<>();
            data.put("loginUser", user);
            return Result.ok(data);
        }
        return Result.build(null,ResultCodeEnum.NOTLOGIN);
    }

    /**
     * 查询用户是否可用
     * @param username
     * @return
     */
    @Override
    public Result checkUserName(String username) {
        User user = checkUserNameBase(username);
        if (user == null) {
            return Result.ok(null);
        }

        return Result.build(null, ResultCodeEnum.USERNAME_USED);
    }

    /**
     * 用户注册，注册前需要验证用户是否存在
     * @param user
     * @return
     */
    @Override
    public Result regist(User user) {
        User userDB = checkUserNameBase(user.getUsername());
        if (userDB != null) {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        // 将密码加密
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        //注册用户
        int rows = userMapper.insert(user);
        log.info("rows: {}", rows);

        return Result.ok(null);
    }

    /**
     * 价差用户是否存在，返回类型是User(NULL)
     * @param username
     * @return
     */
    @Override
    public User checkUserNameBase(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);

        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 使用token验证登录是否过期
     * @param token
     * @return
     */
    @Override
    public Result checkLogin(String token) {
        if (jwtHelper.isExpiration(token) || StringUtils.isEmpty(token)){
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }

        return Result.ok(null);
    }
}




