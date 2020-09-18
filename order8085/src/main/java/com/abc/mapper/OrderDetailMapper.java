package com.abc.mapper;

import com.abc.dataobject.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    @Insert("insert into order_detail(detail_id,order_id,product_id,product_name,product_price,product_quantity,product_icon)"
            +"values(#{detailId},#{orderId},#{productId},#{productName},#{productPrice},#{productQuantity},#{productIcon})"
    )
    int add(OrderDetail orderDetail);

    @Select("select * from order_detail where order_id=#{orderId}")
    List<OrderDetail> findByOrderId(String orderId);

}
