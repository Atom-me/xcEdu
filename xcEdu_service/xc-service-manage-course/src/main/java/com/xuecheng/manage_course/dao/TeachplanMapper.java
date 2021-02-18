package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author atom
 */
@Mapper
public interface TeachplanMapper {

    /**
     * 查询课程教学计划列表
     *
     * @param courseId 课程ID
     * @return TeachplanNode
     */
    TeachplanNode findList(String courseId);

}
