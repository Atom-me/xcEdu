package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author atom
 */
@Api(tags = "CMS页面数据模型管理接口")
public interface CmsConfigControllerApi {

    @ApiOperation(value = "根据id查询CMS配置信息(页面数据模型数据)")
    CmsConfig getModel(String id);

}
