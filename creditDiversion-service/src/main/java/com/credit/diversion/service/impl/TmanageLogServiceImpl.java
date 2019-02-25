package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TmanageLogMapper;
import com.credit.diversion.model.TmanageLog;
import com.credit.diversion.service.TmanageLogService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TmanageLogServiceImpl
 * @后台日志ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TmanageLogServiceImpl extends ServiceImpl<TmanageLogMapper, TmanageLog> implements TmanageLogService {

    @Autowired
    private TmanageLogMapper tmanageLogMapper;
}
