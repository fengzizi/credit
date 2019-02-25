package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TmanageRoleInfoMapper;
import com.credit.diversion.model.TmanageRoleInfo;
import com.credit.diversion.service.TmanageRoleInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TmanageRoleInfoServiceImpl
 * @角色信息ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TmanageRoleInfoServiceImpl extends ServiceImpl<TmanageRoleInfoMapper, TmanageRoleInfo> implements TmanageRoleInfoService {

    @Autowired
    private TmanageRoleInfoMapper tmanageRoleInfoMapper;
}
