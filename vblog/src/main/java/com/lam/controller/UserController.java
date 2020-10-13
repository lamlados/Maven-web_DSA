package com.lam.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lam.common.lang.Result;
import com.lam.entity.User;
import com.lam.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lam
 * @since 2020-10-05
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @RequiresAuthentication
    @GetMapping("/index")
    public Object index() {
        User user = userService.getById(1L);
        return Result.succ(user);
    }
}
