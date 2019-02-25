package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TcreditShopCategoryMapper;
import com.credit.diversion.model.TcreditShopCategory;
import com.credit.diversion.service.TcreditShopCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TcreditShopCategoryServiceImpl
 * @信贷类型分类ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TcreditShopCategoryServiceImpl extends ServiceImpl<TcreditShopCategoryMapper, TcreditShopCategory> implements TcreditShopCategoryService {

    @Autowired
    private TcreditShopCategoryMapper tcreditShopCategoryMapper;
}
