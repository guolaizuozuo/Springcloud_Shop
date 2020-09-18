package com.abc.controller;



import com.abc.converter.OrderForm2OrderDTOConverter;
import com.abc.dto.OrderDTO;
import com.abc.enums.ResultEnum;
import com.abc.exception.SellException;
import com.abc.form.OrderForm;
import com.abc.service.OrderService;
import com.abc.utils.ResultVOUtil;
import com.abc.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            log.error("【创建订单】参数不正确，orderForm={}",orderForm);
//            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
//        }

        //模拟一个商品
String items="[" +
        "{\"productId\": \"157875196366160022\",\"productName\": \"蛋粥\",\"productPrice\":0.01,\"productQuantity\": 10}," +
        "{\"productId\": \"164103465734242707\",\"productName\": \"蜜汁鸡翅\",\"productPrice\":0.2,\"productQuantity\": 10}" +
        "]";
        orderForm.setItems(items);
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<String,String>();
        map.put("orderId",createResult.getOrderId());
        return ResultVOUtil.success(map);
    }

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    @PostMapping("/finish")
    public ResultVO<OrderDTO> finish(@RequestParam("orderId")String orderId){
        return ResultVOUtil.success(orderService.finish(orderId));
    }

}
