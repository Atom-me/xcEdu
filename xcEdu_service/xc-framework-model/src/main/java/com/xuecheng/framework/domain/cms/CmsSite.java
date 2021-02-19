package com.xuecheng.framework.domain.cms;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Author: mrt.
 * @Description:
 * @Date:Created in 2018/1/24 9:46.
 * @Modified By:
 */
@Data
@Document(collection = "cms_site")
public class CmsSite {

    /**
     * 站点ID 对应 cms_site  集合中的 _id 字段
     */
    @Id
    private String siteId;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 站点名称
     */
    private String siteDomain;
    /**
     * 站点端口
     */
    private String sitePort;
    /**
     * 站点访问地址
     */
    private String siteWebPath;
    /**
     * 站点创建时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date siteCreateTime;
    /**
     * 站点物理路径
     */
    private String sitePhysicalPath;


}
