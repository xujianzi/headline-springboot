package com.jianxu.service;

import com.jianxu.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jianxu.utils.Result;

/**
* @author Now Now
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-03-06 13:04:06
*/
public interface UserService extends IService<User> {

    Result login(User user);

    Result getUserInfo(String token);

    Result checkUserName(String username);

    Result regist(User user);

    User checkUserNameBase(String username);

    Result checkLogin(String token);
}
