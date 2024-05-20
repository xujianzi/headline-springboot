package com.jianxu.controller;

import com.jianxu.pojo.User;
import com.jianxu.service.UserService;
import com.jianxu.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: springboot-headline-part
 * @author: Jian Xu
 * @version: 1.0
 * @time 3/17/2024
 * @description: TODO
 */

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        Result result = userService.login(user);
        log.info("result: {}", result);
//        System.out.println("result: "+result);
        return result;
    }

    @GetMapping("/getUserInfo")
    public Result userInfo(@RequestHeader(value = "token") String token){
        Result result = userService.getUserInfo(token);
        log.info("result: {}", result);

        return result;
    }

    @PostMapping("checkUserName")
    public Result checkUserName(@RequestParam(name = "username") String username){
        Result result = userService.checkUserName(username);
        log.info("result: {}", result);

        return result;
    }

    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        log.info("result: {}", result);

        return result;
    }

    @RequestMapping("/checkLogin")
    public Result checkLogin(@RequestHeader(name = "token") String token){
        Result result = userService.checkLogin(token);
        log.info("result: {}", result);
        return result;
    }
}
