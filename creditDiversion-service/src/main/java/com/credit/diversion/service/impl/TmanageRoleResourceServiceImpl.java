package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TmanageRoleResourceMapper;
import com.credit.diversion.model.TmanageRoleResource;
import com.credit.diversion.service.TmanageRoleResourceService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TmanageRoleResourceServiceImpl
 * @角色权限ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TmanageRoleResourceServiceImpl extends ServiceImpl<TmanageRoleResourceMapper, TmanageRoleResource> implements TmanageRoleResourceService {

    @Autowired
    private TmanageRoleResourceMapper tmanageRoleResourceMapper;
}
