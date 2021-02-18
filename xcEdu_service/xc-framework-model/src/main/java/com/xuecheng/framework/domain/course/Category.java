package com.xuecheng.framework.domain.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 课程分类表，也是树形结构表
 *
 * @author admin
 * @date 2018/2/7
 */
@Data
@ToString
@Entity
@Table(name = "category")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
//@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Category implements Serializable {
    private static final long serialVersionUID = -906357110051689484L;
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    private String id;
    /**
     * 课程分类名称
     */
    private String name;
    /**
     * 课程分类标签默认和名称一样
     */
    private String label;
    /**
     * 父结点id
     */
    private String parentid;
    /**
     * 是否显示
     */
    private String isshow;
    /**
     * 排序字段
     */
    private Integer orderby;
    /**
     * 是否叶子
     */
    private String isleaf;

}
