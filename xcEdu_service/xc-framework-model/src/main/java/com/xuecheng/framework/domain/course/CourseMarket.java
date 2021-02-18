package com.xuecheng.framework.domain.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 课程营销表
 *
 * @author admin
 * @date 2018/2/10
 */
@Data
@ToString
@Entity
@Table(name = "course_market")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class CourseMarket implements Serializable {
    private static final long serialVersionUID = -916357110051689486L;

    /**
     * 课程id
     */
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    private String id;
    /**
     * 收费规则，对应数据字典
     */
    private String charge;
    /**
     * 有效性，对应数据字典
     */
    private String valid;
    /**
     * 咨询qq
     */
    private String qq;
    /**
     * 价格
     */
    private Float price;
    /**
     * 原价
     */
    private Float price_old;
    /**
     * 过期时间
     */
//    private Date expires;
    /**
     * 课程有效期-开始时间
     */
    @Column(name = "start_time")
    private Date startTime;
    /**
     * 课程有效期-结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

}
