package com.credit.diversion.service;

import com.baomidou.mybatisplus.service.IService;
import com.credit.diversion.model.TmanageLoginAccount;

/**
 * @TmanageLoginAccountService
 * @后台登录帐号Service
 * @version : Ver 1.0
 */
public interface TmanageLoginAccountService extends IService<TmanageLoginAccount>{

    /**
     * 获取当前登录用户
     *
     * @return
     */
    TmanageLoginAccount selectLoginIng();
}
