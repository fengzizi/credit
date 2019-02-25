package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TitemAttach;
import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.model.TitemComment;
import com.credit.diversion.service.TitemCommentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @TitemCommentController
 * @信贷评论统计Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/titemComment")
@Transactional
@Slf4j
public class TitemCommentController {

    @Autowired
    private TitemCommentService titemCommentService;

    /**
     * @添加信贷评论统计
     * @param titemComment
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTitemComment(@RequestBody TitemComment titemComment){
        log.debug("添加信贷评论统计");
        titemCommentService.insert(titemComment);
        return JsonResp.ok(titemComment);
    }

    /**
     * @修改信贷评论统计
     * @param titemComment
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTitemComment(@RequestBody TitemComment titemComment){
        log.debug("修改信贷评论统计");
        titemCommentService.updateById(titemComment);
        return JsonResp.ok(titemComment);
    }
    /**
     * @根据id查找信贷评论统计
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTitemComment(Integer id){
        log.debug("查找信贷评论统计");
        TitemComment titemComment = titemCommentService.selectById(id);
        return JsonResp.ok(titemComment);
    }

    /**
     * 查询列表
     * @param limit
     * @param page
     * @param comment
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, TitemComment comment) {
        Page<TitemComment> tarticlePage = titemCommentService.selectPage(
                new Page<TitemComment>(page, limit), new EntityWrapper<TitemComment>().orderBy("item_id", false));
        return JsonResp.dataPageForAuto(tarticlePage);
    }
}
