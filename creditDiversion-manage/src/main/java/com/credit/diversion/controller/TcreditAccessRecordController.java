package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TuserAuthInfo;
import com.credit.diversion.model.TuserLoginInfo;
import com.credit.diversion.util.DateUtils;
import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.model.TcreditAccessRecord;
import com.credit.diversion.service.TcreditAccessRecordService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @TcreditAccessRecordController
 * @Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tcreditAccessRecord")
@Transactional
@Slf4j
public class TcreditAccessRecordController {

    @Autowired
    private TcreditAccessRecordService tcreditAccessRecordService;

    /**
     * @添加
     * @param tcreditAccessRecord
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTcreditAccessRecord(TcreditAccessRecord tcreditAccessRecord){
        log.debug("添加");
        tcreditAccessRecordService.insert(tcreditAccessRecord);
        return JsonResp.ok(tcreditAccessRecord);
    }

    /**
     * @修改
     * @param tcreditAccessRecord
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTcreditAccessRecord(TcreditAccessRecord tcreditAccessRecord){
        log.debug("修改");
        tcreditAccessRecordService.updateById(tcreditAccessRecord);
        return JsonResp.ok(tcreditAccessRecord);
    }
    /**
     * @根据id查找
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTcreditAccessRecord(Integer id){
        log.debug("查找");
        TcreditAccessRecord tcreditAccessRecord = tcreditAccessRecordService.selectById(id);
        return JsonResp.ok(tcreditAccessRecord);
    }

    /**
     * 查询列表
     *
     * @param limit
     * @param page
     * @param record
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, TcreditAccessRecord record) {
        Wrapper<TcreditAccessRecord> wrapper = new EntityWrapper<TcreditAccessRecord>();
        if (!StringUtils.isEmpty(record.getCreditId())) {
            wrapper.eq("credit_id", record.getCreditId());
        }
        if (!StringUtils.isEmpty(record.getUserId())) {
            wrapper.eq("user_id", record.getUserId());
        }
        if (!StringUtils.isEmpty(record.getIsRegister())) {
            wrapper.eq("is_register", record.getIsRegister());
        }

        if (!StringUtils.isEmpty(record.getCreditName())) {
            wrapper.like("credit_name", record.getCreditName());
        }
        Page<TcreditAccessRecord> recordPage = tcreditAccessRecordService.selectPage(
                new Page<TcreditAccessRecord>(page, limit), wrapper.orderBy("id", false));
        return JsonResp.dataPageForAuto(recordPage);
    }

    /**
     * 分组统计
     * @return
     */
    @RequestMapping(value = "/selectSumGroup")
    public JsonResp selectSumGroup(Long id,Integer day) {
        Date date=new Date();
        return JsonResp.ok(tcreditAccessRecordService.selectSumGroup(id,date,day));
    }

}
