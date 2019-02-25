package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TmanageLoginAccount;
import com.credit.diversion.model.TuserAuthInfo;
import com.credit.diversion.model.TuserPersonInfo;
import com.credit.diversion.service.TmanageLoginAccountService;
import com.credit.diversion.service.TuserAuthInfoService;
import com.credit.diversion.service.TuserPersonInfoService;
import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.model.TuserFeedback;
import com.credit.diversion.service.TuserFeedbackService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @TuserFeedbackController
 * @用户反馈Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tuserFeedback")
@Transactional
@Slf4j
public class TuserFeedbackController {

    @Autowired
    private TuserFeedbackService tuserFeedbackService;

    @Autowired
    private TmanageLoginAccountService tmanageLoginAccountService;

    @Autowired
    private TuserPersonInfoService tuserPersonInfoService;

    @Autowired
    private TuserAuthInfoService tuserAuthInfoService;

    /**
     * @添加用户反馈
     * @param tuserFeedback
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTuserFeedback(TuserFeedback tuserFeedback){
        log.debug("添加用户反馈");
        tuserFeedbackService.insert(tuserFeedback);
        return JsonResp.ok(tuserFeedback);
    }

    /**
     * @修改用户反馈
     * @param tuserFeedback
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTuserFeedback(TuserFeedback tuserFeedback){
        log.debug("修改用户反馈");
        tuserFeedback.setUpdatedAt(new Date());
        TmanageLoginAccount tadmin = tmanageLoginAccountService.selectLoginIng();
        tuserFeedback.setUpdatedBy(tadmin.getUserName());
        tuserFeedbackService.updateById(tuserFeedback);
        return JsonResp.ok(tuserFeedback);
    }
    /**
     * @根据id查找用户反馈
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne")
    public JsonResp selectTuserFeedback(String id){
        log.debug("查找用户反馈");
        TuserFeedback tuserFeedback = tuserFeedbackService.selectById(id);
        if(tuserFeedback!=null){
            //tuserFeedback.setTuserPersonInfo(tuserPersonInfoService.selectById(tuserFeedback.getUid()));
            tuserFeedback.setTuserAuthInfo(tuserAuthInfoService.selectById(tuserFeedback.getUid()));
        }
        return JsonResp.ok(tuserFeedback);
    }

    /**
     * 查询列表 关联用户个人信息查询
     * @param limit
     * @param page
     * @param personInfo
     * @return
     */
    @RequestMapping(value = "/selectListOfUser")
    public JsonResp selectList(Integer limit, Integer page, TuserFeedback feedback,TuserPersonInfo personInfo) {
        Page<TuserFeedback> tbannerPage = tuserFeedbackService.selectListOfUser(
                new Page<TuserFeedback>(page, limit),feedback,personInfo);
        return JsonResp.dataPageForAuto(tbannerPage);
    }

    /**
     * 查询列表 关联用户认证信息查询
     * @param limit
     * @param page
     * @param feedback
     * @param info
     * @return
     */
    @RequestMapping(value = "/selectListOfAuth")
    public JsonResp selectListOfAuth(Integer limit, Integer page, TuserFeedback feedback,TuserAuthInfo info) {
        Page<TuserFeedback> tbannerPage = tuserFeedbackService.selectListOfAuth(
                new Page<TuserFeedback>(page, limit),feedback,info);
        return JsonResp.dataPageForAuto(tbannerPage);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/deleteById")
    public JsonResp deleteById(String id){
        log.debug("删除");
        tuserFeedbackService.deleteById(id);
        return JsonResp.ok("删除成功");
    }

}
