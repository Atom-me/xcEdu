package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author atom
 */
@Api(tags = "文件管理服务")
public interface FileSystemControllerApi {

    /**
     * 文件上传
     *
     * @param file        文件
     * @param filetag     文件标签
     * @param businesskey 业务key
     * @param metadata    元数据, JSON格式
     * @return UploadFileResult 上传结果
     */
    @ApiOperation(value = "文件上传")
    UploadFileResult upload(MultipartFile file,
                            String filetag,
                            String businesskey,
                            String metadata);

}
