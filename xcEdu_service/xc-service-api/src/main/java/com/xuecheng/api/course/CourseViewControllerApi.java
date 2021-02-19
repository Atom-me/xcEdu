package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author atom
 */
@Api(tags = "课程预览")
public interface CourseViewControllerApi {

    /**
     * 查询课程预览所需模型数据
     *
     * @param courseId
     * @return
     */
    @ApiOperation(value = "查询课程预览所需模型数据")
    CourseView courseview(String courseId);

    /**
     * 预览课程，生成页面预览URL，返回给前端，用户点击预览URL进行页面预览
     *
     * @param courseId
     * @return
     */
    @ApiOperation(value = "课程视图预览")
    CoursePublishResult coursePreview(String courseId);

    @ApiOperation(value = "课程发布")
    CoursePublishResult coursePublish(String courseId);

}
