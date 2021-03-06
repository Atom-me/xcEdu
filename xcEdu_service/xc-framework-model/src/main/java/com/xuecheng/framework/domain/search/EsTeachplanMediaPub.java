package com.xuecheng.framework.domain.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * 课程媒体资源ES索引
 *
 * @author atom
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "xc_course_media", type = "doc", shards = 1, replicas = 0)
public class EsTeachplanMediaPub {

    /**
     * 课程ID
     */
    @Id
    private String courseid;

    /**
     * 媒体资源文件原始文件名
     */
    @Field
    private String media_fileoriginalname;

    /**
     * 媒体资源文件ID
     */
    @Field
    private String media_id;

    /**
     * 媒资文件访问地址(m3u8文件地址)
     * 5/f/5fbb79a2016c0eb609ecd0cd3dc48016/hls/5fbb79a2016c0eb609ecd0cd3dc48016.m3u8
     */
    @Field
    private String media_url;

    /**
     * 课程学习计划ID
     */
    @Field
    @JsonProperty("teachplan_id")
    private String teachplanId;

}
