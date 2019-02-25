package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TmanageLoginAccount;
import com.credit.diversion.model.TversionControl;
import com.credit.diversion.service.TmanageLoginAccountService;
import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.model.TuserPersonInfo;
import com.credit.diversion.service.TuserPersonInfoService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @TuserPersonInfoController
 * @用户个人信息Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tuserPersonInfo")
@Transactional
@Slf4j
public class TuserPersonInfoController {

    @Autowired
    private TuserPersonInfoService tuserPersonInfoService;

    @Autowired
    private TmanageLoginAccountService tmanageLoginAccountService;

    /**
     * @添加用户个人信息
     * @param tuserPersonInfo
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTuserPersonInfo(TuserPersonInfo tuserPersonInfo){
        log.debug("添加用户个人信息");
        tuserPersonInfo.setCreatedAt(new Date());
        TmanageLoginAccount tadmin=tmanageLoginAccountService.selectLoginIng();
        tuserPersonInfo.setCreatedBy(tadmin.getUserName());
        tuserPersonInfoService.insert(tuserPersonInfo);
        return JsonResp.ok(tuserPersonInfo);
    }

    /**
     * @修改用户个人信息
     * @param tuserPersonInfo
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTuserPersonInfo(TuserPersonInfo tuserPersonInfo){
        log.debug("修改用户个人信息");
        TmanageLoginAccount tadmin=tmanageLoginAccountService.selectLoginIng();
        tuserPersonInfo.setUpdatedBy(tadmin.getLoginName());
        tuserPersonInfo.setUpdatedAt(new Date());
        tuserPersonInfoService.updateById(tuserPersonInfo);
        return JsonResp.ok(tuserPersonInfo);
    }
    /**
     * @根据id查找用户个人信息
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne")
    public JsonResp selectTuserPersonInfo(String id){
        log.debug("查找用户个人信息");
        TuserPersonInfo tuserPersonInfo = tuserPersonInfoService.selectById(id);
        return JsonResp.ok(tuserPersonInfo);
    }

    /**
     * 查询列表
     * @param limit
     * @param page
     * @param personInfo
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, TuserPersonInfo personInfo) {
        Wrapper<TuserPersonInfo> wrapper = new EntityWrapper<TuserPersonInfo>();
        if (!StringUtils.isEmpty(personInfo.getUserPhone())) {
            wrapper.like("user_phone", personInfo.getUserPhone());
        }
        if (!StringUtils.isEmpty(personInfo.getUserReal())) {
            wrapper.like("user_real", personInfo.getUserReal());
        }
        if (!StringUtils.isEmpty(personInfo.getUserNick())) {
            wrapper.like("user_nick", personInfo.getUserNick());
        }
        if (!StringUtils.isEmpty(personInfo.getGender())) {
            wrapper.like("gender", personInfo.getGender());
        }
        Page<TuserPersonInfo> tbannerPage = tuserPersonInfoService.selectPage(
                new Page<TuserPersonInfo>(page, limit), wrapper.orderBy("uid", false));
        return JsonResp.dataPageForAuto(tbannerPage);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/deleteById")
    public JsonResp deleteById(Long id){
        log.debug("删除");
        tuserPersonInfoService.deleteById(id);
        return JsonResp.ok("删除成功");
    }


}
