package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.Category;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author admin
 * @date 2018/2/7
 */
@Data
@ToString(callSuper = true)
public class CategoryNode extends Category {

    List<CategoryNode> children;

}
