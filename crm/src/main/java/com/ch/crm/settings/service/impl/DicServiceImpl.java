package com.ch.crm.settings.service.impl;

import com.ch.crm.settings.dao.DicTypeDao;
import com.ch.crm.settings.dao.DicValueDao;
import com.ch.crm.settings.domain.DicType;
import com.ch.crm.settings.domain.DicValue;
import com.ch.crm.settings.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {
    @Resource
    private DicTypeDao dicTypeDao;
    @Resource
    private DicValueDao dicValueDao;

    @Override
    public Map<String, List<DicValue>> getAll() {
        HashMap<String, List<DicValue>> map = new HashMap<>();
        List<DicType> dicTypeList=dicTypeDao.getTypeList();
        for (DicType dicType:dicTypeList){
            List<DicValue> dicValueList=dicValueDao.getValueListByCode(dicType.getCode());
            map.put(dicType.getCode(),dicValueList);
        }
        return map;
    }
}
