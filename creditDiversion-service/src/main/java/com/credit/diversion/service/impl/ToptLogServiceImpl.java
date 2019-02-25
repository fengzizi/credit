package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.ToptLogMapper;
import com.credit.diversion.model.ToptLog;
import com.credit.diversion.service.ToptLogService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ToptLogServiceImpl
 * @ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class ToptLogServiceImpl extends ServiceImpl<ToptLogMapper, ToptLog> implements ToptLogService {

    @Autowired
    private ToptLogMapper toptLogMapper;
}
