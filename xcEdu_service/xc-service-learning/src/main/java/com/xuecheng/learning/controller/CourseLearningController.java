package com.xuecheng.learning.controller;

import com.xuecheng.api.learning.CourseLearningControllerApi;
import com.xuecheng.framework.domain.learning.response.GetMediaResult;
import com.xuecheng.learning.service.LearningService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author atom
 */
@RestController
@RequestMapping("learning/course")
public class CourseLearningController implements CourseLearningControllerApi {

    @Resource
    private LearningService learningService;

    /**
     * 获取课程学习地址:媒资文件播放地址
     *
     * @param courseId    课程ID
     * @param teachplanId 学习计划ID
     * @return
     */
    @Override
    @GetMapping("getmedia/{courseId}/{teachplanId}")
    public GetMediaResult getMedia(@PathVariable("courseId") String courseId, @PathVariable("teachplanId") String teachplanId) {
        //获取课程学习地址
        return learningService.getMedia(courseId, teachplanId);
    }
}