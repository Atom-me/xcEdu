package com.xuecheng.manage_media.service;

import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.service.BaseService;
import com.xuecheng.manage_media.dao.MediaFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author atom
 */
@Slf4j
@Service
public class MediaFileService extends BaseService {

    @Resource
    private MediaFileRepository mediaFileRepository;

    /**
     * 分页查询媒资文件列表
     *
     * @param page                  当前页码
     * @param size                  每页记录数
     * @param queryMediaFileRequest 查询条件
     * @return QueryResponseResult
     */
    public QueryResponseResult findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest) {
        // 处理分页参数
        if (page <= 0) {
            page = 1;
        }
        //页码从0开始
        page = page - 1;

        // 处理分页查询条件
        if (Objects.isNull(queryMediaFileRequest)) {
            queryMediaFileRequest = new QueryMediaFileRequest();
        }
        MediaFile mediaFile = new MediaFile();
        if (StringUtils.isNotBlank(queryMediaFileRequest.getFileOriginalName())) {
            mediaFile.setFileOriginalName(queryMediaFileRequest.getFileOriginalName());
        }
        if (StringUtils.isNotBlank(queryMediaFileRequest.getProcessStatus())) {
            mediaFile.setProcessStatus(queryMediaFileRequest.getProcessStatus());
        }
        if (StringUtils.isNotBlank(queryMediaFileRequest.getTag())) {
            mediaFile.setTag(queryMediaFileRequest.getTag());
        }
        //ExampleMatcher对象，它是匹配“实体对象”的，表示了如何使用“实体对象”中的“值”进行查询，它代表的是“查询方式”，解释了如何去查的问题。
        // 如：要查询姓“X”的客户，即姓名以“X”开头的客户，该对象就表示了“以某某开头的”这个查询方式，
        // 如上例中:withMatcher(“userName”, GenericPropertyMatchers.startsWith())
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("fileOriginalName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<MediaFile> example = Example.of(mediaFile, exampleMatcher);

        // 查询
        Page<MediaFile> mediaFiles = mediaFileRepository.findAll(example, PageRequest.of(page, size));

        QueryResult<MediaFile> queryResult = new QueryResult<>(mediaFiles.getContent(), mediaFiles.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 删除指定ID的mediaFile
     *
     * @param id 媒资文件ID
     */
    public void delete(String id) {
        mediaFileRepository.deleteById(id);
    }
}
