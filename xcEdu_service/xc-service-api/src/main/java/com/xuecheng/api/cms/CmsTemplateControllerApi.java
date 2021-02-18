package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author atom
 */
@Api(tags = "CMS模板管理接口")
public interface CmsTemplateControllerApi {

    @ApiOperation(value = "查询所有模板信息")
    QueryResponseResult findAll();

    @ApiOperation(value = "分页查询模板列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path")
    })
    QueryResponseResult findList(int page, int size);

    @ApiOperation(value = "新增模板")
    CmsTemplateResult add(CmsTemplate cmsTemplate);

    @ApiOperation(value = "修改模板")
    CmsTemplateResult edit(CmsTemplate cmsTemplate);

    @ApiOperation(value = "按ID获取模板")
    CmsTemplateResult getCmsTemplate(String templateId);

    @ApiOperation(value = "按ID删除模板")
    CmsTemplateResult deleteById(String templateId);

    /**
     * 上传模版文件
     *
     * @param file 模版文件
     * @return 模版文件上传成功后返回 GridFs中文件ID 即：ObjectId
     */
    @ApiOperation(value = "上传模板文件")
    String uploadTemplate(MultipartFile file);

    @ApiOperation(value = "移除模板文件")
    void removeTemplateFile(String templateFileId);

}
