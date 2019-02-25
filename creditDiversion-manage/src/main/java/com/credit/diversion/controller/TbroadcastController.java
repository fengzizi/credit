package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.Tbroadcast;
import com.credit.diversion.model.TmanageLoginAccount;
import com.credit.diversion.service.TbroadcastService;
import com.credit.diversion.service.TmanageLoginAccountService;
import com.credit.diversion.util.commons.JsonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @version : Ver 1.0
 * @TbroadcastController
 * @APP广播里的数据Controller
 */
@RestController
@RequestMapping(value = "/manage/tbroadcast")
@Transactional
@Slf4j
public class TbroadcastController {

    @Autowired
    private TbroadcastService tbroadcastService;

    @Autowired
    private TmanageLoginAccountService tmanageLoginAccountService;

    /**
     * @param tbroadcast
     * @return 返回值JsonResp
     * @添加APP广播里的数据
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResp addTbroadcast(Tbroadcast tbroadcast) {
        log.debug("添加APP广播里的数据");
        tbroadcast.setCreatedAt(new Date());
        TmanageLoginAccount tadmin = tmanageLoginAccountService.selectLoginIng();
        tbroadcast.setCreatedBy(tadmin.getUserName());
        tbroadcastService.insert(tbroadcast);
        return JsonResp.ok(tbroadcast);
    }

    /**
     * @param tbroadcast
     * @return 返回值JsonResp
     * @修改APP广播里的数据
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResp updateTbroadcast(Tbroadcast tbroadcast) {
        log.debug("修改APP广播里的数据");
        tbroadcast.setUpdatedAt(new Date());
        TmanageLoginAccount tadmin = tmanageLoginAccountService.selectLoginIng();
        tbroadcast.setUpdatedBy(tadmin.getUserName());
        tbroadcastService.updateById(tbroadcast);
        return JsonResp.ok(tbroadcast);
    }

    /**
     * @param id
     * @return 返回值JsonResp
     * @根据id查找APP广播里的数据
     */
    @RequestMapping(value = "/selectOne", method = RequestMethod.GET)
    public JsonResp selectTbroadcast(Integer id) {
        log.debug("查找APP广播里的数据");
        Tbroadcast tbroadcast = tbroadcastService.selectById(id);
        return JsonResp.ok(tbroadcast);
    }

    /**
     * 查询列表
     *
     * @param limit
     * @param page
     * @param tbroadcast
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, Tbroadcast tbroadcast) {
        Wrapper<Tbroadcast> wrapper = new EntityWrapper<Tbroadcast>();
        if (!StringUtils.isEmpty(tbroadcast.getBizType())) {
            wrapper.eq("biz_type", tbroadcast.getBizType());
        }
        if (!StringUtils.isEmpty(tbroadcast.getBizStatus())) {
            wrapper.eq("biz_status", tbroadcast.getBizStatus());
        }
        if (!StringUtils.isEmpty(tbroadcast.getCreatedBy())) {
            wrapper.like("created_by", tbroadcast.getCreatedBy());
        }
        if (!StringUtils.isEmpty(tbroadcast.getUpdatedBy())) {
            wrapper.like("updated_by", tbroadcast.getUpdatedBy());
        }
        Page<Tbroadcast> tbroadcastPage = tbroadcastService.selectPage(
                new Page<Tbroadcast>(page, limit), wrapper.orderBy("id", false));
        return JsonResp.dataPageForAuto(tbroadcastPage);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteById")
    public JsonResp deleteById(Long id) {
        log.debug("删除");
        tbroadcastService.deleteById(id);
        return JsonResp.ok("删除成功");
    }


}
