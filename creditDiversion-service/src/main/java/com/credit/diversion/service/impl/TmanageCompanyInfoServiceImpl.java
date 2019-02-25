package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TmanageCompanyInfoMapper;
import com.credit.diversion.model.TmanageCompanyInfo;
import com.credit.diversion.service.TmanageCompanyInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TmanageCompanyInfoServiceImpl
 * @公司列ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TmanageCompanyInfoServiceImpl extends ServiceImpl<TmanageCompanyInfoMapper, TmanageCompanyInfo> implements TmanageCompanyInfoService {

    @Autowired
    private TmanageCompanyInfoMapper tmanageCompanyInfoMapper;
}
