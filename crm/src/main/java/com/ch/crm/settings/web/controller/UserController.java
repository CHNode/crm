package com.ch.crm.settings.web.controller;

import com.ch.crm.exception.UserLoginException;
import com.ch.crm.settings.domain.User;
import com.ch.crm.settings.service.UserService;
import com.ch.crm.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService service;

    @RequestMapping("/loginUser.do")
    @ResponseBody
    public Map<String, Object> userLogin(HttpServletRequest request, String loginAct, String loginPwd) throws UserLoginException {
        String ip=request.getRemoteAddr();
        System.out.println(ip);
        String loginPwd1 = MD5Util.getMD5(loginPwd);
        HashMap<String , Object> map = new HashMap<>();

            User user = service.userLogin(loginAct, loginPwd1, ip);
            request.getSession().setAttribute("user",user);
            map.put("success",true);
        return map;



    }
}
