package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author atom
 */
@Api(tags = "课程计划管理接口")
public interface CoursePlanControllerApi {

    @ApiOperation(value = "查询指定课程的课程计划")
    TeachplanNode findList(String courseId);

    @ApiOperation(value = "查询指定ID的课程计划")
    Teachplan findById(String teachplanId);

    @ApiOperation(value = "新增课程计划")
    ResponseResult add(Teachplan teachplan);

    @ApiOperation(value = "编辑课程计划")
    ResponseResult edit(Teachplan teachplan);

    @ApiOperation(value = "删除课程计划")
    ResponseResult delete(String teachplanId);

    @ApiOperation(value = "保存媒资信息")
    ResponseResult saveMedia(TeachplanMedia teachplanMedia);


}
