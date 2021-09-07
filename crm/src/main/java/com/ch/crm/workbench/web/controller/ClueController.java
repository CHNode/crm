package com.ch.crm.workbench.web.controller;

import com.ch.crm.settings.domain.User;
import com.ch.crm.settings.service.UserService;
import com.ch.crm.utils.DateTimeUtil;
import com.ch.crm.utils.UUIDUtil;
import com.ch.crm.workbench.domain.Activity;
import com.ch.crm.workbench.domain.Clue;
import com.ch.crm.workbench.domain.Tran;
import com.ch.crm.workbench.service.ActivityService;
import com.ch.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
        @Resource
        private UserService userService;
        @Resource
        private ClueService clueService;
        @Resource
        private ActivityService activityService;

        @RequestMapping("/getUserList.do")
        @ResponseBody
        public List<User> getUserList(){
            List<User> userList = userService.getUserList();
            return userList;
        }

    @RequestMapping("/save.do")
    @ResponseBody
    public Map<String,Object> save(HttpServletRequest request, Clue clue){
        String  id= UUIDUtil.getUUID();
        String  createTime= DateTimeUtil.getSysTime();
        String  createBy=((User)request.getSession().getAttribute("user")).getName();

        clue.setId(id);
        clue.setCreateTime(createTime);
        clue.setCreateBy(createBy);

        boolean flag=clueService.save(clue);
        Map<String, Object> map = new HashMap<>();
        map.put("success",flag);
        return map;
    }


    @RequestMapping("/detail.do")
    @ResponseBody
    public ModelAndView detail(String id){
        ModelAndView modelAndView = new ModelAndView();
        Clue clue=clueService.detail(id);
        modelAndView.addObject("clue",clue);
        modelAndView.setViewName("forward:/workbench/clue/detail.jsp");
        return modelAndView;
    }

    @RequestMapping("/getActivityListByClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByClueId(String clueId){
            List<Activity> activityList=activityService.getActivityByClueId(clueId);
            return activityList;
    }

    @RequestMapping("/unbund.do")
    @ResponseBody
    public Map<String,Object> unbund(String id){
        Map<String , Object> map = new HashMap<>();

        boolean flag=clueService.unbund(id);
        map.put("success",flag);
        return map;
    }
    @RequestMapping("/getActivityListByNameAndNotByClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByNameAndNotByClueId (String aname,String clueId){
        Map<String, String> map = new HashMap<>();
        map.put("aname",aname);
        map.put("clueId",clueId);
        List<Activity> activityList=activityService.getActivityListByNameAndNotByClueId(map);
        return activityList;
    }

    @RequestMapping("/bund.do")
    @ResponseBody
    public Map<String,Object> bund(String cid,String[] aid){
        Map<String, Object> map = new HashMap<>();

        boolean flag=clueService.bund(cid,aid);
        map.put("success",flag);
        return map;
    }

    @RequestMapping("/getActivityListByName.do")
    @ResponseBody
    public List<Activity> getActivityListByName(String aname){
        List<Activity> activityList=activityService.getActivityListByName(aname);
        return activityList;
    }
    @RequestMapping("/convert.do")
    public ModelAndView convert( HttpServletRequest request,String clueId, String flag){
        ModelAndView mv = new ModelAndView();
        String  createBy=((User)request.getSession().getAttribute("user")).getName();
        Tran tran=null;
        if ("a".equals(flag)){
                tran=new Tran();
                String  id= UUIDUtil.getUUID();
                String  createTime= DateTimeUtil.getSysTime();
                String money = request.getParameter("money");
                String name = request.getParameter("name");
                String expectedDate = request.getParameter("expectedDate");
                String stage = request.getParameter("stage");
                String activityId = request.getParameter("activityId");

                tran.setId(id);
                tran.setCreateTime(createTime);
                tran.setCreateBy(createBy);
                tran.setMoney(money);
                tran.setName(name);
                tran.setExpectedDate(expectedDate);
                tran.setStage(stage);
                tran.setActivityId(activityId);

            }
            boolean flag1=clueService.convert(clueId,tran,createBy);
            if (flag1){
                mv.setViewName("redirect:/workbench/clue/index.jsp");
            }
            return mv;
    }

}
