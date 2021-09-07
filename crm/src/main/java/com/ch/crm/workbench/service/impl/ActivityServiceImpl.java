package com.ch.crm.workbench.service.impl;

import com.ch.crm.vo.PaginationVO;
import com.ch.crm.workbench.dao.ActivityDao;
import com.ch.crm.workbench.dao.ActivityRemarkDao;
import com.ch.crm.workbench.domain.Activity;
import com.ch.crm.workbench.domain.ActivityDeleteLogs;
import com.ch.crm.workbench.domain.ActivityRemark;
import com.ch.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityDao activityDao;
    @Resource
    private ActivityRemarkDao activityRemarkDao;


    public boolean save(Activity activity){
        boolean flag=true;
        int count=activityDao.save(activity);
        if (count!=1){

            flag=false;
        }
        return flag;
    }


    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {


        int total=activityDao.getTotalByCondition(map);
        List<Activity> dataList=activityDao.getActivityListByCondition(map);
        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public boolean delete(String deleteBy,String[] id) {
        boolean flag=true;

        //查询出需要删除的备注的数量
        int count1=activityRemarkDao.getCountByAids(id);
        //删除备注，返回受到影响的条数（实际删除的数量）
        int count2=activityRemarkDao.deleteByAids(id);
        if (count1!=count2){
            flag=false;
        }
        //删除市场活动
       int count3= activityDao.delete(id);
        if (count3!= id.length){
            flag=false;
        }
        return flag;
    }

    @Override
    public Activity getActivityById(String id) {
        Activity activity=activityDao.getActivityById(id);
        return activity;
    }

    @Override
    public boolean updateActivity(Activity activity) {
        boolean flag=true;
        int count=activityDao.updateActivity(activity);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {

        Activity activity=activityDao.detail(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListAid(String activityId) {
        List<ActivityRemark> arList=activityRemarkDao.getRemarkListByAid(activityId);
        return arList;
    }

    @Override
    public boolean deleteRemarkById(String id) {
        boolean flag=true;
        int count=activityRemarkDao.deleteRemarkById(id);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {

        boolean flag=true;
        int count=activityRemarkDao.saveRemark(activityRemark);
        if (count!=1){
            flag=false;
        }

        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        boolean flag=true;
        int count=activityRemarkDao.updateRemark(activityRemark);
        if (count!=1){
            flag=false;
        }

        return flag;
    }

    @Override
    public List<Activity> getActivityByClueId(String clueId) {

        List<Activity> activityList=activityDao.getActivityByClueId(clueId);
        return activityList;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {

        List<Activity> activityList=activityDao.getActivityListByNameAndNotByClueId(map);


        return activityList;
    }

    @Override
    public List<Activity> getActivityListByName(String aname) {
        List<Activity> activityList=activityDao.getActivityListByName(aname);
        return activityList;
    }

    @Override
    public List<ActivityDeleteLogs> getActivityByIdArgs(String[] strings) {

        List<ActivityDeleteLogs> logs=activityDao.getActivityByIdArgs(strings);

        return logs;
    }

    @Override
    public List<String> deleteLogs() {
        List<String> stringList = new ArrayList<>();
        String s=null;
        BufferedReader reader=null;
        try {
            reader = new BufferedReader(new FileReader("D:\\CRM-ssm\\crm\\src\\main\\java\\com\\ch\\crm\\workbench\\service\\aspect\\ActivityLogs.txt"));
            while ((s=reader.readLine())!=null){

                stringList.add(s);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringList;
    }
}
