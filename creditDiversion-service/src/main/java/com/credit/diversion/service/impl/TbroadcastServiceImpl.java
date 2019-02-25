package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TbroadcastMapper;
import com.credit.diversion.model.Tbroadcast;
import com.credit.diversion.service.TbroadcastService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TbroadcastServiceImpl
 * @APP广播里的数据ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TbroadcastServiceImpl extends ServiceImpl<TbroadcastMapper, Tbroadcast> implements TbroadcastService {

    @Autowired
    private TbroadcastMapper tbroadcastMapper;
}
