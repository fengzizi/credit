package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TmanageCompanyInfo;
import com.credit.diversion.model.TmanageDepartmentInfo;
import com.credit.diversion.model.TmanageRoleInfo;
import com.credit.diversion.service.TmanageCompanyInfoService;
import com.credit.diversion.service.TmanageDepartmentInfoService;
import com.credit.diversion.util.commons.JsonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @TmanageCompanyInfoController
 * @公司列Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tmanageCompanyInfo")
@Transactional
@Slf4j
public class TmanageCompanyInfoController {

    @Autowired
    private TmanageCompanyInfoService tmanageCompanyInfoService;

    @Autowired
    private TmanageDepartmentInfoService tmanageDepartmentInfoService;

    /**
     * @添加公司列
     * @param tmanageCompanyInfo
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTmanageCompanyInfo(TmanageCompanyInfo tmanageCompanyInfo){
        log.debug("添加公司列");
        tmanageCompanyInfoService.insert(tmanageCompanyInfo);
        return JsonResp.ok(tmanageCompanyInfo);
    }

    /**
     * @修改公司列
     * @param tmanageCompanyInfo
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTmanageCompanyInfo(TmanageCompanyInfo tmanageCompanyInfo){
        log.debug("修改公司列");
        tmanageCompanyInfoService.updateById(tmanageCompanyInfo);
        return JsonResp.ok(tmanageCompanyInfo);
    }
    /**
     * @根据id查找公司列
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTmanageCompanyInfo(Integer id){
        log.debug("查找公司列");
        TmanageCompanyInfo tmanageCompanyInfo = tmanageCompanyInfoService.selectById(id);
        return JsonResp.ok(tmanageCompanyInfo);
    }

    /**
     * 查询列表
     * @param limit
     * @param page
     * @param companyInfo
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, TmanageCompanyInfo companyInfo) {
        Wrapper<TmanageCompanyInfo> wrapper = new EntityWrapper<TmanageCompanyInfo>();
        if (!StringUtils.isEmpty(companyInfo.getCompanyStatus())) {
            wrapper.eq("company_status", companyInfo.getCompanyStatus());
        }
        if (!StringUtils.isEmpty(companyInfo.getCompanyType())) {
            wrapper.eq("company_type", companyInfo.getCompanyType());
        }
        if (!StringUtils.isEmpty(companyInfo.getCompanyName())) {
            wrapper.like("company_name", companyInfo.getCompanyName());
        }
        Page<TmanageCompanyInfo> tbannerPage = tmanageCompanyInfoService.selectPage(
                new Page<TmanageCompanyInfo>(page, limit), wrapper.orderBy("id", false));
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
        tmanageCompanyInfoService.deleteById(id);
        tmanageDepartmentInfoService.delete(new EntityWrapper<TmanageDepartmentInfo>()
        .eq("company_id",id));
        return JsonResp.ok("删除成功");
    }

    /**
     * 查询列表
     * @param info
     * @return
     */
    @RequestMapping(value="/selectListName")
    public JsonResp selectListName(TmanageCompanyInfo info){
        log.debug("查找角色信息");
        Wrapper<TmanageCompanyInfo> wrapper=new EntityWrapper<TmanageCompanyInfo>();
        if(!StringUtils.isEmpty(info.getCompanyStatus())){
            wrapper.eq("company_status",info.getCompanyStatus());
        }
        if(!StringUtils.isEmpty(info.getCompanyType())){
            wrapper.eq("company_type",info.getCompanyType());
        }
        if(!StringUtils.isEmpty(info.getCompanyName())){
            wrapper.like("company_name",info.getCompanyName());
        }
        List<TmanageCompanyInfo> list = tmanageCompanyInfoService.selectList(wrapper);
        return JsonResp.ok(list);
    }
}
