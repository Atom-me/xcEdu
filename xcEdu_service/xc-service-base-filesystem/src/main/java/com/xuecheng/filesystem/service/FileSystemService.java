package com.xuecheng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author atom
 */
@Slf4j
@Service
public class FileSystemService extends BaseService {

    @Resource
    private FileSystemRepository fileSystemRepository;

    @Resource
    private FastFileStorageClient fastFileStorageClient;

    /**
     * 文件上传
     *
     * @param file        文件
     * @param filetag     文件标签
     * @param businesskey 业务key
     * @param metadata    元数据, JSON格式
     * @return UploadFileResult 上传结果
     */
    public FileSystem upload(MultipartFile file, String filetag, String businesskey, String metadata) {
        // 上传文件
        String fileId = uploadFile(file);
        // 创建文件信息对象
        FileSystem fileSystem = buildFileSystemObject(file, filetag, businesskey, metadata, fileId);
        // 保存到文件信息数据库
        return fileSystemRepository.save(fileSystem);
    }

    /**
     * 构建FileSystem对象信息
     *
     * @param file
     * @param filetag
     * @param businesskey
     * @param metadata
     * @param fileId
     * @return
     */
    private FileSystem buildFileSystemObject(MultipartFile file, String filetag, String businesskey, String metadata, String fileId) {
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(fileId);
        fileSystem.setFilePath(fileId);
        fileSystem.setBusinesskey(businesskey);
        fileSystem.setFiletag(filetag);
        if (StringUtils.isNotBlank(metadata)) {
            try {
                Map metadataMap = JSON.parseObject(metadata, Map.class);
                fileSystem.setMetadata(metadataMap);
            } catch (Exception e) {
                log.error("[文件上传] 解析文件JSON元数据失败, metadata = {}", metadata);
                ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_METAERROR);
            }
        }
        fileSystem.setFileName(file.getOriginalFilename());
        fileSystem.setFileSize(file.getSize());
        fileSystem.setFileType(file.getContentType());
        return fileSystem;
    }

    /**
     * 文件上传到FastDFS
     *
     * @param file 文件
     * @return 文件ID
     */
    private String uploadFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
            }
            // 获取文件后缀名
            String filename = file.getOriginalFilename();
            assert filename != null;

//            String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
            String fileExtName = FilenameUtils.getExtension(filename);

            // 上传文件
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), fileExtName, null);
            // 返回文件ID，即文件路径/group1/M00/00/01/wKhlQFp5wnCAG-kAAATMXxpSaMg864.png
            return storePath.getFullPath();
        } catch (IOException e) {
            log.error("读取文件内容发生IO异常.  e = ", e);
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
            return null;
        }
    }
}
