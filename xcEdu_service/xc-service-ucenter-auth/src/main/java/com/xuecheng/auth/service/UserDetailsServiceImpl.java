package com.xuecheng.auth.service;

import com.xuecheng.auth.client.UserClient;
import com.xuecheng.framework.domain.ucenter.XcMenu;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author atom
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private ClientDetailsService clientDetailsService;

    @Resource
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证,统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if (Objects.isNull(authentication)) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (Objects.nonNull(clientDetails)) {
                //密码
                String clientSecret = clientDetails.getClientSecret();
                return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        // 查询用户信息
        XcUserExt userExt = userClient.findByUsername(username);
        if (userExt.getPermissions() == null) {
            userExt.setPermissions(new ArrayList<>());
        }
        if (userExt == null) {
            return null;
        }
        //从数据库查询用户正确的密码，Spring Security会去比对输入密码的正确性
        String password = userExt.getPassword();

        List<String> stringList = userExt.getPermissions()
                .stream()
                .map(XcMenu::getCode)
                .collect(Collectors.toList());

        String[] permissionList = new String[stringList.size()];

        UserJwt userDetails = new UserJwt(username,
                password,
                AuthorityUtils.createAuthorityList(stringList.toArray(permissionList)));

        userDetails.setId(userExt.getId());
        userDetails.setUtype(userExt.getUtype());//用户类型
        userDetails.setCompanyId(userExt.getCompanyId());//所属企业
        userDetails.setName(userExt.getName());//用户名称
        userDetails.setUserpic(userExt.getUserpic());//用户头像

       /* UserDetails userDetails = new org.springframework.security.core.userdetails.User(username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(""));*/
//                AuthorityUtils.createAuthorityList("course_get_baseinfo","course_get_list"));
        return userDetails;
    }
}
