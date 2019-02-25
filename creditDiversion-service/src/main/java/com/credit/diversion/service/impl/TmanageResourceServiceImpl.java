package com.credit.diversion.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TmanageResourceMapper;
import com.credit.diversion.model.TmanageResource;
import com.credit.diversion.service.TmanageResourceService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @TmanageResourceServiceImpl
 * @模块资源ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TmanageResourceServiceImpl extends ServiceImpl<TmanageResourceMapper, TmanageResource> implements TmanageResourceService {

    @Autowired
    private TmanageResourceMapper tmanageResourceMapper;

    /**
     * 获取子集 节点树
     * @param list
     * @param pid
     * @return
     */
    public JSONArray getChildren(List<TmanageResource> list, Long pid){
        JSONArray jsonArray1=new JSONArray();
        if(list!=null&&list.size()>0){
            for (int j = 0; j < list.size(); j++) {
                if(list.get(j).getPid().equals(pid)){
                    JSONObject jsonObject1=new JSONObject();
                    jsonObject1.put("title",list.get(j).getResourceName());
                    jsonObject1.put("icon",list.get(j).getResourceIcon());
                    jsonObject1.put("href",list.get(j).getResourceUrl());
                    jsonObject1.put("spread",false);
                    jsonObject1.put("id",list.get(j).getId());
                    list.remove(j);
                    j--;
                    JSONArray jsonArray2=getChildren(list,jsonObject1.getLong("id"));
                    jsonObject1.put("children",jsonArray2);
                    jsonArray1.add(jsonObject1);

                }
            }
        }
        return jsonArray1;
    }
}
