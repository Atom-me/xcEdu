package com.xuecheng.framework.domain.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 权限数据模型： 角色表，存储了系统的角色信息，学生，老师，教学管理员，系统管理员等
 * <p>
 * 角色的产生是为了分配权限产生的
 *
 * @author admin
 * @date 2018/3/19
 */
@Data
@ToString
@Entity
@Table(name = "xc_role")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcRole {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "roleCode")
    private String role_code;
    private String description;
    private String status;
    @Column(name = "createTime")
    private Date create_time;
    @Column(name = "update_time")
    private Date updateTime;


}
