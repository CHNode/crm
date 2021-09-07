package com.ch.crm.workbench.service;

import com.ch.crm.vo.PaginationVO;
import com.ch.crm.workbench.domain.Activity;
import com.ch.crm.workbench.domain.ActivityDeleteLogs;
import com.ch.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
     boolean save(Activity activity);
    PaginationVO<Activity> pageList(Map<String,Object> map);

    boolean delete(String deleteBy,String[] id);

    Activity getActivityById(String id);

    boolean updateActivity(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListAid(String activityId);

    boolean deleteRemarkById(String id);

    boolean saveRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);

    List<Activity> getActivityByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<Activity> getActivityListByName(String aname);

    List<ActivityDeleteLogs> getActivityByIdArgs(String[] strings);

    List<String> deleteLogs();
}

