package com.xuecheng.framework.domain.cms;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @Author: mrt.
 * @Description:
 * @Date:Created in 2018/1/24 10:04.
 * @Modified By:
 */
@Data
@Document(collection = "cms_page")
public class CmsPage {
    /**
     * 页面名称、别名、访问地址、类型（静态/动态）、页面模版、状态
     */
    /**
     * 站点ID
     */
    private String siteId;
    /**
     * 页面ID, @Id 对应 cms_page 集合中的 _id 字段
     */
    @Id
    private String pageId;
    /**
     * 页面名称
     */
    private String pageName;
    /**
     * 别名
     */
    private String pageAliase;
    /**
     * 访问地址
     */
    private String pageWebPath;
    /**
     * 参数
     */
    private String pageParameter;
    /**
     * 物理路径
     */
    private String pagePhysicalPath;
    /**
     * 类型（静态/动态）
     */
    private String pageType;
    /**
     * 页面模版
     */
    private String pageTemplate;
    /**
     * 页面静态化内容
     */
    private String pageHtml;
    /**
     * 状态
     */
    private String pageStatus;
    /**
     * 创建时间
     */
    private Date pageCreateTime;
    /**
     * 模版id
     */
    private String templateId;
    /**
     * 参数列表
     */
    private List<CmsPageParam> pageParams;
    /**
     * 模版文件Id
     */
//    private String templateFileId;
    /**
     * 静态文件Id
     */
    private String htmlFileId;
    /**
     * 获取模版数据模型的Url，它基于HTTP方式，CMS对页面进行静态化时会从页面信息中读取dataUrl,通过HTTP远程调用的方法请求dataUrl获取数据模型
     */
    private String dataUrl;

}
