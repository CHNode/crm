package com.ch.crm.workbench.service;

import com.ch.crm.workbench.domain.Clue;
import com.ch.crm.workbench.domain.Tran;

public interface ClueService {
    boolean save(Clue clue);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aid);

    boolean convert(String clueId, Tran tran, String createBy);
}
