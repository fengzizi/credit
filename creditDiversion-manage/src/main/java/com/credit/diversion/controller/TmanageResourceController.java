package com.credit.diversion.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TcreditShopCategory;
import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.model.TmanageResource;
import com.credit.diversion.service.TmanageResourceService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @TmanageResourceController
 * @模块资源Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tmanageResource")
@Transactional
@Slf4j
public class TmanageResourceController {

    @Autowired
    private TmanageResourceService tmanageResourceService;

    /**
     * @添加模块资源
     * @param tmanageResource
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTmanageResource(TmanageResource tmanageResource){
        log.debug("添加模块资源");
        if(tmanageResource.getPid()==0){
            tmanageResource.setResourceType(0);
        }else{
            tmanageResource.setResourceType(1);
        }
        tmanageResourceService.insert(tmanageResource);
        return JsonResp.ok(tmanageResource);
    }

    /**
     * @修改模块资源
     * @param tmanageResource
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTmanageResource(TmanageResource tmanageResource){
        log.debug("修改模块资源");
        if(tmanageResource.getPid()==0){
            tmanageResource.setResourceType(0);
        }else{
            tmanageResource.setResourceType(1);
        }
        tmanageResourceService.updateById(tmanageResource);
        return JsonResp.ok(tmanageResource);
    }
    /**
     * @根据id查找模块资源
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTmanageResource(Long id){
        log.debug("查找模块资源");
        TmanageResource tmanageResource = tmanageResourceService.selectById(id);
        return JsonResp.ok(tmanageResource);
    }

    /**
     * 查询总列表
     * @param resource
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(TmanageResource resource) {
        Wrapper<TmanageResource> wrapper = new EntityWrapper<TmanageResource>();
        if (!StringUtils.isEmpty(resource.getPid())) {
            wrapper.eq("pid", resource.getPid());
        }
        if (!StringUtils.isEmpty(resource.getResourceStatus())) {
            wrapper.eq("resource_status", resource.getResourceStatus());
        }
        if (!StringUtils.isEmpty(resource.getResourceType())) {
            wrapper.eq("resource_type", resource.getResourceType());
        }
        if (!StringUtils.isEmpty(resource.getResourceName())) {
            wrapper.like("resource_name", resource.getResourceName());
        }
        List<TmanageResource> list = tmanageResourceService.selectList(wrapper
                .orderBy("resource_order",false)
                .orderBy("id", false));
        return JsonResp.ok(list);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/deleteById")
    public JsonResp deleteById(Long id){
        log.debug("删除");
        tmanageResourceService.deleteById(id);
        if(id!=null&&!id.equals(0L)){
            tmanageResourceService.delete(new EntityWrapper<TmanageResource>()
                    .eq("pid",id)
            );
        }

        return JsonResp.ok("删除成功");
    }

    @RequestMapping(value = "/selectListTree")
    public JsonResp selectListTree(TmanageResource resource) {
        Wrapper<TmanageResource> wrapper = new EntityWrapper<TmanageResource>();
        if (!StringUtils.isEmpty(resource.getPid())) {
            wrapper.eq("pid", resource.getPid());
        }
        if (!StringUtils.isEmpty(resource.getResourceStatus())) {
            wrapper.eq("resource_status", resource.getResourceStatus());
        }
        if (!StringUtils.isEmpty(resource.getResourceType())) {
            wrapper.eq("resource_type", resource.getResourceType());
        }
        if (!StringUtils.isEmpty(resource.getResourceName())) {
            wrapper.like("resource_name", resource.getResourceName());
        }
        List<TmanageResource> list = tmanageResourceService.selectList(wrapper
                .orderBy("resource_order",false)
                .orderBy("id", false));
        JSONArray jsonArray=tmanageResourceService.getChildren(list,0L);
        return JsonResp.ok(jsonArray);
    }



}
