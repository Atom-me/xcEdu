package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * 统一异常处理类，可以在各个资源服务中自定义自己服务的统一异常处理器，自定义自己服务的异常代码，只需要继承这个类即可
 *
 * @author atom
 */
@Slf4j
@ControllerAdvice
public class ExceptionCatch {

    /**
     * 已知异常种类列表
     */
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> OPTIONS;

    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    static {
        OPTIONS = builder
                .put(HttpMessageNotReadableException.class, CommonCode.PARAMS_ERROR)
                .put(MissingServletRequestPartException.class, CommonCode.PARAMS_ERROR)
                .build();
    }

    /**
     * 自定义异常类型处理
     *
     * @param e CustomerException
     */
    @ResponseBody
    @ExceptionHandler(CustomerException.class)
    public ResponseResult customerException(CustomerException e) {
        log.error("发生异常, 异常信息：{}", e.getMessage());
        return new ResponseResult(e.getResultCode());
    }

    /**
     * 全局异常类型处理
     *
     * @param e Exception
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult exception(Throwable e) {
        log.error("发生异常, 异常信息：{}", e.getMessage());
        // 该异常为已知异常类型
        if (OPTIONS.containsKey(e.getClass())) {
            return new ResponseResult(OPTIONS.get(e.getClass()));
        }
        return new ResponseResult(CommonCode.SERVER_ERROR);
    }


}
