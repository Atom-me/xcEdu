package com.xuecheng.auth.client;

import com.xuecheng.framework.client.XcServiceList;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author atom
 */
@FeignClient(XcServiceList.XC_SERVICE_UCENTER)
public interface UserClient {

    /**
     * 查询用户信息，用户公司信息，用户权限信息
     *
     * @param username
     * @return
     */
    @GetMapping("ucenter/getuserext")
    XcUserExt findByUsername(@RequestParam("username") String username);

}
