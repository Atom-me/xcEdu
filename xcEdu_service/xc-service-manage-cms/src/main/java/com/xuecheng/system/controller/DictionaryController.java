package com.xuecheng.system.controller;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.system.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author atom
 */
@Api(tags = "数据字典接口")
@RestController
@RequestMapping("sys/dictionary")
public class DictionaryController extends BaseController {

    @Resource
    private DictionaryService dictionaryService;

    /**
     * 按type获取数据字段值
     *
     * @param type 数据类型
     * @return SysDictionary
     */
    @ApiOperation(value = "按type获取数据字段值")
    @GetMapping("get/{type}")
    public SysDictionary getDictionaryByType(@PathVariable String type) {
        SysDictionary sysDictionary = dictionaryService.findByType(type);
        isNullOrEmpty(sysDictionary, CommonCode.PARAMS_ERROR);
        return sysDictionary;
    }

}
