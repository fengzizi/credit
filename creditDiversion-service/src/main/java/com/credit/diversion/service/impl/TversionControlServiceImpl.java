package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TversionControlMapper;
import com.credit.diversion.model.TversionControl;
import com.credit.diversion.service.TversionControlService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TversionControlServiceImpl
 * @审核开关ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TversionControlServiceImpl extends ServiceImpl<TversionControlMapper, TversionControl> implements TversionControlService {

    @Autowired
    private TversionControlMapper tversionControlMapper;
}
