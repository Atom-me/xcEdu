package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 课程预览页面所需要的数据模型
 *
 * @author atom
 */
@Data
@NoArgsConstructor
public class CourseView implements Serializable {

    /**
     * 课程基本信息
     */
    private CourseBase courseBase;

    /**
     * 课程营销信息
     */
    private CourseMarket courseMarket;

    /**
     * 课程图片信息
     */
    private CoursePic coursePic;

    /**
     * 课程学习计划（教学计划）信息
     */
    private TeachplanNode teachplanNode;
}
