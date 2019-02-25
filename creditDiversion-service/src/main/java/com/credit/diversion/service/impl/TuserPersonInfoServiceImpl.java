package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TuserPersonInfoMapper;
import com.credit.diversion.model.TuserPersonInfo;
import com.credit.diversion.service.TuserPersonInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TuserPersonInfoServiceImpl
 * @用户个人信息ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TuserPersonInfoServiceImpl extends ServiceImpl<TuserPersonInfoMapper, TuserPersonInfo> implements TuserPersonInfoService {

    @Autowired
    private TuserPersonInfoMapper tuserPersonInfoMapper;
}
