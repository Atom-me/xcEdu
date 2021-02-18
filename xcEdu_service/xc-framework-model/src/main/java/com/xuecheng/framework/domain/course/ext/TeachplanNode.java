package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.Teachplan;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 课程教学计划属性结构
 *
 * @author admin
 * @date 2018/2/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)//打印父类属性
public class TeachplanNode extends Teachplan {

    List<TeachplanNode> children;

    /**
     * 媒资信息
     */
    private String mediaId;
    private String mediaFileOriginalName;

}
