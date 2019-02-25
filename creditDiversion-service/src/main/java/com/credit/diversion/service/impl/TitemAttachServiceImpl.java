package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TitemAttachMapper;
import com.credit.diversion.model.TitemAttach;
import com.credit.diversion.service.TitemAttachService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TitemAttachServiceImpl
 * @信贷附属ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TitemAttachServiceImpl extends ServiceImpl<TitemAttachMapper, TitemAttach> implements TitemAttachService {

    @Autowired
    private TitemAttachMapper titemAttachMapper;
}
