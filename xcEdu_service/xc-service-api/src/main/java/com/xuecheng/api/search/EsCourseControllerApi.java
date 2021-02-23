package com.xuecheng.api.search;

import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.domain.search.EsCoursePub;
import com.xuecheng.framework.domain.search.EsTeachplanMediaPub;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

/**
 * @author atom
 */
@Api(tags = "课程搜索接口")
public interface EsCourseControllerApi {

    @ApiOperation(value = "课程综合搜索")
    QueryResponseResult list(int page, int size, CourseSearchParam courseSearchParam);

    /**
     * 学习页面查询课程计划接口
     *
     * @param courseId 课程ID
     * @return
     */
    @ApiOperation(value = "根据课程id查询课程信息")
    Map<String, EsCoursePub> getAll(String courseId);

    /**
     * 学习页面查询课程计划媒体资源接口
     *
     * @param teachplanId 课程计划ID
     * @return
     */
    @ApiOperation(value = "根据课程计划ID查询课程计划媒资信息")
    EsTeachplanMediaPub getMedia(String teachplanId);
}
