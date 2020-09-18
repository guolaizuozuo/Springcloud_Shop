package com.abc.service;


import com.abc.dto.OrderDTO;

public interface OrderService {
    /** 创建订单. */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 完结订单（只能卖家操作）
     * @param orderId
     * @return
     */
    OrderDTO finish(String orderId);
}
