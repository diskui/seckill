package com.example.seckill.vo;

import com.example.seckill.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* 订单详情返回 对象  */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {

    private Order order;
    private GoodsVo goodsVo;
}
