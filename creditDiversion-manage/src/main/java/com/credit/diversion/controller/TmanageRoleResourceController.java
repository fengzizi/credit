package com.credit.diversion.controller;

import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.model.TmanageRoleResource;
import com.credit.diversion.service.TmanageRoleResourceService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @TmanageRoleResourceController
 * @角色权限Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tmanageRoleResource")
@Transactional
@Slf4j
public class TmanageRoleResourceController {

    @Autowired
    private TmanageRoleResourceService tmanageRoleResourceService;

    /**
     * @添加角色权限
     * @param tmanageRoleResource
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTmanageRoleResource(@RequestBody TmanageRoleResource tmanageRoleResource){
        log.debug("添加角色权限");
        tmanageRoleResourceService.insert(tmanageRoleResource);
        return JsonResp.ok(tmanageRoleResource);
    }

    /**
     * @修改角色权限
     * @param tmanageRoleResource
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTmanageRoleResource(@RequestBody TmanageRoleResource tmanageRoleResource){
        log.debug("修改角色权限");
        tmanageRoleResourceService.updateById(tmanageRoleResource);
        return JsonResp.ok(tmanageRoleResource);
    }
    /**
     * @根据id查找角色权限
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTmanageRoleResource(Integer id){
        log.debug("查找角色权限");
        TmanageRoleResource tmanageRoleResource = tmanageRoleResourceService.selectById(id);
        return JsonResp.ok(tmanageRoleResource);
    }


}
