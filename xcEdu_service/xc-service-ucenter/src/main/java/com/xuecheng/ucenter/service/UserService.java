package com.xuecheng.ucenter.service;

import com.xuecheng.framework.domain.ucenter.XcCompanyUser;
import com.xuecheng.framework.domain.ucenter.XcMenu;
import com.xuecheng.framework.domain.ucenter.XcUser;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.ucenter.dao.XcCompanyUserRepository;
import com.xuecheng.ucenter.dao.XcMenuMapper;
import com.xuecheng.ucenter.dao.XcUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author atom
 */
@Service
public class UserService {

    @Resource
    private XcUserRepository xcUserRepository;

    @Resource
    private XcCompanyUserRepository xcCompanyUserRepository;

    @Resource
    private XcMenuMapper xcMenuMapper;


    /**
     * 按用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    public XcUserExt findByUsername(String username) {
        XcUserExt result = new XcUserExt();

        // 查询用户信息
        XcUser userInfo = xcUserRepository.findByUsername(username);
        if (Objects.isNull(userInfo)) {
            return null;
        }

        BeanUtils.copyProperties(userInfo, result);

        // 查询用户公司信息
        XcCompanyUser companyUser = xcCompanyUserRepository.findByUserId(userInfo.getId());
        if (Objects.nonNull(companyUser)) {
            result.setCompanyId(companyUser.getCompanyId());
        }

        // 查询用户权限
        List<XcMenu> xcMenus = xcMenuMapper.selectPermissionByUserId(userInfo.getId());
        result.setPermissions(xcMenus);

        return result;
    }
}
