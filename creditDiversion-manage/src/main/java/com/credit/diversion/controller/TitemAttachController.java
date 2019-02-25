package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TcreditShopItem;
import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.model.TitemAttach;
import com.credit.diversion.service.TitemAttachService;

import com.credit.diversion.util.qiniu.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @TitemAttachController
 * @信贷附属Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/titemAttach")
@Transactional
@Slf4j
public class TitemAttachController {

    @Autowired
    private TitemAttachService titemAttachService;

    /**
     * @添加信贷附属
     * @param titemAttach
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTitemAttach(TitemAttach titemAttach){
        log.debug("添加信贷附属");
        titemAttachService.insert(titemAttach);
        return JsonResp.ok(titemAttach);
    }

    /**
     * @修改信贷附属
     * @param titemAttach
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTitemAttach(TitemAttach titemAttach){
        log.debug("修改信贷附属");
        titemAttachService.updateById(titemAttach);
        return JsonResp.ok(titemAttach);
    }
    /**
     * @根据id查找信贷附属
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTitemAttach(Integer id){
        log.debug("查找信贷附属");
        TitemAttach titemAttach = titemAttachService.selectById(id);
        return JsonResp.ok(titemAttach);
    }

    /**
     * 查询列表
     * @param limit
     * @param page
     * @param titemAttach
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, TitemAttach titemAttach) {
        Wrapper<TitemAttach> wrapper = new EntityWrapper<TitemAttach>();
        if (!StringUtils.isEmpty(titemAttach.getPlatformDesc())) {
            wrapper.like("platform_desc", titemAttach.getPlatformDesc());
        }
        if (!StringUtils.isEmpty(titemAttach.getApplyCondition())) {
            wrapper.eq("apply_condition", titemAttach.getApplyCondition());
        }
        Page<TitemAttach> tarticlePage = titemAttachService.selectPage(
                new Page<TitemAttach>(page, limit), wrapper.orderBy("item_id", false));
        return JsonResp.dataPageForAuto(tarticlePage);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/deleteById")
    public JsonResp deleteById(Long id){
        log.debug("删除");
        titemAttachService.deleteById(id);
        return JsonResp.ok("删除成功");
    }

}
