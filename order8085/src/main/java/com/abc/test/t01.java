package com.abc.test;

import com.abc.dataobject.OrderDetail;
import com.abc.utils.JsonUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class t01 {
    @Test
    public void contextLoads() {

        List<OrderDetail> list=new ArrayList<>();

        OrderDetail m=new OrderDetail();
        m.setProductId("157875196366160022");
        m.setProductName("皮蛋粥");
        m.setProductPrice(new BigDecimal(0.01));
        m.setProductQuantity(10);
        list.add(m);
        String s = JsonUtil.toJSON(list);
        System.out.println(s);

    }

}
