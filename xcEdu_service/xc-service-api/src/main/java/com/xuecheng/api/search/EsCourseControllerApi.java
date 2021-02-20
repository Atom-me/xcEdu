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
@Api(tags = "课程搜索")
public interface EsCourseControllerApi {

    @ApiOperation(value = "课程搜索")
    QueryResponseResult list(int page, int size, CourseSearchParam courseSearchParam);


    @ApiOperation(value = "根据id查询课程信息")
    Map<String, EsCoursePub> getAll(String id);

    @ApiOperation(value = "根据课程计划查询媒资信息")
    EsTeachplanMediaPub getMedia(String teachplanId);
}
