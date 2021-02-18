package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDao {
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeachplanMapper teachplanMapper;


    /**
     * 测试 spring data jpa
     */
    @Test
    public void testCourseBaseRepository() {
        Optional<CourseBase> optional = courseBaseRepository.findById("402885816243d2dd016243f24c030002");
        if (optional.isPresent()) {
            CourseBase courseBase = optional.get();
            System.err.println(courseBase);
        }

    }

    /**
     * 测试 mybatis
     */
    @Test
    public void testCourseMapper() {
        CourseBase courseBase = courseMapper.findCourseBaseById("402885816243d2dd016243f24c030002");
        System.err.println(courseBase);

    }


    /**
     * 查询课程教学计划表，属性结构
     */
    @Test
    public void testTeachPlan() {
        TeachplanNode list = teachplanMapper.findList("4028e581617f945f01617f9dabc40000");
        System.err.println(list);
    }
}
