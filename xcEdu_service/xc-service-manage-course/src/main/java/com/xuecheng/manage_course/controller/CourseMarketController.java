package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseMarketControllerApi;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_course.service.CourseMarketService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Atom
 */
@RestController
@RequestMapping("course/coursemarket")
public class CourseMarketController extends BaseController implements CourseMarketControllerApi {

    @Resource
    private CourseMarketService courseMarketService;

    @Override
    @GetMapping("/get/{courseId}")
    public CourseMarket getCourseMarketById(@PathVariable("courseId") String courseId) {
        return courseMarketService.getCourseMarketById(courseId);
    }

    /**
     * 接口实现可采用先查询课程营销信息，如果存在则更新，否则添加课程营销信息
     *
     * @param id
     * @param courseMarket
     * @return
     */
    @Override
    @PostMapping("/update/{id}")
    public ResponseResult updateCourseMarket(@PathVariable("id") String id, @RequestBody CourseMarket courseMarket) {
        CourseMarket courseMarketUpdateResult = courseMarketService.updateCourseMarket(id, courseMarket);
        if (Objects.nonNull(courseMarketUpdateResult)) {
            return new ResponseResult(CommonCode.SUCCESS);
        } else {
            return new ResponseResult(CommonCode.FAIL);
        }
    }
}
