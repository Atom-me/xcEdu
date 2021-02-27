package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CoursePlanControllerApi;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_course.service.CoursePlanService;
import com.xuecheng.manage_course.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author atom
 */
@RestController
@RequestMapping("course/teachplan")
public class CoursePlanController extends BaseController implements CoursePlanControllerApi {

    @Resource
    private CoursePlanService coursePlanService;

    @Resource
    private CourseService courseService;

    /**
     * 根据课程ID查询指定课程的课程计划
     *
     * @param courseId 课程ID
     * @return TeachPlanNode
     */
//    @PreAuthorize("hasAuthority('course_teachplan_list')")//拥有course_teachplan_list权限的用户才可以访问这个方法
    @Override
    @GetMapping("list/{courseId}")
    public TeachplanNode findList(@PathVariable String courseId) {
        return coursePlanService.findList(courseId);
    }

    /**
     * 查询指定ID的课程计划
     *
     * @param teachplanId 课程计划ID
     * @return Teachplan
     */
    @Override
    @GetMapping("{teachplanId}")
    public Teachplan findById(@PathVariable String teachplanId) {
        return coursePlanService.findById(teachplanId);
    }

    /**
     * 新增课程计划
     *
     * @param teachplan 课程计划
     * @return ResponseResult
     */
    @Override
    @PostMapping("add")
    public ResponseResult add(@RequestBody Teachplan teachplan) {
        if (Objects.isNull(teachplan) || StringUtils.isBlank(teachplan.getCourseid())) {
            return new ResponseResult(CommonCode.PARAMS_ERROR);
        }
        // 新增
        Teachplan add = coursePlanService.add(teachplan);
        isNullOrEmpty(add, CourseCode.COURSE_PLAN_ADD_ERROR);
        return ResponseResult.SUCCESS();
    }

    /**
     * 编辑课程计划
     *
     * @param teachplan 课程计划
     * @return ResponseResult
     */
    @Override
    @PutMapping("edit")
    public ResponseResult edit(@RequestBody Teachplan teachplan) {
        if (teachplan == null
                || StringUtils.isBlank(teachplan.getCourseid())
                || StringUtils.isBlank(teachplan.getId())) {
            return new ResponseResult(CommonCode.PARAMS_ERROR);
        }
        // 编辑
        Teachplan edit = coursePlanService.edit(teachplan);
        isNullOrEmpty(edit, CourseCode.COURSE_PLAN_ADD_ERROR);
        return ResponseResult.SUCCESS();
    }

    /**
     * 删除课程计划
     *
     * @param teachplanId 课程计划ID
     * @return ResponseResult
     */
    @Override
    @DeleteMapping("{teachplanId}")
    public ResponseResult delete(@PathVariable String teachplanId) {
        coursePlanService.deleteById(teachplanId);
        return ResponseResult.SUCCESS();
    }


    /**
     * 保存课程计划和媒体资源文件的关联
     *
     * @param teachplanMedia
     * @return
     */
    @Override
    @PostMapping("savemedia")
    public ResponseResult saveMedia(@RequestBody TeachplanMedia teachplanMedia) {
        TeachplanMedia saveMedia = courseService.saveMedia(teachplanMedia);
        isNullOrEmpty(saveMedia, CommonCode.SERVER_ERROR);
        return ResponseResult.SUCCESS();
    }
}
