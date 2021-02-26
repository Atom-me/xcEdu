package com.xuecheng.api.ucenter;

import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author atom
 */
@Api(tags = "用户中心")
public interface UcenterControllerApi {

    @ApiOperation(value = "按用户名查询用户信息")
    XcUserExt findByUsername(String username);

}
