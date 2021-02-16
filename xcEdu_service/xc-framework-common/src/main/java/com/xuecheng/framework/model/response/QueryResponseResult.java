package com.xuecheng.framework.model.response;

import lombok.Data;

/**
 * @author atom
 */
@Data
public class QueryResponseResult extends ResponseResult {

    QueryResult queryResult;

    public QueryResponseResult(ResultCode resultCode, QueryResult queryResult) {
        super(resultCode);
        this.queryResult = queryResult;
    }

}
