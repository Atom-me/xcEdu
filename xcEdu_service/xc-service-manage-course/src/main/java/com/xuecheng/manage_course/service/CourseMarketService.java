package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.service.BaseService;
import com.xuecheng.manage_course.dao.CourseMarketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * @author atom
 */
@Slf4j
@Service
public class CourseMarketService extends BaseService {

    @Resource
    private CourseMarketRepository courseMarketRepository;

    public CourseMarket getCourseMarketById(String courseId) {
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(courseId);
        return courseMarketOptional.orElse(null);
    }

    public CourseMarket updateCourseMarket(String id, CourseMarket courseMarket) {
        CourseMarket one = this.getCourseMarketById(id);
        if (Objects.nonNull(one)) {
            one.setCharge(courseMarket.getCharge());
            //课程有效期，开始时间
            one.setStartTime(courseMarket.getStartTime());
            //课程有效期，结束时间
            one.setEndTime(courseMarket.getEndTime());
            one.setPrice(courseMarket.getPrice());
            one.setQq(courseMarket.getQq());
            one.setValid(courseMarket.getValid());
            courseMarketRepository.save(one);
        } else {
            //添加课程营销信息
            one = new CourseMarket();
            BeanUtils.copyProperties(courseMarket, one);
            //设置课程id
            one.setId(id);
            courseMarketRepository.save(one);
        }
        return one;

    }
}
