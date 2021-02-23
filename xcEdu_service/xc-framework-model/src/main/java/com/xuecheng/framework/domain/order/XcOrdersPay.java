package com.xuecheng.framework.domain.order;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 订单支付表，记录订单的支付状态
 *
 * @author admin
 * @date 2018/2/10
 */
@Data
@ToString
@Entity
@Table(name = "xc_orders_pay")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcOrdersPay implements Serializable {
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
     * 支付系统订单号
     */
    @Column(name = "pay_number")
    private String payNumber;

    /**
     * 交易状态
     */
    private String status;

}
