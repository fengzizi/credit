package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TmanageCompanyInfo;
import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.model.TmanageRoleInfo;
import com.credit.diversion.service.TmanageRoleInfoService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @TmanageRoleInfoController
 * @角色信息Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tmanageRoleInfo")
@Transactional
@Slf4j
public class TmanageRoleInfoController {

    @Autowired
    private TmanageRoleInfoService tmanageRoleInfoService;

    /**
     * @添加角色信息
     * @param tmanageRoleInfo
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTmanageRoleInfo(TmanageRoleInfo tmanageRoleInfo){
        log.debug("添加角色信息");
        tmanageRoleInfoService.insert(tmanageRoleInfo);
        return JsonResp.ok(tmanageRoleInfo);
    }

    /**
     * @修改角色信息
     * @param tmanageRoleInfo
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTmanageRoleInfo(TmanageRoleInfo tmanageRoleInfo){
        log.debug("修改角色信息");
        tmanageRoleInfoService.updateById(tmanageRoleInfo);
        return JsonResp.ok(tmanageRoleInfo);
    }
    /**
     * @根据id查找角色信息
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTmanageRoleInfo(Integer id){
        log.debug("查找角色信息");
        TmanageRoleInfo tmanageRoleInfo = tmanageRoleInfoService.selectById(id);
        return JsonResp.ok(tmanageRoleInfo);
    }

    /**
     * 查询列表
     * @param info
     * @return
     */
    @RequestMapping(value="/selectListName")
    public JsonResp selectListName(TmanageRoleInfo info){
        log.debug("查找角色信息");
        Wrapper<TmanageRoleInfo> wrapper=new EntityWrapper<TmanageRoleInfo>();
        if(!StringUtils.isEmpty(info.getRoleStatus())){
            wrapper.eq("role_status",info.getRoleStatus());
        }
        if(!StringUtils.isEmpty(info.getRoleType())){
            wrapper.eq("role_type",info.getRoleType());
        }
        if(!StringUtils.isEmpty(info.getRoleName())){
            wrapper.like("role_name",info.getRoleName());
        }
        List<TmanageRoleInfo> list = tmanageRoleInfoService.selectList(wrapper);
        return JsonResp.ok(list);
    }

    /**
     * 查询列表
     * @param limit
     * @param page
     * @param roleInfo
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, TmanageRoleInfo roleInfo) {
        Wrapper<TmanageRoleInfo> wrapper = new EntityWrapper<TmanageRoleInfo>();
        if (!StringUtils.isEmpty(roleInfo.getRoleType())) {
            wrapper.eq("role_type", roleInfo.getRoleType());
        }
        if (!StringUtils.isEmpty(roleInfo.getRoleStatus())) {
            wrapper.eq("role_status", roleInfo.getRoleStatus());
        }
        if (!StringUtils.isEmpty(roleInfo.getRoleName())) {
            wrapper.like("role_name", roleInfo.getRoleName());
        }
        Page<TmanageRoleInfo> tbannerPage = tmanageRoleInfoService.selectPage(
                new Page<TmanageRoleInfo>(page, limit), wrapper.orderBy("id", false));
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
        tmanageRoleInfoService.deleteById(id);
        return JsonResp.ok("删除成功");
    }

}
