package com.xuecheng.framework.domain.learning;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 学生选课信息表
 *
 * @author admin
 * @date 2018/2/10
 */
@Data
@ToString
@Entity
@Table(name = "xc_learning_course")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcLearningCourse implements Serializable {
    private static final long serialVersionUID = -916357210051789799L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;

    /**
     * 课程id
     */
    @Column(name = "course_id")
    private String courseId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 有效性
     */
    private String valid;

    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 选课状态
     */
    private String status;

}
