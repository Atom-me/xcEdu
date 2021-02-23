package com.xuecheng.framework.domain.order;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单明细表
 * 记录订单的明细信息
 *
 * @author admin
 * @date 2018/2/10
 */
@Data
@ToString
@Entity
@Table(name = "xc_orders_detail")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcOrdersDetail implements Serializable {
    private static final long serialVersionUID = -916357210051689789L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;

    /**
     * 订单号
     */
    @Column(name = "order_number")
    private String orderNumber;

    /**
     * 商品id
     */
    @Column(name = "item_id")
    private String itemId;

    /**
     * 商品数量
     */
    @Column(name = "item_num")
    private Integer itemNum;

    /**
     * 金额
     */
    @Column(name = "item_price")
    private Float itemPrice;

    /**
     * 课程有效性
     */
    private String valid;

    /**
     * 课程开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 课程结束时间
     */
    @Column(name = "end_time")
    private Date endTime;
}
