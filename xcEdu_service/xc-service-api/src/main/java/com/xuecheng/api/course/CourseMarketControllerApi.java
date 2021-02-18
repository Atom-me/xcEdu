package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author atom
 */
@Api(tags = "课程营销接口")
public interface CourseMarketControllerApi {

    @ApiOperation(value = "获取课程营销信息")
    CourseMarket getCourseMarketById(String courseId);

    /**
     * 接口实现可采用先查询课程营销信息，如果存在则更新，否则添加课程营销信息
     *
     * @param id
     * @param courseMarket
     * @return
     */
    @ApiOperation(value = "更新课程营销信息")
    ResponseResult updateCourseMarket(String id, CourseMarket courseMarket);

}
