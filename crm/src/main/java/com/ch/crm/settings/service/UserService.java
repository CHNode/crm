package com.ch.crm.settings.service;

import com.ch.crm.exception.UserLoginException;
import com.ch.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User userLogin(String loginAct,String loginPwd,String ip) throws UserLoginException;

    List<User> getUserList();
}
