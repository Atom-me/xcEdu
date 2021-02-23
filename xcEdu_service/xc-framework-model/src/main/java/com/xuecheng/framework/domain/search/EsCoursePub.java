package com.xuecheng.framework.domain.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 课程信息ES索引
 *
 * @author atom
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "xc_course", type = "doc", shards = 1, replicas = 0)
public class EsCoursePub {

    private static final long serialVersionUID = -916357110051689487L;

    @Id
    private String id;

    @Field
    private String name;
    @Field
    private String users;
    @Field
    private String mt;
    @Field
    private String st;
    @Field
    private String grade;
    @Field
    private String studymodel;
    @Field
    private String teachmode;
    @Field
    private String description;
    /**
     * 图片
     */
    @Field
    private String pic;
    /**
     * 时间戳
     */
    @Field
    private Date timestamp;
    @Field
    private String charge;
    @Field
    private String valid;
    @Field
    private String qq;
    @Field
    private BigDecimal price;
    @Field
    private BigDecimal price_old;
    @Field
    private String expires;
    /**
     * 课程计划
     */
    @Field
    private String teachplan;
    /**
     * 课程发布时间
     */
    @Field
    private String pub_time;

}
