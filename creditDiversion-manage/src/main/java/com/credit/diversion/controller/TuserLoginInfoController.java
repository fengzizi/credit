package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TcreditAccessRecord;
import com.credit.diversion.model.TuserAuthInfo;
import com.credit.diversion.model.TuserLoginInfo;
import com.credit.diversion.service.TcreditAccessRecordService;
import com.credit.diversion.service.TuserAuthInfoService;
import com.credit.diversion.service.TuserLoginInfoService;
import com.credit.diversion.util.DateUtils;
import com.credit.diversion.util.commons.JsonResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @version : Ver 1.0
 * @TuserLoginInfoController
 * @用户登录信息Controller
 */
@RestController
@RequestMapping(value = "/manage/tuserLoginInfo")
@Transactional
@Slf4j
public class TuserLoginInfoController {

    @Autowired
    private TuserLoginInfoService tuserLoginInfoService;

    @Autowired
    private TuserAuthInfoService tuserAuthInfoService;

    @Autowired
    private TcreditAccessRecordService tcreditAccessRecordService;

    /**
     * @param tuserLoginInfo
     * @return 返回值JsonResp
     * @添加用户登录信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResp addTuserLoginInfo(TuserLoginInfo tuserLoginInfo) {
        log.debug("添加用户登录信息");
        tuserLoginInfo.setCreated(new Date());
        tuserLoginInfoService.insert(tuserLoginInfo);
        return JsonResp.ok(tuserLoginInfo);
    }

    /**
     * @param tuserLoginInfo
     * @return 返回值JsonResp
     * @修改用户登录信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResp updateTuserLoginInfo(TuserLoginInfo tuserLoginInfo) {
        log.debug("修改用户登录信息");
        tuserLoginInfo.setUpdated(new Date());
        tuserLoginInfoService.updateById(tuserLoginInfo);
        return JsonResp.ok(tuserLoginInfo);
    }

    /**
     * @param id
     * @return 返回值JsonResp
     * @根据id查找用户登录信息
     */
    @RequestMapping(value = "/selectOne", method = RequestMethod.GET)
    public JsonResp selectTuserLoginInfo(String id) {
        log.debug("查找用户登录信息");
        TuserLoginInfo tuserLoginInfo = tuserLoginInfoService.selectById(id);
        return JsonResp.ok(tuserLoginInfo);
    }

    /**
     * 查询列表
     *
     * @param limit
     * @param page
     * @param loginInfo
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, TuserLoginInfo loginInfo) {
        Wrapper<TuserLoginInfo> wrapper = new EntityWrapper<TuserLoginInfo>();
        if (!StringUtils.isEmpty(loginInfo.getLoginStatus())) {
            wrapper.eq("login_status", loginInfo.getLoginStatus());
        }
        if (!StringUtils.isEmpty(loginInfo.getLoginType())) {
            wrapper.eq("login_type", loginInfo.getLoginType());
        }
        if (!StringUtils.isEmpty(loginInfo.getLoginName())) {
            wrapper.like("login_name", loginInfo.getLoginName());
        }

        if (!StringUtils.isEmpty(loginInfo.getLoginSalt())) {
            wrapper.like("login_salt", loginInfo.getLoginSalt());
        }
        Page<TuserLoginInfo> tbannerPage = tuserLoginInfoService.selectPage(
                new Page<TuserLoginInfo>(page, limit), wrapper.orderBy("id", false));
        return JsonResp.dataPageForAuto(tbannerPage);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteById")
    public JsonResp deleteById(String id) {
        log.debug("删除");
        tuserLoginInfoService.deleteById(id);
        return JsonResp.ok("删除成功");
    }

    /**
     * 选择框数据查询
     *
     * @param loginInfo
     * @return
     */
    @RequestMapping(value = "/selectListName")
    public JsonResp selectListName(TuserLoginInfo loginInfo) {
        Wrapper<TuserLoginInfo> wrapper = new EntityWrapper<TuserLoginInfo>();
        if (!StringUtils.isEmpty(loginInfo.getLoginStatus())) {
            wrapper.eq("login_status", loginInfo.getLoginStatus());
        }
        if (!StringUtils.isEmpty(loginInfo.getLoginType())) {
            wrapper.eq("login_type", loginInfo.getLoginType());
        }
        if (!StringUtils.isEmpty(loginInfo.getLoginName())) {
            wrapper.like("login_name", loginInfo.getLoginName());
        }

        if (!StringUtils.isEmpty(loginInfo.getLoginSalt())) {
            wrapper.like("login_salt", loginInfo.getLoginSalt());
        }
        Page<TuserLoginInfo> tbannerPage = tuserLoginInfoService.selectPage(
                new Page<TuserLoginInfo>(0, 20), wrapper.orderBy("id", false));
        return JsonResp.dataPageForAuto(tbannerPage);
    }

    /**
     * 用户数据统计分析
     *
     * @return
     */
    @RequestMapping(value = "/selectSum")
    public JsonResp selectSum() {
        Wrapper<TuserLoginInfo> wrapper = new EntityWrapper<TuserLoginInfo>();
        wrapper.setSqlSelect("count(1) as countAll," +
                "sum(if(to_days(created) = to_days(now()),1,0)) as countNowRegister," +
                "sum(if(to_days(last_login_time) = to_days(now()),1,0)) as countNowLogin");
        Map<String, Object> map = tuserLoginInfoService.selectMap(wrapper);

        Wrapper<TuserAuthInfo> authInfoWrapper = new EntityWrapper<TuserAuthInfo>();
        authInfoWrapper.setSqlSelect("count(1) as countAuthAll," +
                "sum(if(to_days(created_at) = to_days(now()),1,0)) as countNowAuth");
        map.putAll(tuserAuthInfoService.selectMap(authInfoWrapper));//认证数查询


        Wrapper<TcreditAccessRecord> creditWrapper = new EntityWrapper<TcreditAccessRecord>();
        creditWrapper.setSqlSelect("count(distinct user_id) as creditCountAll," +
                "sum(if(is_register = 1,1,0)) as creditCountReAll");
        creditWrapper.ge("create_time",DateUtils.getDateBeforeZero(new Date(),1));//今日
        map.putAll(tcreditAccessRecordService.selectMap(creditWrapper));//认证数查询
        return JsonResp.ok(map);
    }

    /**
     * 分组统计
     * @return
     */
    @RequestMapping(value = "/selectSumGroup")
    public JsonResp selectSumGroup() {
        int day=15;
        Date date=new Date();
        Wrapper<TuserLoginInfo> wrapper = new EntityWrapper<TuserLoginInfo>();
        wrapper.setSqlSelect("DATE_FORMAT(created,'%m-%d') days," +
                "count(1) as countNowRegister");
        wrapper.ge("created",DateUtils.getDateBeforeZero(date,day));
        wrapper.groupBy("days");
        List<Map<String, Object>> registers = tuserLoginInfoService.selectMaps(wrapper);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("registers",registers);//注册分组

        Wrapper<TuserAuthInfo> authInfoWrapper = new EntityWrapper<TuserAuthInfo>();
        authInfoWrapper.setSqlSelect("DATE_FORMAT(created_at,'%m-%d') days," +
                "count(1) as countNowAuth");
        authInfoWrapper.ge("created_at",DateUtils.getDateBeforeZero(date,day));
        authInfoWrapper.groupBy("days");
        List<Map<String, Object>> auths = tuserAuthInfoService.selectMaps(authInfoWrapper);
        map.put("auths",auths);//认证分组
        map.put("credits",tcreditAccessRecordService.selectSumGroup(null,date,day));//信贷UV+注册分组
        return JsonResp.ok(map);
    }
}
