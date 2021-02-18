package com.xuecheng.system.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.framework.service.BaseService;
import com.xuecheng.manage_cms.dao.DictionaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author atom
 */
@Slf4j
@Service
public class DictionaryService extends BaseService {

    @Resource
    private DictionaryRepository dictionaryRepository;

    /**
     * 按type获取数据字段值
     *
     * @param type 数据类型
     * @return SysDictionary
     */
    public SysDictionary findByType(String type) {
        return dictionaryRepository.findByDType(type);
    }
}
