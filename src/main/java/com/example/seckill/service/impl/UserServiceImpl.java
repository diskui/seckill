package com.example.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckill.exception.GlobalException;
import com.example.seckill.mapper.UserMapper;
import com.example.seckill.pojo.User;
import com.example.seckill.service.IUserService;
import com.example.seckill.util.CookieUtil;
import com.example.seckill.util.MD5Util;
import com.example.seckill.util.UUIDUtil;
import com.example.seckill.util.ValidatorUtil;
import com.example.seckill.vo.LoginVo;
import com.example.seckill.vo.RespBean;
import com.example.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2022-07-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //参数校验

//       if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
//           return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//       }
//
//       if(!ValidatorUtil.isMobile(mobile)){
//           return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//       }


        //根据手机号获取数据
        User user = userMapper.selectById(mobile);
        if (null == user) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }

        //判断密码是否正确
        if (!MD5Util.fromPassToDBPass(password, user.getSlat()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }


        //生成 cookie
        String ticket = UUIDUtil.uuid();
        // 将用户信息存入redis中
        redisTemplate.opsForValue().set("user:"+ticket,user);
//        request.getSession().setAttribute(ticket, user);
        CookieUtil.setCookie(request, response, "userTicket", ticket);

        return RespBean.success(ticket);
    }

    @Override
    public User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response) {

        if(StringUtils.isEmpty(userTicket)){
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:"+userTicket);
        if(user != null){
            CookieUtil.setCookie(request,response,"userTicket",userTicket);
        }
        return user;
    }

    /* update the password */
    @Override
    public RespBean updatePassword(String userTicket, String password,HttpServletRequest request,HttpServletResponse response){

        User user = getUserByCookie(userTicket, request, response);
        if(user == null){
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);
        }
        user.setPassword(MD5Util.inputPassToDBPass(password,user.getSlat()));
        int result = userMapper.updateById(user);
        if(result == 1){
            // 删除 redis
            redisTemplate.delete("user"+userTicket);
            return RespBean.success();
        }


        return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);
    }
}
