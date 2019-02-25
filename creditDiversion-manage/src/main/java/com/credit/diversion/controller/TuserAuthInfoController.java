package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TuserPersonInfo;
import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.model.TuserAuthInfo;
import com.credit.diversion.service.TuserAuthInfoService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @TuserAuthInfoController
 * @用户认证信息扩展Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tuserAuthInfo")
@Transactional
@Slf4j
public class TuserAuthInfoController {

    @Autowired
    private TuserAuthInfoService tuserAuthInfoService;

    /**
     * @添加用户认证信息扩展
     * @param tuserAuthInfo
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTuserAuthInfo(TuserAuthInfo tuserAuthInfo){
        log.debug("添加用户认证信息扩展");
        tuserAuthInfoService.insert(tuserAuthInfo);
        return JsonResp.ok(tuserAuthInfo);
    }

    /**
     * @修改用户认证信息扩展
     * @param tuserAuthInfo
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTuserAuthInfo(TuserAuthInfo tuserAuthInfo){
        log.debug("修改用户认证信息扩展");
        tuserAuthInfoService.updateById(tuserAuthInfo);
        return JsonResp.ok(tuserAuthInfo);
    }
    /**
     * @根据id查找用户认证信息扩展
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTuserAuthInfo(String id){
        log.debug("查找用户认证信息扩展");
        TuserAuthInfo tuserAuthInfo = tuserAuthInfoService.selectById(id);
        return JsonResp.ok(tuserAuthInfo);
    }

    /**
     * 查询列表
     * @param limit
     * @param page
     * @param info
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, TuserAuthInfo info) {
        Wrapper<TuserAuthInfo> wrapper = new EntityWrapper<TuserAuthInfo>();
        if (!StringUtils.isEmpty(info.getUserPhone())) {
            wrapper.like("user_phone", info.getUserPhone());
        }
        if (!StringUtils.isEmpty(info.getRealName())) {
            wrapper.like("real_name", info.getRealName());
        }
        if (!StringUtils.isEmpty(info.getIdCard())) {
            wrapper.like("id_card", info.getIdCard());
        }
        Page<TuserAuthInfo> tbannerPage = tuserAuthInfoService.selectPage(
                new Page<TuserAuthInfo>(page, limit), wrapper.orderBy("uid", false));
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
        tuserAuthInfoService.deleteById(id);
        return JsonResp.ok("删除成功");
    }


}
