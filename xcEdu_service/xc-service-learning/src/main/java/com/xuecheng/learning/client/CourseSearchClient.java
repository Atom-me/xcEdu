package com.xuecheng.learning.client;

import com.xuecheng.framework.client.XcServiceList;
import com.xuecheng.framework.domain.search.EsTeachplanMediaPub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author atom
 */
@FeignClient(value = XcServiceList.XC_SERVICE_SEARCH)
public interface CourseSearchClient {

    /**
     * 学习页面:查询课程计划媒体资源信息接口
     *
     * @param teachplanId 课程计划ID
     * @return
     */
    @GetMapping(value = "search/course/getmedia/{teachplanId}")
    EsTeachplanMediaPub getMedia(@PathVariable("teachplanId") String teachplanId);

}