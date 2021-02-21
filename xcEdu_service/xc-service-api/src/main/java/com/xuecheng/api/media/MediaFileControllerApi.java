package com.xuecheng.api.media;

import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author atom
 */
@Api(tags = "媒资管理接口")
public interface MediaFileControllerApi {

    /**
     * 分页查询媒资文件列表
     *
     * @param page                  当前页码
     * @param size                  每页记录数
     * @param queryMediaFileRequest 查询条件
     * @return QueryResponseResult
     */
    @ApiOperation(value = "分页查询媒资文件列表")
    QueryResponseResult findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest);

    /**
     * 删除媒资文件
     *
     * @param id 媒资文件ID
     * @return ResponseResult
     */
    @ApiOperation(value = "删除媒资文件")
    ResponseResult delete(String id);


}
