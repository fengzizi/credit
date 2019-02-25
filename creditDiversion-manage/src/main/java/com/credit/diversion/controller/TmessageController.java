package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TuserLoginInfo;
import com.credit.diversion.model.TversionControl;
import com.credit.diversion.service.TuserLoginInfoService;
import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.model.Tmessage;
import com.credit.diversion.service.TmessageService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @TmessageController
 * @消息Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tmessage")
@Transactional
@Slf4j
public class TmessageController {

    @Autowired
    private TmessageService tmessageService;

    @Autowired
    private TuserLoginInfoService tuserLoginInfoService;

    /**
     * @添加消息
     * @param tmessage
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTmessage(Tmessage tmessage,String userLoginName){
        log.debug("添加消息");
        tmessage.setCreateTime(new Date());
        if(StringUtils.isEmpty(userLoginName)){
            return JsonResp.toFail("请选择接收人！");
        }
        if(userLoginName.equals("all")){
            tmessage.setUserId("0");
        }else{
            TuserLoginInfo tuserLoginInfo=tuserLoginInfoService.selectOne(new EntityWrapper<TuserLoginInfo>()
                    .eq("login_name",userLoginName)
                    .orderBy("id",false)
                    .last("limit 1")
            );
            if(tuserLoginInfo==null){
                return JsonResp.toFail("该接收人不存在！");
            }
            tmessage.setUserId(tuserLoginInfo.getId());
        }
        tmessageService.insert(tmessage);
        return JsonResp.ok(tmessage);
    }

    /**
     * @修改消息
     * @param tmessage
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTmessage(Tmessage tmessage,String userLoginName){
        log.debug("修改消息");
        if(StringUtils.isEmpty(userLoginName)){
            return JsonResp.toFail("请选择接收人！");
        }
        if(userLoginName.equals("all")){
            tmessage.setUserId("0");
        }else{
            TuserLoginInfo tuserLoginInfo=tuserLoginInfoService.selectOne(new EntityWrapper<TuserLoginInfo>()
                    .eq("login_name",userLoginName)
                    .orderBy("id",false)
                    .last("limit 1")
            );
            if(tuserLoginInfo==null){
                return JsonResp.toFail("该接收人不存在！");
            }
            tmessage.setUserId(tuserLoginInfo.getId());
        }
        tmessageService.updateById(tmessage);
        return JsonResp.ok(tmessage);
    }
    /**
     * @根据id查找消息
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTmessage(Integer id){
        log.debug("查找消息");
        Tmessage tmessage = tmessageService.selectById(id);
        if(tmessage!=null){
            if(tmessage.getUserId()!=null&&!tmessage.getUserId().equals("0")){
                TuserLoginInfo tuserLoginInfo=tuserLoginInfoService.selectById(tmessage.getUserId());
                if(tuserLoginInfo!=null){
                    tmessage.setUserLoginName(tuserLoginInfo.getLoginName());
                }
            }else{
                tmessage.setUserLoginName("all");
            }
        }
        return JsonResp.ok(tmessage);
    }

    /**
     * 查询列表
     * @param limit
     * @param page
     * @param tmessage
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, Tmessage tmessage) {
        Page<Tmessage> tbannerPage = tmessageService.selectListOfLoginName(
                new Page<Tmessage>(page, limit), tmessage);
        return JsonResp.dataPageForAuto(tbannerPage);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/deleteById")
    public JsonResp deleteById(Integer id){
        log.debug("删除");
        tmessageService.deleteById(id);
        return JsonResp.ok("删除成功");
    }

}
