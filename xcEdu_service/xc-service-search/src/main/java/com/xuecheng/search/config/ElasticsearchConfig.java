package com.xuecheng.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author atom
 */
@Data
@Component
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchConfig {

    /**
     * 课程信息查询返回的字段
     */
    private String esCourseSourceField;

    /**
     * 课程计划媒体资源信息查询返回的字段
     */
    private String esCourseMediaSourceField;
}
