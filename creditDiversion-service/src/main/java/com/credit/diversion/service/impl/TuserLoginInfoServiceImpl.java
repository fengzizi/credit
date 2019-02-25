package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TuserLoginInfoMapper;
import com.credit.diversion.model.TuserLoginInfo;
import com.credit.diversion.service.TuserLoginInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TuserLoginInfoServiceImpl
 * @用户登录信息ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TuserLoginInfoServiceImpl extends ServiceImpl<TuserLoginInfoMapper, TuserLoginInfo> implements TuserLoginInfoService {

    @Autowired
    private TuserLoginInfoMapper tuserLoginInfoMapper;
}
