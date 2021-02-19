package com.xuecheng.framework.domain.course;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 课程信息分布在 course_base、course_pic、teachplan 等不同的表中。
 * 课程发布成功后为了方便logstash数据采集，进行索引，将这几张表的数据合并到一张表（course_pub）中，作为课程发布信息。
 *
 * @author admin
 * @date 2018/2/10
 */
@Data
@Entity
@Table(name = "course_pub")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class CoursePub implements Serializable {
    private static final long serialVersionUID = -916357110051689487L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    private String id;
    /**
     * 课程名称
     */
    private String name;
    /**
     * 使用人群
     */
    private String users;
    /**
     * 大分类
     */
    private String mt;
    /**
     * 小分类
     */
    private String st;
    /**
     * 课程等级
     */
    private String grade;
    /**
     * 学习模式
     */
    private String studymodel;
    /**
     * 教育模式
     */
    private String teachmode;
    /**
     * 课程介绍
     */
    private String description;
    /**
     * 课程图片
     */
    private String pic;
    /**
     * 时间戳，logstash使用
     */
    private Date timestamp;
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
     * 原价格
     */
    private Float price_old;
    /**
     * 过期时间
     */
    private String expires;
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
    /**
     * 课程计划，转为json入库
     */
    private String teachplan;
    /**
     * 课程发布时间
     */
    @Column(name = "pub_time")
    private String pubTime;
}
