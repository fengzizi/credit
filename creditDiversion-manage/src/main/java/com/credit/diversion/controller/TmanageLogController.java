package com.credit.diversion.controller;

import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.model.TmanageLog;
import com.credit.diversion.service.TmanageLogService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @TmanageLogController
 * @后台日志Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tmanageLog")
@Transactional
@Slf4j
public class TmanageLogController {

    @Autowired
    private TmanageLogService tmanageLogService;

    /**
     * @添加后台日志
     * @param tmanageLog
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTmanageLog(@RequestBody TmanageLog tmanageLog){
        log.debug("添加后台日志");
        tmanageLogService.insert(tmanageLog);
        return JsonResp.ok(tmanageLog);
    }

    /**
     * @修改后台日志
     * @param tmanageLog
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTmanageLog(@RequestBody TmanageLog tmanageLog){
        log.debug("修改后台日志");
        tmanageLogService.updateById(tmanageLog);
        return JsonResp.ok(tmanageLog);
    }
    /**
     * @根据id查找后台日志
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTmanageLog(Integer id){
        log.debug("查找后台日志");
        TmanageLog tmanageLog = tmanageLogService.selectById(id);
        return JsonResp.ok(tmanageLog);
    }


}
