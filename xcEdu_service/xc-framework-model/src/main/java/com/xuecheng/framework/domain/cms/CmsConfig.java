package com.xuecheng.framework.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * cms_page 页面模版数据模型
 *
 * @author admin
 * @date 2018/2/6
 */
@Data
@Document(collection = "cms_config")
public class CmsConfig {

    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * 页面数据模型名称
     */
    private String name;
    /**
     * 页面数据模型项目
     */
    private List<CmsConfigModel> model;

}
