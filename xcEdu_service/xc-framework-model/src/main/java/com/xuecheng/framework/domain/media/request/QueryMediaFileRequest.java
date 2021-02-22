package com.xuecheng.framework.domain.media.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;

/**
 * @author atom
 */
@Data
public class QueryMediaFileRequest extends RequestData {

    /**
     * 媒体资源文件原始名称
     */
    private String fileOriginalName;
    /**
     * 文件处理状态，数据字典
     */
    private String processStatus;
    /**
     * 对文件管理的标签，文件的分类
     */
    private String tag;
}
