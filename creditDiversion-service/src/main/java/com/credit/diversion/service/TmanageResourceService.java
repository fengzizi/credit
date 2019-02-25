package com.credit.diversion.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.service.IService;
import com.credit.diversion.model.TmanageResource;

import java.util.List;

/**
 * @TmanageResourceService
 * @模块资源Service
 * @version : Ver 1.0
 */
public interface TmanageResourceService extends IService<TmanageResource>{

    /**
     * 获取子集 节点树
     * @param list
     * @param pid
     * @return
     */
    JSONArray getChildren(List<TmanageResource> list, Long pid);

}
