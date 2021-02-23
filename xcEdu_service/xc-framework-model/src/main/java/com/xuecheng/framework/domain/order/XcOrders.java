package com.xuecheng.framework.domain.order;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单主表，记录订单的主要信息
 *
 * @author admin
 * @date 2018/2/10
 */
@Data
@ToString
@Entity
@Table(name = "xc_orders")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class XcOrders implements Serializable {
    private static final long serialVersionUID = -916357210051689789L;

    /**
     * 订单号
     */
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(name = "order_number", length = 32)
    private String orderNumber;

    /**
     * 定价
     */
    @Column(name = "initial_price")
    private Float initialPrice;

    /**
     * 交易价
     */
    private Float price;

    /**
     * 起始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;
    /**
     * 交易状态
     */
    private String status;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 订单明细
     */
    @Column(name = "details")
    private String details;

}
