package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TuserFeedbackMapper;
import com.credit.diversion.model.TuserAuthInfo;
import com.credit.diversion.model.TuserFeedback;
import com.credit.diversion.model.TuserPersonInfo;
import com.credit.diversion.service.TuserFeedbackService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TuserFeedbackServiceImpl
 * @用户反馈ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TuserFeedbackServiceImpl extends ServiceImpl<TuserFeedbackMapper, TuserFeedback> implements TuserFeedbackService {

    @Autowired
    private TuserFeedbackMapper tuserFeedbackMapper;

    /**
     * 关联用户个人信息查询
     * @param page
     * @param feedback
     * @param personInfo
     * @return
     */
    public Page<TuserFeedback> selectListOfUser(Page<TuserFeedback> page, TuserFeedback feedback, TuserPersonInfo personInfo){
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题
        // page.setOptimizeCountSql(false);
        // 不查询总记录数
        // page.setSearchCount(false);
        // 注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        return page.setRecords(tuserFeedbackMapper.selectListOfUser(page, feedback,personInfo));
    }

    /**
     * 关联用户认证信息查询
     * @param page
     * @param feedback
     * @param u
     * @return
     */
    public Page<TuserFeedback> selectListOfAuth(Page<TuserFeedback> page, TuserFeedback feedback, TuserAuthInfo u){
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题
        // page.setOptimizeCountSql(false);
        // 不查询总记录数
        // page.setSearchCount(false);
        // 注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        return page.setRecords(tuserFeedbackMapper.selectListOfAuth(page, feedback,u));
    }
}
