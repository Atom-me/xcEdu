package com.xuecheng.manage_cms.controller;

import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.CmsPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 页面在发布前增加预览的步骤，方便用户检查页面内容是否正确。
 * 流程：
 * 1。用户进入CMS前端，点击页面预览，在浏览器请求CMS页面预览链接
 * 2。CMS根据页面ID查询页面dataUrl,并远程请求dataUrl获取页面数据模型
 * 3。CMS根据页面ID查询页面模版
 * 4。CMS执行页面静态化
 * 5。CMS将静态化的内容响应给浏览器
 *
 * @author atom
 */
@Api(tags = "CMS页面预览接口")
@Slf4j
@Controller
@RequestMapping("cms/preview")
public class CmsPagePreviewController extends BaseController {

    @Resource
    private CmsPageService cmsPageService;

    /**
     * CMS页面预览
     *
     * @param pageId 预览的页面ID
     */
    @ApiOperation(value = "CMS页面预览")
    @RequestMapping("/{pageId}")
    public void preview(@PathVariable String pageId) {
        // 获取页面内容，根据页面模版，生成HTML文件内容
        String htmlContent = cmsPageService.genHtml(pageId);
        isNullOrEmpty(htmlContent, CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        // 将静态HTML页面信息输出到浏览器返回
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-type", "text/html;charset=utf-8");
            outputStream.write(htmlContent.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("[CMS页面预览] 预览页面失败，异常信息：", e);
        }
    }

}
