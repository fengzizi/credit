package com.credit.diversion.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.credit.diversion.model.Tmessage;

/**
 * @TmessageService
 * @消息Service
 * @version : Ver 1.0
 */
public interface TmessageService extends IService<Tmessage>{

    /**
     * 查询列表 关联用户登录信息登录号
     * @param page
     * @param tmessage
     * @return
     */
    Page<Tmessage> selectListOfLoginName(Page<Tmessage> page, Tmessage tmessage);
}
