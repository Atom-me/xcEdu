package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CoursePic;
import org.springframework.data.repository.CrudRepository;

/**
 * @author atom
 */
public interface CoursePicRepository extends CrudRepository<CoursePic, String> {

    /**
     * 根据courseid 删除图片关联信息
     *
     * @param courseId
     * @return
     */
    long deleteByCourseid(String courseId);

}
