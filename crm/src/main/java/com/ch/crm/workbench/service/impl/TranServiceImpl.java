package com.ch.crm.workbench.service.impl;

import com.ch.crm.utils.DateTimeUtil;
import com.ch.crm.utils.UUIDUtil;
import com.ch.crm.workbench.dao.CustomerDao;
import com.ch.crm.workbench.dao.TranDao;
import com.ch.crm.workbench.dao.TranHistoryDao;
import com.ch.crm.workbench.domain.Customer;
import com.ch.crm.workbench.domain.Tran;
import com.ch.crm.workbench.domain.TranHistory;
import com.ch.crm.workbench.service.TranService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {
    @Resource
    private TranDao  tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;
   @Resource
    private CustomerDao customerDao;
    @Override
    public boolean save(Tran tran, String customerName) {
        boolean flag=true;
        Customer customer = customerDao.getCustomerByName(customerName);
        if (customer==null){
            customer = new Customer();

            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setOwner(tran.getOwner());


            int count1=customerDao.save(customer);
            if (count1!=1){
                flag=false;
            }

        }
        tran.setCustomerId(customer.getId());

        int count2=tranDao.save(tran);
        if (count2!=1){
            flag=false;
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setCreateBy(tran.getCreateBy());
        int count3 = tranHistoryDao.save(tranHistory);
        if (count3!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Tran detail(String id) {

        Tran tran=tranDao.detail(id);
        return tran;
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String tranId) {
        List<TranHistory> tranHistoryList=tranHistoryDao.getHistoryListByTranId(tranId);

        return tranHistoryList;
    }

    @Override
    public boolean changeStage(Tran tran) {

        boolean flag=true;

        int count1=tranDao.changeStage(tran);
        if (count1!=1){
            flag=false;
        }
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setCreateBy(tran.getEditBy());
        int count2 = tranHistoryDao.save(tranHistory);
        if (count2!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {

        int total=tranDao.getTotal();
        List<Map<String,Object>> dataList=tranDao.getCharts();
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("dataList",dataList);
        return map;
    }
}
