package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * cms page 查询条件模型封装
 *
 * @author atom
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryPageRequest extends RequestData {

    /**
     * 站点id
     */
    @ApiModelProperty("站点ID")
    private String siteId;
    /**
     * 页面ID
     */
    @ApiModelProperty("页面ID")
    private String pageId;
    /**
     * 页面名称
     */
    @ApiModelProperty("页面名称")
    private String pageName;
    /**
     * 别名
     */
    @ApiModelProperty("页面别名")
    private String pageAliase;
    /**
     * 模版id
     */
    @ApiModelProperty("模板ID")
    private String templateId;

}
