package com.xuecheng.ucenter.controller;

import com.xuecheng.api.ucenter.UcenterControllerApi;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.ucenter.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author atom
 */
@RestController
@RequestMapping("ucenter")
public class UserController implements UcenterControllerApi {


    @Resource
    private UserService userService;

    /**
     * 根据用户账号获取用户信息，用户公司信息，用户权限信息
     *
     * @param username
     * @return
     */
    @Override
    @GetMapping("getuserext")
    public XcUserExt findByUsername(@RequestParam("username") String username) {
        return userService.findByUsername(username);
    }
}
