package com.ch.crm.settings.service.impl;

import com.ch.crm.exception.UserLoginException;
import com.ch.crm.settings.dao.UserDao;
import com.ch.crm.settings.domain.User;
import com.ch.crm.settings.service.UserService;
import com.ch.crm.utils.DateTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User userLogin(String loginAct, String loginPwd, String ip) throws UserLoginException {

        User user = userDao.login(loginAct, loginPwd);

        if (user==null){
            throw new UserLoginException("账号或密码错误");
        }

        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime)<0){
            throw new UserLoginException("账号已失效");
        }

        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw new UserLoginException("账号已锁定");
        }

        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new UserLoginException("ip地址受限");
        }

        return user;
    }

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }
}
