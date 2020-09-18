package com.abc.mapper;

import com.abc.dataobject.OrderMaster;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;

@Mapper
public interface OrderMasterMapper {

    @Insert("insert into order_master(order_id,buyer_name,buyer_phone,buyer_address,buyer_openid,order_amount,order_status,pay_status)"
             +"values(#{orderId},#{buyerName},#{buyerPhone},#{buyerAddress},#{buyerOpenid},#{orderAmount},#{orderStatus},#{payStatus})"
    )
    int add(OrderMaster orderMaster);

    @Select("select * from order_master where order_id =#{orderId}")
    Optional<OrderMaster> findById(String orderId);

    @Update("update order_master set order_status=#{orderStatus} where order_id=#{orderId}")
    int saveOrderStatus(OrderMaster orderMaster);
}
