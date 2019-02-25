package com.credit.diversion.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TcreditShopCategory;
import com.credit.diversion.service.TcreditShopCategoryService;
import com.credit.diversion.util.commons.JsonResp;
import com.google.gson.JsonArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @TcreditShopCategoryController
 * @信贷类型分类Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tcreditShopCategory")
@Transactional
@Slf4j
public class TcreditShopCategoryController {

    @Autowired
    private TcreditShopCategoryService tcreditShopCategoryService;

    /**
     * @添加信贷类型分类
     * @param tcreditShopCategory
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTcreditShopCategory(TcreditShopCategory tcreditShopCategory){
        log.debug("添加信贷类型分类");
        if(tcreditShopCategory.getParentId().equals(0L)){
            tcreditShopCategory.setParent(true);
        }else{
            tcreditShopCategory.setParent(false);
        }
        tcreditShopCategory.setCreated(new Date());
        tcreditShopCategoryService.insert(tcreditShopCategory);
        return JsonResp.ok(tcreditShopCategory);
    }

    /**
     * @修改信贷类型分类
     * @param tcreditShopCategory
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTcreditShopCategory(TcreditShopCategory tcreditShopCategory){
        log.debug("修改信贷类型分类");
        tcreditShopCategory.setUpdated(new Date());
        if(tcreditShopCategory.getParentId().equals(0L)){
            tcreditShopCategory.setParent(true);
        }else{
            tcreditShopCategory.setParent(false);
        }
        tcreditShopCategoryService.updateById(tcreditShopCategory);
        return JsonResp.ok(tcreditShopCategory);
    }
    /**
     * @根据id查找信贷类型分类
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTcreditShopCategory(Integer id){
        log.debug("查找信贷类型分类");
        TcreditShopCategory tcreditShopCategory = tcreditShopCategoryService.selectById(id);
        return JsonResp.ok(tcreditShopCategory);
    }

    /**
     * 查询列表
     * @param limit
     * @param page
     * @param tcreditShopCategory
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, TcreditShopCategory tcreditShopCategory) {
        Wrapper<TcreditShopCategory> wrapper = new EntityWrapper<TcreditShopCategory>();
        if (!StringUtils.isEmpty(tcreditShopCategory.getParent())) {
            wrapper.eq("parent", tcreditShopCategory.getParent());
        }
        if (!StringUtils.isEmpty(tcreditShopCategory.getStatus())) {
            wrapper.eq("status", tcreditShopCategory.getStatus());
        }
        if (!StringUtils.isEmpty(tcreditShopCategory.getName())) {
            wrapper.like("name", tcreditShopCategory.getName());
        }
        Page<TcreditShopCategory> tcreditShopCategoryPage = tcreditShopCategoryService.selectPage(
                new Page<TcreditShopCategory>(page, limit), wrapper.orderBy("id", false));
        return JsonResp.dataPageForAuto(tcreditShopCategoryPage);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/deleteById")
    public JsonResp deleteById(Long id){
        log.debug("删除");
        tcreditShopCategoryService.deleteById(id);
        return JsonResp.ok("删除成功");
    }

    /**
     * 查询节点树
     * @param tcreditShopCategory
     * @return
     */
    @RequestMapping(value="/selectListTree")
    public JsonResp selectListTree(TcreditShopCategory tcreditShopCategory){
        Wrapper<TcreditShopCategory> wrapper = new EntityWrapper<TcreditShopCategory>();
        if (!StringUtils.isEmpty(tcreditShopCategory.getStatus())) {
            wrapper.eq("status", tcreditShopCategory.getStatus());
        }
        List<TcreditShopCategory> list=tcreditShopCategoryService.selectList(wrapper
                .orderBy("sort_order",false)
                .orderBy("id",false)
        );
        List<TcreditShopCategory> oldList=new ArrayList<TcreditShopCategory>();
        oldList.addAll(list);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list",oldList);
        JSONArray array=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("name","全部");
        jsonObject.put("spread",true);
        JSONArray jsonArray=getChildren(list,0L);
        jsonObject.put("children",jsonArray);
        array.add(jsonObject);
        map.put("tree",array);
        return JsonResp.ok(map);
    }

    /**
     * 获取子集 节点树
     * @param list
     * @param parentId
     * @return
     */
    public JSONArray getChildren(List<TcreditShopCategory> list,Long parentId){
        JSONArray jsonArray1=new JSONArray();
        if(list!=null&&list.size()>0){
            for (int j = 0; j < list.size(); j++) {
                if(list.get(j).getParentId().equals(parentId)){
                    JSONObject jsonObject1=new JSONObject();
                    jsonObject1.put("name",list.get(j).getName());
                    jsonObject1.put("id",list.get(j).getId());
                    list.remove(j);
                    j--;
                    JSONArray jsonArray2=getChildren(list,jsonObject1.getLong("id"));
                    if(jsonArray2!=null){
                        j=j-jsonArray2.size();
                        jsonObject1.put("children",jsonArray2);
                        StringBuilder stringBuilder=new StringBuilder();
                        for (int i = 0; i < jsonArray2.size(); i++) {
                            stringBuilder.append(jsonArray2.getJSONObject(i).getLong("id")).append(",");
                        }
                        stringBuilder.append(jsonObject1.getLong("id"));
                        jsonObject1.put("ids",stringBuilder);
                        jsonArray1.add(jsonObject1);
                    }
                }
            }
        }
        return jsonArray1;
    }

    /**
     * 查询选择框内容
     * @param tcreditShopCategory
     * @return
     */
    @RequestMapping(value="/selectListName")
    public JsonResp selectListName(TcreditShopCategory tcreditShopCategory,Long notParentId){
        Wrapper<TcreditShopCategory> wrapper = new EntityWrapper<TcreditShopCategory>();
        if (!StringUtils.isEmpty(tcreditShopCategory.getStatus())) {
            wrapper.eq("status", tcreditShopCategory.getStatus());
        }
        if (!StringUtils.isEmpty(tcreditShopCategory.getParentId())) {
            wrapper.eq("parent_id", tcreditShopCategory.getParentId());
        }
        if(!StringUtils.isEmpty(notParentId)){
            wrapper.notIn("parent_id",notParentId);//非父类目
        }
        List<TcreditShopCategory> list=tcreditShopCategoryService.selectList(wrapper
                .orderBy("sort_order",false)
                .orderBy("id",false)
        );
        return JsonResp.ok(list);
    }
}
