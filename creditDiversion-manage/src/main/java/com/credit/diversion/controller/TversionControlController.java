package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TmanageDepartmentInfo;
import com.credit.diversion.model.TmanageLoginAccount;
import com.credit.diversion.service.TmanageLoginAccountService;
import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.model.TversionControl;
import com.credit.diversion.service.TversionControlService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @TversionControlController
 * @审核开关Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tversionControl")
@Transactional
@Slf4j
public class TversionControlController {

    @Autowired
    private TversionControlService tversionControlService;

    @Autowired
    private TmanageLoginAccountService tmanageLoginAccountService;

    /**
     * @添加审核开关
     * @param tversionControl
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTversionControl(TversionControl tversionControl){
        log.debug("添加审核开关");
        TmanageLoginAccount tadmin = tmanageLoginAccountService.selectLoginIng();
        tversionControl.setCreatedAt(new Date());
        tversionControl.setCreatedBy(tadmin.getUserName());
        tversionControlService.insert(tversionControl);
        return JsonResp.ok(tversionControl);
    }

    /**
     * @修改审核开关
     * @param tversionControl
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTversionControl(TversionControl tversionControl){
        log.debug("修改审核开关");
        TmanageLoginAccount tadmin = tmanageLoginAccountService.selectLoginIng();
        tversionControl.setUpdatedAt(new Date());
        tversionControl.setUpdatedBy(tadmin.getUserName());
        tversionControlService.updateById(tversionControl);
        return JsonResp.ok(tversionControl);
    }
    /**
     * @根据id查找审核开关
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTversionControl(Integer id){
        log.debug("查找审核开关");
        TversionControl tversionControl = tversionControlService.selectById(id);
        return JsonResp.ok(tversionControl);
    }

    /**
     * 查询列表
     * @param limit
     * @param page
     * @param tversionControl
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, TversionControl tversionControl) {
        Wrapper<TversionControl> wrapper = new EntityWrapper<TversionControl>();
        if (!StringUtils.isEmpty(tversionControl.getOsVersion())) {
            wrapper.like("os_version", tversionControl.getOsVersion());
        }
        if (!StringUtils.isEmpty(tversionControl.getAuditSwitch())) {
            wrapper.eq("audit_switch", tversionControl.getAuditSwitch());
        }
        if (!StringUtils.isEmpty(tversionControl.getOsType())) {
            wrapper.eq("os_type", tversionControl.getOsType());
        }
        Page<TversionControl> tbannerPage = tversionControlService.selectPage(
                new Page<TversionControl>(page, limit), wrapper.orderBy("id", false));
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
        tversionControlService.deleteById(id);
        return JsonResp.ok("删除成功");
    }


}
