package com.ch.crm.settings.dao;

import com.ch.crm.settings.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    User login(@Param("Act") String loginAct,@Param("Pwd") String loginPwd);
    List<User> getUserList();
}
