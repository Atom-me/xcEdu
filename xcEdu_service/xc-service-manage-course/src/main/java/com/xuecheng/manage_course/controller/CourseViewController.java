package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseViewControllerApi;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_course.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author atom
 */
@RestController
@RequestMapping("course")
public class CourseViewController extends BaseController implements CourseViewControllerApi {

    @Resource
    private CourseService courseService;

    /**
     * 查询课程预览所需模型数据
     *
     * @param courseId 课程ID
     * @return CourseView
     */
    @Override
    @GetMapping("courseview/{id}")
    public CourseView courseview(@PathVariable("id") String courseId) {
        return courseService.getCourseView(courseId);
    }

    /**
     * 预览课程，生成页面预览URL，返回给前端，用户点击预览URL进行页面预览
     *
     * @param courseId 课程ID
     * @return CoursePublishResult
     */
    @Override
    @PostMapping("courseview/preview/{id}")
    public CoursePublishResult coursePreview(@PathVariable("id") String courseId) {
        String previewUrl = courseService.preview(courseId);
        if (StringUtils.isBlank(previewUrl)) {
            return new CoursePublishResult(CommonCode.FAIL, null);
        }
        return new CoursePublishResult(CommonCode.SUCCESS, previewUrl);
    }

    /**
     * 课程发布
     *
     * @param courseId 课程ID
     * @return CoursePublishResult
     */
    @Override
    @PostMapping("publish/{id}")
    public CoursePublishResult coursePublish(@PathVariable("id") String courseId) {
        String publishUrl = courseService.publish(courseId);
        isNullOrEmpty(publishUrl, CommonCode.FAIL);
        return new CoursePublishResult(CommonCode.SUCCESS, publishUrl);
    }
}
