package com.xuecheng.framework.domain.task;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author mrt
 * @date 2018/4/4
 */
@Data
@ToString
@Entity
@Table(name = "xc_task_his")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class XcTaskHis implements Serializable {


    /**
     * 任务id
     */
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    private String id;

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "delete_time")
    private Date deleteTime;

    /**
     * 任务类型
     */
    @Column(name = "task_type")
    private String taskType;

    /**
     * 交换机名称
     */
    @Column(name = "mq_exchange")
    private String mqExchange;

    /**
     * routingkey
     */
    @Column(name = "mq_routingkey")
    private String mqRoutingkey;

    /**
     * 任务请求的内容
     */
    @Column(name = "request_body")
    private String requestBody;
    /**
     * 乐观锁版本号
     */
    private String version;
    /**
     * 任务状态
     */
    private String status;
}
