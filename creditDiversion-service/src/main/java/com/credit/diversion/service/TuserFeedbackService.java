package com.credit.diversion.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import com.credit.diversion.model.TuserAuthInfo;
import com.credit.diversion.model.TuserFeedback;
import com.credit.diversion.model.TuserPersonInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @TuserFeedbackService
 * @用户反馈Service
 * @version : Ver 1.0
 */
public interface TuserFeedbackService extends IService<TuserFeedback>{

    /**
     * 关联用户个人信息查询
     * @param page
     * @param feedback
     * @param personInfo
     * @return
     */
    Page<TuserFeedback> selectListOfUser(Page<TuserFeedback> page, TuserFeedback feedback, TuserPersonInfo personInfo);

    /**
     * 关联用户认证信息查询
     * @param page
     * @param feedback
     * @param authInfo
     * @return
     */
    Page<TuserFeedback> selectListOfAuth(Page<TuserFeedback> page,TuserFeedback feedback,TuserAuthInfo authInfo);
}
