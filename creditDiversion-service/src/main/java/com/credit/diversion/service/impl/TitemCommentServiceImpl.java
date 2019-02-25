package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TitemCommentMapper;
import com.credit.diversion.model.TitemComment;
import com.credit.diversion.service.TitemCommentService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TitemCommentServiceImpl
 * @信贷评论统计ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TitemCommentServiceImpl extends ServiceImpl<TitemCommentMapper, TitemComment> implements TitemCommentService {

    @Autowired
    private TitemCommentMapper titemCommentMapper;
}
