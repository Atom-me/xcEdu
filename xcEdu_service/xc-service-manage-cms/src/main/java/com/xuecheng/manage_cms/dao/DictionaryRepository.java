package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author atom
 */
public interface DictionaryRepository extends MongoRepository<SysDictionary, String> {

    /**
     * 根据配置项的code(type)查询具体配置信息
     *
     * @param type
     * @return
     */
    SysDictionary findByDType(String type);

}
