package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TuserAuthInfoMapper;
import com.credit.diversion.model.TuserAuthInfo;
import com.credit.diversion.service.TuserAuthInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TuserAuthInfoServiceImpl
 * @用户认证信息扩展ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TuserAuthInfoServiceImpl extends ServiceImpl<TuserAuthInfoMapper, TuserAuthInfo> implements TuserAuthInfoService {

    @Autowired
    private TuserAuthInfoMapper tuserAuthInfoMapper;
}
