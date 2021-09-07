package com.ch.crm.web.listener;

import com.ch.crm.settings.domain.DicValue;
import com.ch.crm.settings.service.DicService;
import com.ch.crm.settings.service.impl.DicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;


public class SysInitListener implements ServletContextListener {

    private DicService dicService;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("处理数据字典开始");
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        dicService=context.getBean(DicService.class);
        ServletContext application = servletContextEvent.getServletContext();
        Map<String , List<DicValue>> map=dicService.getAll();
        Set<String> set = map.keySet();
        for (String key:set){
            application.setAttribute(key,map.get(key));
        }
        System.out.println("处理数据字典结束");

        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> e = rb.getKeys();
        Map<String, String> pMap = new HashMap<>();
        while (e.hasMoreElements()){
            String key = e.nextElement();

            String value = rb.getString(key);

            pMap.put(key,value);
        }
        application.setAttribute("pMap",pMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }




}
