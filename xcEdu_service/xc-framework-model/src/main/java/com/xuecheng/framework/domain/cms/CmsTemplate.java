package com.xuecheng.framework.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: mrt.
 * @Description:
 * @Date:Created in 2018/1/24 10:04.
 * @Modified By:
 */
@Data
@Document(collection = "cms_template")
public class CmsTemplate {

    /**
     * 站点ID
     */
    private String siteId;
    /**
     * 模版ID 对应 cms_template集合中的 _id 字段
     */
    @Id
    private String templateId;
    /**
     * 模版名称
     */
    private String templateName;
    /**
     * 模版参数
     */
    private String templateParameter;

    /**
     * 模版文件Id
     */
    private String templateFileId;
}
