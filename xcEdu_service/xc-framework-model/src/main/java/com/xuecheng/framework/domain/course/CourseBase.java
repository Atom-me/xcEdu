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
@ToString
@Entity
@Table(name = "course_base")
//@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class CourseBase implements Serializable {
    private static final long serialVersionUID = -916357110051689486L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    private String name;
    private String users;
    private String mt;
    private String st;
    private String grade;
    private String studymodel;
    private String teachmode;
    private String description;
    private String status;
    /**
     * 教育机构ID，细粒度授权使用，控制用户只能查询自己机构的课程
     */
    @Column(name = "company_id")
    private String companyId;
    @Column(name = "user_id")
    private String userId;

}
