package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author atom
 */
@RestController
@RequestMapping("cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private CmsPageService cmsPageService;

    @Override
    @GetMapping("list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page,
                                        @PathVariable("size") int size,
                                        QueryPageRequest queryPageRequest) {
        return cmsPageService.findList(page, size, queryPageRequest);
    }

    @Override
    @PostMapping
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        if (Objects.isNull(cmsPage)) {
            ExceptionCast.cast(CommonCode.PARAMS_ERROR);
        }
        // 校验是否已存在,使用三个字段标识一个页面的唯一性（站点ID，页面名称，页面的webPath）cms_page集合创建唯一索引 siteId_1_pageName_1_pageWebPath_1 （siteId,pageName,pageWebPath）
        CmsPage _cmsPage = cmsPageService.findBySiteIdAndPageNameAndPageWebPath(cmsPage.getSiteId(), cmsPage.getPageName(), cmsPage.getPageWebPath());
        if (Objects.nonNull(_cmsPage)) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        CmsPage addResult = cmsPageService.add(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, addResult);
    }

    @Override
    @GetMapping("/{pageId}")
    public CmsPageResult getCmsPage(@PathVariable("pageId") String pageId) {
        CmsPage cmsPage = cmsPageService.findByPageId(pageId);
        if (Objects.isNull(cmsPage)) {
            ExceptionCast.cast(CmsCode.CMS_EDITPAGE_NOTEXISTS);
        }
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    @Override
    @DeleteMapping("/{pageId}")
    public CmsPageResult deleteById(@PathVariable("pageId") String pageId) {
        if (StringUtils.isBlank(pageId)) {
            ExceptionCast.cast(CommonCode.PARAMS_ERROR);
        }
        cmsPageService.deleteById(pageId);
        return new CmsPageResult(CommonCode.SUCCESS, null);
    }

    /**
     * 页面发布
     *
     * @param pageId 页面ID
     */
    @Override
    @PostMapping("post/{pageId}")
    public ResponseResult postPage(@PathVariable String pageId) {
        return cmsPageService.postPage(pageId);
    }

    /**
     * 保存页面
     *
     * @param cmsPage
     * @return
     */
    @Override
    @PostMapping("save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage) {
        CmsPage save = cmsPageService.save(cmsPage);
        if (Objects.isNull(save)) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        return new CmsPageResult(CommonCode.SUCCESS, save);
    }

    /**
     * cms page页面一键发布
     *
     * @param cmsPage 页面信息
     * @return CmsPostPageResult
     */
    @Override
    @PostMapping("postPageQuick")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage) {
        String url = cmsPageService.postPageQuick(cmsPage);
        if (StringUtils.isBlank(url)) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        return new CmsPostPageResult(CommonCode.SUCCESS, url);
    }

    @Override
    @PutMapping
    public CmsPageResult edit(@RequestBody CmsPage cmsPage) {
        if (Objects.isNull(cmsPage)) {
            ExceptionCast.cast(CommonCode.PARAMS_ERROR);
        }
        CmsPage edit = cmsPageService.edit(cmsPage);
        if (Objects.isNull(edit)) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        return new CmsPageResult(CommonCode.SUCCESS, edit);
    }


}
