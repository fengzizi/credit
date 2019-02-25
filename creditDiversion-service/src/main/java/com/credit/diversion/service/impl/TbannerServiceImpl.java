package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TbannerMapper;
import com.credit.diversion.model.Tbanner;
import com.credit.diversion.service.TbannerService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TbannerServiceImpl
 * @首页banner记录ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TbannerServiceImpl extends ServiceImpl<TbannerMapper, Tbanner> implements TbannerService {

    @Autowired
    private TbannerMapper tbannerMapper;
}
