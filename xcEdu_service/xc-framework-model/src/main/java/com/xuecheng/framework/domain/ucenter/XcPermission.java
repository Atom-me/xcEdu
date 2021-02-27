package com.xuecheng.framework.domain.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 权限数据模型： 角色权限关系表
 * <p>
 * 一个菜单（功能）有多个权限
 * 一个角色也拥有有多个权限
 * 一个权限也可被多个角色所拥有
 *
 * @author admin
 * @date 2018/3/19
 */
@Data
@ToString
@Entity
@Table(name = "xc_permission")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcPermission {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name = "roleId")
    private String role_id;
    @Column(name = "menuId")
    private String menu_id;
    @Column(name = "createTime")
    private Date create_time;


}
