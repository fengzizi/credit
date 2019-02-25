package com.credit.diversion.controller;

import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.model.ToptLog;
import com.credit.diversion.service.ToptLogService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @ToptLogController
 * @Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/toptLog")
@Transactional
@Slf4j
public class ToptLogController {

    @Autowired
    private ToptLogService toptLogService;

    /**
     * @添加
     * @param toptLog
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addToptLog(@RequestBody ToptLog toptLog){
        log.debug("添加");
        toptLogService.insert(toptLog);
        return JsonResp.ok(toptLog);
    }

    /**
     * @修改
     * @param toptLog
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateToptLog(@RequestBody ToptLog toptLog){
        log.debug("修改");
        toptLogService.updateById(toptLog);
        return JsonResp.ok(toptLog);
    }
    /**
     * @根据id查找
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectToptLog(Integer id){
        log.debug("查找");
        ToptLog toptLog = toptLogService.selectById(id);
        return JsonResp.ok(toptLog);
    }


}
