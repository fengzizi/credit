package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TmanageDepartmentInfoMapper;
import com.credit.diversion.model.TmanageDepartmentInfo;
import com.credit.diversion.service.TmanageDepartmentInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TmanageDepartmentInfoServiceImpl
 * @企业部门信息ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TmanageDepartmentInfoServiceImpl extends ServiceImpl<TmanageDepartmentInfoMapper, TmanageDepartmentInfo> implements TmanageDepartmentInfoService {

    @Autowired
    private TmanageDepartmentInfoMapper tmanageDepartmentInfoMapper;
}
