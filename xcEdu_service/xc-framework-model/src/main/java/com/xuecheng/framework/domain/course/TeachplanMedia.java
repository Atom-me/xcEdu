package com.xuecheng.framework.domain.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author admin
 * @date 2018/2/7
 */
@Data
@ToString
@Entity
@Table(name = "teachplan_media")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class TeachplanMedia implements Serializable {

    private static final long serialVersionUID = -916357110051689485L;

    /**
     * 课程计划id
     */
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(name = "teachplan_id")
    private String teachplanId;

    /**
     * 媒资文件id
     */
    @Column(name = "media_id")
    private String mediaId;

    /**
     * 媒资文件的原始名称
     */
    @Column(name = "media_fileoriginalname")
    private String mediaFileOriginalName;

    /**
     * 媒资文件访问地址(m3u8文件地址)
     * 5/f/5fbb79a2016c0eb609ecd0cd3dc48016/hls/5fbb79a2016c0eb609ecd0cd3dc48016.m3u8
     */
    @Column(name = "media_url")
    private String mediaUrl;

    /**
     * 课程Id
     */
    @Column(name = "courseid")
    private String courseId;

}
