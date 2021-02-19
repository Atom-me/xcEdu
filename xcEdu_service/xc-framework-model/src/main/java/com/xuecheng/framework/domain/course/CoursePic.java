package com.xuecheng.framework.domain.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author admin
 * @date 2018/2/10
 */
@Data
@Entity
@Table(name = "course_pic")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class CoursePic implements Serializable {
    private static final long serialVersionUID = -916357110051689486L;

    /**
     * 一个课程只能有一张图片
     * 课程ID
     */
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    private String courseid;
    /**
     * 图片ID
     */
    private String pic;

}
