package com.xuecheng.ucenter.dao;

import com.xuecheng.framework.domain.ucenter.XcMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author atom
 */
@Mapper
public interface XcMenuMapper {

    /**
     * 通过用户ID查询用户所拥有的权限
     *
     * @param userid
     * @return
     */
    List<XcMenu> selectPermissionByUserId(String userid);

}
