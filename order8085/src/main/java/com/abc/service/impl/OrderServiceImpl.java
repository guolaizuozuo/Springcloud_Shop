package com.abc.service.impl;

import com.abc.client.ProductClient;
import com.abc.dataobject.OrderDetail;
import com.abc.dataobject.OrderMaster;
import com.abc.dataobject.ProductInfo;
import com.abc.dto.CartDTO;
import com.abc.dto.OrderDTO;
import com.abc.enums.OrderStatusEnum;
import com.abc.enums.PayStatusEnum;
import com.abc.enums.ResultEnum;
import com.abc.exception.OrderException;
import com.abc.mapper.OrderDetailMapper;
import com.abc.mapper.OrderMasterMapper;
import com.abc.service.OrderService;
import com.abc.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** 订单服务
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderDetailMapper orderDetailRepository;

    @Autowired
    private OrderMasterMapper orderMasterRepository;

    /**
     * 调用其他服务模块（订单服务调商品服务）
     */
    @Autowired
    private ProductClient productClient;
    
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.getUniqueKey();
        orderDTO.setOrderId(orderId);
        //查询商品信息（调用商品服务）
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfo> productInfoList = productClient.listForOrder(productIdList);

        //读redis

        //计算总价
        BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            for(ProductInfo productInfo:productInfoList){
                if(productInfo.getProductId().equals(orderDetail.getProductId())){
                    //单价x数量
                    orderAmout = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmout);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.getUniqueKey());
                    //订单详情入库
                    orderDetailRepository.add(orderDetail);
                }
            }
        }
        //扣库存(调用商品服务）
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(cartDTOList);

        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        //orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmout);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.add(orderMaster);

        //发送websocket消息
        //webSocket.sendMessage("有新的订单");
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        //1.先查询订单
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);
        if(!orderMasterOptional.isPresent()){
            throw new OrderException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        //2.判断订单状态
        OrderMaster orderMaster = orderMasterOptional.get();
        if(OrderStatusEnum.CANCEL.getCode().equals(orderMaster.getOrderStatus())){
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3.修改订单状态为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterRepository.saveOrderStatus(orderMaster);

        //4.查询订单详情
       List<OrderDetail> orderDetailList =  orderDetailRepository.findByOrderId(orderId);
       if(CollectionUtils.isEmpty(orderDetailList)){
           throw new OrderException(ResultEnum.ORDERDETAIL_NOT_EXIST);
       }

       OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

}
