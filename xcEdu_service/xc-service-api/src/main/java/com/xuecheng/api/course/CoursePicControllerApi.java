package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author atom
 */
@Api(tags = "课程图片管理接口")
public interface CoursePicControllerApi {

    /**
     * 新增课程图片
     *
     * @param courseId 课程ID
     * @param pic      图片ID
     * @return
     */
    @ApiOperation(value = "新增课程图片")
    CoursePic saveCoursePic(String courseId, String pic);

    @ApiOperation(value = "查询课程图片")
    CoursePic findById(String courseId);

    @ApiOperation(value = "删除课程图片")
    ResponseResult deleteById(String courseId);

}
