package com.xuecheng.api.learning;

import com.xuecheng.framework.domain.learning.response.GetMediaResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author atom
 */
@Api(tags = "录播课程学习管理")
public interface CourseLearningControllerApi {

    /**
     * 获取课程学习地址:媒资文件播放地址
     *
     * @param courseId    课程ID
     * @param teachplanId 学习计划ID
     * @return
     */
    @ApiOperation(value = "获取课程学习地址")
    GetMediaResult getMedia(String courseId, String teachplanId);

}
