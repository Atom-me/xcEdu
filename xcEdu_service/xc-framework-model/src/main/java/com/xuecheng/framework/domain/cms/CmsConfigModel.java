package com.xuecheng.framework.domain.cms;

import lombok.Data;

import java.util.Map;

/**
 * 页面数据模型
 *
 * @author admin
 * @date 2018/2/6
 */
@Data
public class CmsConfigModel {
    /**
     * 主键
     */
    private String key;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目URL
     */
    private String url;
    /**
     * 项目复杂值
     */
    private Map mapValue;
    /**
     * 项目简单值
     */
    private String value;

}
