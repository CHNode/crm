package com.ch.crm.workbench.dao;

import com.ch.crm.workbench.domain.Activity;
import com.ch.crm.workbench.domain.ActivityDeleteLogs;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity activity);

    int getTotalByCondition(Map<String, Object> map);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int delete(String[] id);

    Activity getActivityById(String id);

    int updateActivity(Activity activity);

    Activity detail(String id);

    List<Activity> getActivityByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<Activity> getActivityListByName(String aname);

    List<ActivityDeleteLogs> getActivityByIdArgs(String[] strings);
}
