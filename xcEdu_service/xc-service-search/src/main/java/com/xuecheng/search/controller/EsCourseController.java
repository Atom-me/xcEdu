package com.xuecheng.search.controller;

import com.xuecheng.api.search.EsCourseControllerApi;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.domain.search.EsCoursePub;
import com.xuecheng.framework.domain.search.EsTeachplanMediaPub;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.search.service.EsCourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author atom
 */
@RestController
@RequestMapping("search/course")
public class EsCourseController extends BaseController implements EsCourseControllerApi {

    @Resource
    private EsCourseService esCourseService;


    /**
     * 课程综合搜索
     *
     * @param page
     * @param size
     * @param courseSearchParam
     * @return
     */
    @Override
    @GetMapping("list/{page}/{size}")
    public QueryResponseResult list(@PathVariable int page,
                                    @PathVariable int size,
                                    CourseSearchParam courseSearchParam) {
        return esCourseService.findList(page, size, courseSearchParam);
    }

    /**
     * 查询课程信息，包含课程学习计划
     *
     * @param courseId 课程ID
     * @return
     */
    @Override
    @GetMapping("getall/{id}")
    public Map<String, EsCoursePub> getAll(@PathVariable("id") String courseId) {
        return esCourseService.getAll(courseId);
    }

    /**
     * 学习页面查询课程计划媒体资源接口
     *
     * @param teachplanId 课程计划ID
     * @return
     */
    @Override
    @GetMapping("getmedia/{teachplanId}")
    public EsTeachplanMediaPub getMedia(@PathVariable String teachplanId) {
        //将课程计划id放在数组中，为调用service作准备
        String[] teachplanIds = new String[]{teachplanId};
        //通过service查询ES获取课程媒资信息
        List<EsTeachplanMediaPub> esTeachplanMediaPubList = esCourseService.getMedia(teachplanIds);

        return esTeachplanMediaPubList.isEmpty() ? null : esTeachplanMediaPubList.get(0);
    }
}
