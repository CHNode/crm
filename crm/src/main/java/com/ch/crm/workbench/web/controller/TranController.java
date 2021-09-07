package com.ch.crm.workbench.web.controller;

import com.ch.crm.settings.domain.User;
import com.ch.crm.settings.service.UserService;
import com.ch.crm.utils.DateTimeUtil;
import com.ch.crm.utils.UUIDUtil;
import com.ch.crm.workbench.domain.Customer;
import com.ch.crm.workbench.domain.Tran;
import com.ch.crm.workbench.domain.TranHistory;
import com.ch.crm.workbench.service.CustomerService;
import com.ch.crm.workbench.service.TranService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/workbench/transaction")
@Controller
public class TranController {
        @Resource
        private UserService userService;
        @Resource
        private CustomerService customerService;
        @Resource
        private TranService tranService;

        @RequestMapping("/add.do")
        @ResponseBody
        public ModelAndView add(){
            ModelAndView mv = new ModelAndView();
            List<User> userList = userService.getUserList();
            mv.addObject("userList",userList);
            mv.setViewName("forward:/workbench/transaction/save.jsp");

            return mv;
        }
        @RequestMapping("/getCustomerName.do")
        @ResponseBody
        public List<String> getCustomerName (String name){
            List<String> customerList=customerService.getCustomerName(name);
            return customerList;
        }


        @RequestMapping("/save.do")
        public ModelAndView save(HttpServletRequest request,Tran tran, String customerName){
            String id= UUIDUtil.getUUID();
            String createBy=((User)request.getSession().getAttribute("user")).getName();
            String createTime= DateTimeUtil.getSysTime();
            ModelAndView mv = new ModelAndView();

            tran.setId(id);
            tran.setCreateBy(createBy);
            tran.setCreateTime(createTime);
            boolean flag=tranService.save(tran,customerName);
            if (flag){
                mv.setViewName("redirect:/workbench/transaction/index.jsp");
            }
            return mv;
        }



    @RequestMapping("/detail.do")
    public ModelAndView detail(HttpServletRequest request,String id){
        ModelAndView mv = new ModelAndView();
        Tran tran=tranService.detail(id);

        String stage = tran.getStage();
        Map<String,String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        String possibility=pMap.get(stage);
        tran.setPossibility(possibility);
        mv.addObject("tran",tran);
        mv.setViewName("forward:/workbench/transaction/detail.jsp");
        return mv;
    }
    @RequestMapping("/getHistoryListByTranId.do")
    @ResponseBody
    public List<TranHistory> getHistoryListByTranId(HttpServletRequest request,String tranId){
        Map<String,String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");
        List<TranHistory> tranHistoryList =tranService.getHistoryListByTranId(tranId);
        for (TranHistory tranHistory:tranHistoryList){
            String stage = tranHistory.getStage();
            String possibility=pMap.get(stage);
            tranHistory.setPossibility(possibility);
        }

        return  tranHistoryList;
    }

    @RequestMapping("changeStage.do")
    @ResponseBody
    public Map<String ,Object> changeStage(HttpServletRequest request,Tran tran){
        Map<String, Object> map = new HashMap<>();
        String editBy=((User)request.getSession().getAttribute("user")).getName();
        String editTime= DateTimeUtil.getSysTime();
        Map<String,String> pMap = (Map<String, String>) request.getServletContext().getAttribute("pMap");

        tran.setPossibility(pMap.get(tran.getStage()));
        tran.setEditTime(editTime);
        tran.setEditBy(editBy);

        boolean flag=tranService.changeStage(tran);
        map.put("success",flag);
        map.put("t",tran);
        return map;
    }
    @RequestMapping("/getCharts.do")
    @ResponseBody
    public Map<String,Object> getCharts(){
        Map<String,Object> map=tranService.getCharts();
        return map;
    }
}
