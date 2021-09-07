package com.ch.crm.workbench.service.aspect;

import com.ch.crm.utils.DateTimeUtil;
import com.ch.crm.workbench.domain.ActivityDeleteLogs;
import com.ch.crm.workbench.service.ActivityService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
@Aspect
public class MyAspect {
    @Resource
    private ActivityService activityService;

    @Around(value = "execution(public boolean com.ch.crm.workbench.service.impl.ActivityServiceImpl.delete(..))")
    public boolean activityDeleteLogs(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("日志查询");
        Object[] args = pjp.getArgs();
        String deleteUser=(String)args[0];
        String[] strings=(String[])args[1];
        List<ActivityDeleteLogs> logs=activityService.getActivityByIdArgs(strings);
        for (ActivityDeleteLogs adl:logs){
            adl.setDeleteDate(DateTimeUtil.getSysTime());
            adl.setDeleteUser(deleteUser);
            System.out.println(adl.getName()+adl.getOwner()+adl.getCost()+adl.getStartDate()+adl.getEndDate()+adl.getDeleteDate()+adl.getDeleteUser());
        }
        boolean flag=(boolean)pjp.proceed();
        //String path=Thread.currentThread().getContextClassLoader().getResource("ActivityLogs.txt").getPath();



        if (flag){
            FileWriter out=null;
            try {
                out = new FileWriter("D:\\CRM-ssm\\crm\\src\\main\\java\\com\\ch\\crm\\workbench\\service\\aspect\\ActivityLogs.txt",true);
                for (ActivityDeleteLogs adl:logs){
                    StringBuilder builder = new StringBuilder("");
                    builder.append(adl.getName()+"\t\t");
                    builder.append(adl.getOwner()+"\t\t");
                    builder.append(adl.getCost()+"\t\t");
                    builder.append(adl.getStartDate()+"\t\t");
                    builder.append(adl.getEndDate()+"\t\t");
                    builder.append(adl.getDeleteDate()+"\t\t");
                    builder.append(adl.getDeleteUser()+"\n");
                    out.write(String.valueOf(builder));
                    out.flush();
                }

            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if (out!=null){
                    try {
                        out.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }

        }
        return flag;
    }

}
