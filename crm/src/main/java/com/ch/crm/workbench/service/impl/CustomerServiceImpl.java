package com.ch.crm.workbench.service.impl;

import com.ch.crm.workbench.dao.CustomerDao;
import com.ch.crm.workbench.domain.Customer;
import com.ch.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerDao customerDao;


    @Override
    public List<String> getCustomerName(String name) {
        List<String> customerName = customerDao.getCustomerName(name);
        return customerName;
    }
}
