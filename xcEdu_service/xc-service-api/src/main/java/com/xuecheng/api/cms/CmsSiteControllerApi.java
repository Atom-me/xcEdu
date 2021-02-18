package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsSiteResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author atom
 */
@Api(tags = "CMS站点管理接口")
public interface CmsSiteControllerApi {


    @ApiOperation(value = "查询所有站点信息")
    QueryResponseResult findAll();

    @ApiOperation(value = "分页查询站点列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path")
    })
    QueryResponseResult findList(int page, int size);

    @ApiOperation(value = "新增站点")
    CmsSiteResult add(CmsSite cmsSite);

    @ApiOperation(value = "修改站点")
    CmsSiteResult edit(CmsSite cmsSite);

    @ApiOperation(value = "按站点ID获取站点")
    CmsSiteResult getCmsSite(String siteId);

    @ApiOperation(value = "按站点ID删除站点")
    CmsSiteResult deleteById(String siteId);

}
