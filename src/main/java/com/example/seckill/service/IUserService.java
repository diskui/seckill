package com.example.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckill.pojo.User;
import com.example.seckill.vo.LoginVo;
import com.example.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2022-07-29
 */
public interface IUserService extends IService<User> {


    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    // 根据 cookie 获取用户
    User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response);


    RespBean updatePassword(String userTicket,String password,HttpServletRequest request,HttpServletResponse response);
}
