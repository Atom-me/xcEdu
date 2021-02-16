package com.xuecheng.framework.web;

import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.ResultCode;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author mrt
 * @date 2018/5/22
 */
public class BaseController {

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected HttpSession session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {

        this.request = request;

        this.response = response;

        this.session = request.getSession();

    }

    /**
     * 字符串非空校验并抛出异常
     *
     * @param content    内容
     * @param resultCode 错误码
     */
    public void isNullOrEmpty(String content, ResultCode resultCode) {
        if (StringUtils.isEmpty(content)) {
            ExceptionCast.cast(resultCode);
        }
    }

    /**
     * 字符串非空校验并抛出异常
     *
     * @param content    内容
     * @param resultCode 错误码
     */
    public void isNullOrEmpty(Object content, ResultCode resultCode) {
        if (content == null) {
            ExceptionCast.cast(resultCode);
        }
    }

}
