package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TmanageCompanyInfo;
import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.model.TmanageDepartmentInfo;
import com.credit.diversion.service.TmanageDepartmentInfoService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @TmanageDepartmentInfoController
 * @企业部门信息Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tmanageDepartmentInfo")
@Transactional
@Slf4j
public class TmanageDepartmentInfoController {

    @Autowired
    private TmanageDepartmentInfoService tmanageDepartmentInfoService;

    /**
     * @添加企业部门信息
     * @param tmanageDepartmentInfo
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTmanageDepartmentInfo(TmanageDepartmentInfo tmanageDepartmentInfo){
        log.debug("添加企业部门信息");
        tmanageDepartmentInfoService.insert(tmanageDepartmentInfo);
        return JsonResp.ok(tmanageDepartmentInfo);
    }

    /**
     * @修改企业部门信息
     * @param tmanageDepartmentInfo
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTmanageDepartmentInfo(TmanageDepartmentInfo tmanageDepartmentInfo){
        log.debug("修改企业部门信息");
        tmanageDepartmentInfoService.updateById(tmanageDepartmentInfo);
        return JsonResp.ok(tmanageDepartmentInfo);
    }
    /**
     * @根据id查找企业部门信息
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTmanageDepartmentInfo(Integer id){
        log.debug("查找企业部门信息");
        TmanageDepartmentInfo tmanageDepartmentInfo = tmanageDepartmentInfoService.selectById(id);
        return JsonResp.ok(tmanageDepartmentInfo);
    }

    /**
     * 查询列表
     * @param limit
     * @param page
     * @param departmentInfo
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, TmanageDepartmentInfo departmentInfo) {
        Wrapper<TmanageDepartmentInfo> wrapper = new EntityWrapper<TmanageDepartmentInfo>();
        if (!StringUtils.isEmpty(departmentInfo.getCompanyId())) {
            wrapper.eq("company_id", departmentInfo.getCompanyId());
        }
        if (!StringUtils.isEmpty(departmentInfo.getDepartmentStatus())) {
            wrapper.eq("department_status", departmentInfo.getDepartmentStatus());
        }
        if (!StringUtils.isEmpty(departmentInfo.getDepartmentType())) {
            wrapper.eq("department_type", departmentInfo.getDepartmentType());
        }
        if (!StringUtils.isEmpty(departmentInfo.getDepartmentName())) {
            wrapper.like("department_name", departmentInfo.getDepartmentName());
        }
        Page<TmanageDepartmentInfo> tbannerPage = tmanageDepartmentInfoService.selectPage(
                new Page<TmanageDepartmentInfo>(page, limit), wrapper.orderBy("id", false));
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
        tmanageDepartmentInfoService.deleteById(id);
        return JsonResp.ok("删除成功");
    }

    /**
     * 查询选择列表
     * @param info
     * @return
     */
    @RequestMapping(value="/selectListName")
    public JsonResp selectListName(TmanageDepartmentInfo info){
        log.debug("查找角色信息");
        Wrapper<TmanageDepartmentInfo> wrapper=new EntityWrapper<TmanageDepartmentInfo>();
        if(!StringUtils.isEmpty(info.getDepartmentType())){
            wrapper.eq("department_type",info.getDepartmentType());
        }
        if(!StringUtils.isEmpty(info.getDepartmentStatus())){
            wrapper.eq("department_status",info.getDepartmentStatus());
        }
        if(!StringUtils.isEmpty(info.getCompanyId())){
            wrapper.eq("company_id",info.getCompanyId());
        }
        if(!StringUtils.isEmpty(info.getId())){
            wrapper.eq("id",info.getId());
        }
        if(!StringUtils.isEmpty(info.getDepartmentName())){
            wrapper.like("department_name",info.getDepartmentName());
        }
        List<TmanageDepartmentInfo> list = tmanageDepartmentInfoService.selectList(wrapper);
        return JsonResp.ok(list);
    }

}
