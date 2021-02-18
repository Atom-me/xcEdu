package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.CourseBase;
import lombok.Data;
import lombok.ToString;

/**
 * 我的课程列表返回对象，包含课程信息和课程图片信息
 *
 * @author admin
 * @date 2018/2/10
 */
@Data
@ToString(callSuper = true)
public class CourseInfo extends CourseBase {

    /**
     * 课程图片
     */
    private String pic;

}
