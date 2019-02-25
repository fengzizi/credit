package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TcreditShopItem;
import com.credit.diversion.model.TitemAttach;
import com.credit.diversion.model.TitemComment;
import com.credit.diversion.service.TcreditShopItemService;
import com.credit.diversion.service.TitemAttachService;
import com.credit.diversion.service.TitemCommentService;
import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.util.qiniu.FileResultEntity;
import com.credit.diversion.util.qiniu.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @TcreditShopItemController
 * @贷超甲方产品信息Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tcreditShopItem")
@Transactional
@Slf4j
public class TcreditShopItemController {

    @Autowired
    private TcreditShopItemService tcreditShopItemService;

    @Autowired
    private TitemCommentService titemCommentService;

    @Autowired
    private TitemAttachService titemAttachService;

    /**
     * @添加贷超甲方产品信息
     * @param tcreditShopItem
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTcreditShopItem(TcreditShopItem tcreditShopItem, TitemComment titemComment, TitemAttach titemAttach){
        log.debug("添加贷超甲方产品信息");
        tcreditShopItem.setCreated(new Date());
        tcreditShopItemService.insert(tcreditShopItem);
        titemAttach.setItemId(tcreditShopItem.getId());
        titemAttachService.insert(titemAttach);
        titemComment.setItemId(tcreditShopItem.getId());
        titemCommentService.insert(titemComment);
        return JsonResp.ok(tcreditShopItem);
    }

    /**
     * @修改贷超甲方产品信息
     * @param tcreditShopItem
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTcreditShopItem(TcreditShopItem tcreditShopItem, TitemComment titemComment, TitemAttach titemAttach){
        log.debug("修改贷超甲方产品信息");
        tcreditShopItem.setUpdated(new Date());
        if(tcreditShopItem.getAppIconUrl()!=null){
            TcreditShopItem old=tcreditShopItemService.selectById(tcreditShopItem.getId());
            if(old!=null&&!StringUtils.isEmpty(old.getAppIconUrl())){
                FileUtil.deleteSingleFile(old.getAppIconUrl());//删除原图片
            }
        }
        if(titemComment.getAvgCollection()!=null){
            TitemComment comment=titemCommentService.selectById(tcreditShopItem.getId());
            titemComment.setItemId(tcreditShopItem.getId());
            if(comment==null){
                titemCommentService.insert(titemComment);
            }else{
                titemCommentService.updateById(titemComment);
            }
        }
        if(!StringUtils.isEmpty(titemAttach.getApplyCondition())){
            TitemAttach attach=titemAttachService.selectById(tcreditShopItem.getId());
            titemAttach.setItemId(tcreditShopItem.getId());
            if(attach==null){
                titemAttachService.insert(titemAttach);
            }else{
                titemAttachService.updateById(titemAttach);
            }
        }
        tcreditShopItemService.updateById(tcreditShopItem);
        return JsonResp.ok(tcreditShopItem);
    }
    /**
     * @根据id查找贷超甲方产品信息
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTcreditShopItem(Integer id){
        log.debug("查找贷超甲方产品信息");
        TcreditShopItem tcreditShopItem = tcreditShopItemService.selectById(id);
        if(tcreditShopItem!=null){
            TitemComment comment=titemCommentService.selectById(id);
            tcreditShopItem.setTitemComment(comment);
            TitemAttach attach=titemAttachService.selectById(id);
            tcreditShopItem.setTitemAttach(attach);
        }
        return JsonResp.ok(tcreditShopItem);
    }

    /**
     * 上传图片
     * @param imageFile
     * @param request
     * @return
     */
    @RequestMapping(value="/uploadImg", method = RequestMethod.POST)
    public JsonResp uploadImg(MultipartFile imageFile, HttpServletRequest request){
        log.debug("修改文章信息");
        FileResultEntity entity=FileUtil.upload(imageFile,request,"creditShopItem/appIcon");
        if(!entity.isSuccess()){
            return JsonResp.toFail(entity.getErrorMsg());
        }
        return JsonResp.ok(entity);
    }

    /**
     * 查询列表
     * @param limit
     * @param page
     * @param tcreditShopItem
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, TcreditShopItem tcreditShopItem,String categoryIds) {
        /*Wrapper<TcreditShopItem> wrapper = new EntityWrapper<TcreditShopItem>();
        if (!StringUtils.isEmpty(tcreditShopItem.getState())) {
            wrapper.eq("state", tcreditShopItem.getState());
        }
        if (!StringUtils.isEmpty(tcreditShopItem.getCategoryId())) {
            wrapper.eq("category_id", tcreditShopItem.getCategoryId());
        }
        if (!StringUtils.isEmpty(tcreditShopItem.getTag())) {
            wrapper.eq("tag", tcreditShopItem.getTag());
        }
        if (!StringUtils.isEmpty(tcreditShopItem.getAppName())) {
            wrapper.like("app_name", tcreditShopItem.getAppName());
        }
        if(!StringUtils.isEmpty(categoryIds)){
            wrapper.in("category_id", categoryIds.split(","));
        }

        Page<TcreditShopItem> tarticlePage = tcreditShopItemService.selectPage(
                new Page<TcreditShopItem>(page, limit), wrapper.orderBy("id", false));*/
        String [] array=null;
        if(!StringUtils.isEmpty(categoryIds)){
            array= categoryIds.split(",");
        }
        Page<TcreditShopItem> tarticlePage=tcreditShopItemService.selectListAll(new Page<TcreditShopItem>(page,limit),tcreditShopItem,array);
        return JsonResp.dataPageForAuto(tarticlePage);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/deleteById")
    public JsonResp deleteById(Long id){
        log.debug("删除");
        TcreditShopItem old=tcreditShopItemService.selectById(id);
        if(old==null){
            return JsonResp.toFail("未找到该记录");
        }
        if(!StringUtils.isEmpty(old.getAppIconUrl())){
            FileUtil.deleteSingleFile(old.getAppIconUrl());//删除原图片
        }
        titemCommentService.deleteById(id);
        tcreditShopItemService.deleteById(id);
        titemAttachService.deleteById(id);
        return JsonResp.ok("删除成功");
    }

    /**
     * 查询列表
     * @param tcreditShopItem
     * @return
     */
    @RequestMapping(value = "/selectListAll")
    public JsonResp selectListAll(TcreditShopItem tcreditShopItem) {
        Wrapper<TcreditShopItem> wrapper = new EntityWrapper<TcreditShopItem>();
        if (!StringUtils.isEmpty(tcreditShopItem.getState())) {
            wrapper.eq("state", tcreditShopItem.getState());
        }
        if (!StringUtils.isEmpty(tcreditShopItem.getCategoryId())) {
            wrapper.eq("category_id", tcreditShopItem.getCategoryId());
        }
        if (!StringUtils.isEmpty(tcreditShopItem.getTag())) {
            wrapper.eq("tag", tcreditShopItem.getTag());
        }
        if (!StringUtils.isEmpty(tcreditShopItem.getAppName())) {
            wrapper.like("app_name", tcreditShopItem.getAppName());
        }

        List<TcreditShopItem> list = tcreditShopItemService.selectList(wrapper.orderBy("id", false));
        return JsonResp.ok(list);
    }
}
