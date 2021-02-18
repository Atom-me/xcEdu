package com.xuecheng.framework.domain.system;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * 数据字典表，存在mongodb中
 *
 * @author admin
 * @date 2018/2/6
 */
@Data
@Document(collection = "sys_dictionary")
public class SysDictionary {

    @Id
    private String id;

    /**
     * 配置项名称
     */
    @Field("d_name")
    private String dName;

    /**
     * 配置项编号，唯一
     */
    @Field("d_type")
    private String dType;

    /**
     * 配置项的值
     */
    @Field("d_value")
    private List<SysDictionaryValue> dValue;

}
