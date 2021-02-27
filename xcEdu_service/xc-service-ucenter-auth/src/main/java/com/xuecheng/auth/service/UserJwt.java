package com.xuecheng.auth.service;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author atom
 */
@Data
@ToString(callSuper = true)
public class UserJwt extends User {

    private String id;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 用户头像
     */
    private String userpic;
    /**
     * 用户类型
     */
    private String utype;
    /**
     * 所属企业
     */
    private String companyId;

    public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

}
