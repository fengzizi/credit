package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TmessageMapper;
import com.credit.diversion.model.Tmessage;
import com.credit.diversion.service.TmessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @TmessageServiceImpl
 * @消息ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TmessageServiceImpl extends ServiceImpl<TmessageMapper, Tmessage> implements TmessageService {

    @Autowired
    private TmessageMapper tmessageMapper;

    /**
     * 查询列表 关联用户登录信息登录号
     * @param page
     * @param tmessage
     * @return
     */
    public Page<Tmessage> selectListOfLoginName(Page<Tmessage> page, Tmessage tmessage){
        return page.setRecords(tmessageMapper.selectListOfLoginName(page, tmessage));
    }
}
