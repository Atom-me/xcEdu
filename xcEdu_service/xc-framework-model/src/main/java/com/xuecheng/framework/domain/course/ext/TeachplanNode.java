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
@ToString(callSuper = true)
public class TeachplanNode extends Teachplan {

    List<TeachplanNode> children;

    /**
     * 媒体资源文件ID
     * <p>
     * 关联 teachplan_media 表的 media_id ，供页面展示
     */
    private String mediaId;

    /**
     * 媒体资源文件原始文件名
     * <p>
     * 关联 teachplan_media 表的 mediaFileOriginalName ，供页面展示
     */
    private String mediaFileOriginalName;

}
