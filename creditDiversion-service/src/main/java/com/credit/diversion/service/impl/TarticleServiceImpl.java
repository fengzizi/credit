package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TarticleMapper;
import com.credit.diversion.model.Tarticle;
import com.credit.diversion.service.TarticleService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TarticleServiceImpl
 * @文章信息ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TarticleServiceImpl extends ServiceImpl<TarticleMapper, Tarticle> implements TarticleService {

    @Autowired
    private TarticleMapper tarticleMapper;
}
