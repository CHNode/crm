package com.ch.crm.workbench.web.controller;

import com.ch.crm.settings.domain.User;
import com.ch.crm.settings.service.UserService;
import com.ch.crm.utils.DateTimeUtil;
import com.ch.crm.utils.UUIDUtil;
import com.ch.crm.vo.PaginationVO;
import com.ch.crm.workbench.domain.Activity;
import com.ch.crm.workbench.domain.ActivityRemark;
import com.ch.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

    @Resource
    private UserService userService;
    @Resource
    private ActivityService activityService;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> userLogin(HttpServletRequest request)  {
        List<User> userList = userService.getUserList();
        return userList;

    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Map<String ,Object> save(HttpServletRequest request, HttpServletResponse response,Activity activity){


        /*String  id= UUIDUtil.getUUID();
       String  owner=request.getParameter("owner");
       String  name=request.getParameter("name");
       String  startDate=request.getParameter("startDate");
       String  endDate=request.getParameter("endDate");
       String  cost=request.getParameter("cost");
       String  description=request.getParameter("description");
       String  createTime= DateTimeUtil.getSysTime();
       String  createBy=((User)request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();
        activity.setId(id);
        activity.setCost(cost);
        activity.setStartDate(startDate);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setEndDate(endDate);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);*/

        String  id= UUIDUtil.getUUID();
        String  createTime= DateTimeUtil.getSysTime();
        String  createBy=((User)request.getSession().getAttribute("user")).getName();
        Map<String , Object> map = new HashMap<>();
        activity.setId(id);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        boolean flag=activityService.save(activity);

        map.put("success",flag);
        return map;

    }
    @RequestMapping("/pageList.do")
    @ResponseBody
    public PaginationVO<Activity> pageList(HttpServletRequest request, HttpServletResponse response, String name, String owner, String startDate, String endDate, String pageNo, String pageSize){
        int pageSize1=Integer.valueOf(pageSize);
        int pageNo1=Integer.valueOf(pageNo);
        int skipCount=(pageNo1-1)*pageSize1;

        HashMap<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize1);

        PaginationVO<Activity> vo=activityService.pageList(map);

        return vo;
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public Map<String ,Object> delete(HttpServletRequest request,String[] id){
        String  deleteBy=((User)request.getSession().getAttribute("user")).getName();
        boolean flag=activityService.delete(deleteBy,id);
        Map<String, Object> map = new HashMap<>();
        System.out.println("方法执行返回"+flag);
        map.put("success",flag);
        return map;
    }

    @RequestMapping("/getUserListAndActivity.do")
    @ResponseBody
    public Map<String ,Object> getUserListAndActivity (String id){

        List<User> userList = userService.getUserList();
        Activity activity=activityService.getActivityById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("uList",userList);
        map.put("a",activity);
        return map;
    }


    @RequestMapping("/updateActivity.do")
    @ResponseBody
    public Map<String,Object> updateActivity(HttpServletRequest request,Activity activity){
        boolean flag;
        Map<String, Object> map = new HashMap<>();
        String  editBy=((User)request.getSession().getAttribute("user")).getName();
        String  editTime= DateTimeUtil.getSysTime();
        activity.setEditBy(editBy);
        activity.setEditTime(editTime);
        flag=activityService.updateActivity(activity);
        map.put("success",flag);
        return map;
    }
    @RequestMapping("/detail.do")
    public ModelAndView detail(HttpServletRequest request,HttpServletResponse response,String id){
        Activity activity=activityService.detail(id);
        ModelAndView mv = new ModelAndView();

        mv.addObject("activity",activity);
        mv.setViewName("forward:/workbench/activity/detail.jsp");
        return mv;
    }

    @RequestMapping("/getRemarkListByAid.do")
    @ResponseBody
    public List<ActivityRemark> getRemarkListByAid(String activityId){
        List<ActivityRemark> arList=activityService.getRemarkListAid(activityId);
        return arList;
    }

    @RequestMapping("/deleteRemark.do")
    @ResponseBody
    public Map<String ,Object> deleteRemark(String id){
        Map<String, Object> map = new HashMap<>();

        boolean flag=activityService.deleteRemarkById(id);
        map.put("success",flag);
        return map;
    }

    @RequestMapping("/saveRemark.do")
    @ResponseBody
    public Map<String ,Object> saveRemark(HttpServletRequest request,ActivityRemark activityRemark){
        String  id= UUIDUtil.getUUID();
        String  createTime= DateTimeUtil.getSysTime();
        String  createBy=((User)request.getSession().getAttribute("user")).getName();
        String editFlag="0";
        activityRemark.setId(id);
        activityRemark.setCreateTime(createTime);
        activityRemark.setCreateBy(createBy);
        activityRemark.setEditFlag(editFlag);
        boolean flag=activityService.saveRemark(activityRemark);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success",flag);
        map.put("ar",activityRemark);
        return map;
    }


    @RequestMapping("/updateRemark.do")
    @ResponseBody
    public Map<String ,Object> updateRemark(HttpServletRequest request,ActivityRemark activityRemark){
        String  editTime= DateTimeUtil.getSysTime();
        String  editBy=((User)request.getSession().getAttribute("user")).getName();
        String editFlag="1";

        activityRemark.setEditTime(editTime);
        activityRemark.setEditBy(editBy);
        activityRemark.setEditFlag(editFlag);

        boolean flag=activityService.updateRemark(activityRemark);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success",flag);
        map.put("ar",activityRemark);
        return map;
    }

    @RequestMapping("/deleteLogs.do")
    @ResponseBody
    public List<String> deleteLogs(){
        List<String> stringList=activityService.deleteLogs();
        return stringList;
    }



}
