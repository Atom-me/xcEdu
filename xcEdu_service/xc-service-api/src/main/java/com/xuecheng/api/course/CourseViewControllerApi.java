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
     * @param id
     * @return
     */
    @ApiOperation(value = "查询课程预览所需模型数据")
    CourseView courseview(String id);

    @ApiOperation(value = "课程视图预览")
    CoursePublishResult coursePreview(String id);

    @ApiOperation(value = "课程发布")
    CoursePublishResult coursePublish(String id);

}
