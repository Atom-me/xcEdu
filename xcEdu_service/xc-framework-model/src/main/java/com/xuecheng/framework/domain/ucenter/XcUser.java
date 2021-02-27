package com.xuecheng.framework.domain.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 权限数据模型： 用户表，存储系统用户信息，用户类型包括学生，老师，管理员等
 *
 * @author admin
 * @date 2018/3/19
 */
@Data
@ToString
@Entity
@Table(name = "xc_user")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcUser {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    private String username;
    private String password;
    private String salt;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 用户类型
     */
    private String utype;
    private String birthday;
    /**
     * 用户头像
     */
    private String userpic;
    private String sex;
    private String email;
    private String phone;
    private String status;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;


}
