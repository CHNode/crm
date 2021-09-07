package com.ch.crm.settings.dao;

import com.ch.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getValueListByCode(String code);
}
