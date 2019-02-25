package com.credit.diversion.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.credit.diversion.model.TuserAuthInfo;
import com.credit.diversion.model.TuserFeedback;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.credit.diversion.model.TuserPersonInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @TuserFeedbackMapper
 * @用户反馈Mapper
 * @version : Ver 1.0
 */
@Repository
public interface TuserFeedbackMapper extends BaseMapper<TuserFeedback>{

    /**
     * 关联用户个人信息查询
     * @param page
     * @param feedback
     * @param personInfo
     * @return
     */
    List<TuserFeedback> selectListOfUser(@Param("page") Pagination page,@Param("f") TuserFeedback feedback,@Param("p") TuserPersonInfo personInfo);

    /**
     * 关联用户认证信息查询
     * @param page
     * @param feedback
     * @param authInfo
     * @return
     */
    List<TuserFeedback> selectListOfAuth(@Param("page") Pagination page, @Param("f") TuserFeedback feedback, @Param("a")TuserAuthInfo authInfo);

}
