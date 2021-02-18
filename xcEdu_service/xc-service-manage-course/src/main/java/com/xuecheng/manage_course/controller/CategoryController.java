package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CategoryControllerApi;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_course.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author atom
 */
@RestController
@RequestMapping("category")
public class CategoryController extends BaseController implements CategoryControllerApi {

    @Resource
    private CategoryService categoryService;

    /**
     * 查询分类列表
     *
     * @return CategoryNode
     */
    @Override
    @GetMapping("list")
    public CategoryNode findCategoryList() {
        return categoryService.findCategoryList();
    }
}
