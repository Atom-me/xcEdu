package com.xuecheng.manage_course.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author atom
 */
@Data
@Component
@ConfigurationProperties(prefix = "course-publish")
public class CoursePublishConfig {
    /**
     * 课程发布站点ID
     */
    private String siteId;
    /**
     * 课程页面模版ID
     */
    private String templateId;
    /**
     * 课程预览URL
     */
    private String previewUrl;
    /**
     * 课程发布（不是预览）页面webPath
     */
    private String pageWebPath;
    /**
     * 页面物理路径，是一个相对于站点物理路径的相对路径。
     */
    private String pagePhysicalPath;
    /**
     * 获取课程详情页面模型数据地址前缀
     */
    private String dataUrlPre;
}
