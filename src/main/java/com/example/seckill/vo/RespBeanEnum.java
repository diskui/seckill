package com.example.seckill.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {

    //通用
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"server error"),


    //登录模块 5002
    LOGIN_ERROR(200210,"用户名或密码错误"),
    MOBILE_ERROR(200211,"手机号码格式不正确"),

    BIND_ERROR(200212,"参数校验异常"),
    MOBILE_NOT_EXIST(200213,"手机号码不存在"),
    PASSWORD_UPDATE_FAIL(200214,"更新密码失败"),
    SESSION_ERROR(200215,"用户不存在"),


    //秒杀模块 5005
    EMPTY_STOCK(500500,"库存不足"),
    REPEAT_ERROR(500501,"该商品每人限购一件"),
    REQUEST_ILLEGAL(500502,"请求非法，请重新尝试"),
    ERROR_CAPTCHA(500503,"验证码错误，请重新输入"),
    ACCESS_LIMIT_REACHED(500504,"访问过于频繁"),

    //订单模块 5003
    ORDER_NOT_EXIST(500300,"订单信息不存在"),

    ;


    private final Integer code;
    private final String message;
}
