package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.Tbanner;
import com.credit.diversion.service.TbannerService;
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

/**
 * @TbannerController
 * @首页banner记录Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tbanner")
@Transactional
@Slf4j
public class TbannerController {

    @Autowired
    private TbannerService tbannerService;

    /**
     * @添加首页banner记录
     * @param tbanner
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTbanner(Tbanner tbanner){
        log.debug("添加首页banner记录");
        tbanner.setCreated(new Date());
        tbannerService.insert(tbanner);
        return JsonResp.ok(tbanner);
    }

    /**
     * @修改首页banner记录
     * @param tbanner
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTbanner(Tbanner tbanner){
        log.debug("修改首页banner记录");
        if(tbanner.getPicUrl()!=null){
            Tbanner old=tbannerService.selectById(tbanner.getId());
            if(old!=null&&!StringUtils.isEmpty(old.getPicUrl())){
                FileUtil.deleteSingleFile(old.getPicUrl());//删除原图片
            }
        }

        tbanner.setUpdated(new Date());
        tbannerService.updateById(tbanner);
        return JsonResp.ok(tbanner);
    }
    /**
     * @根据id查找首页banner记录
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTbanner(Integer id){
        log.debug("查找首页banner记录");
        Tbanner tbanner = tbannerService.selectById(id);
        return JsonResp.ok(tbanner);
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
        FileResultEntity entity=FileUtil.upload(imageFile,request,"banner");
        if(!entity.isSuccess()){
            return JsonResp.toFail(entity.getErrorMsg());
        }
        return JsonResp.ok(entity);
    }

    /**
     * 查询列表
     * @param limit
     * @param page
     * @param tbanner
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, Tbanner tbanner) {
        Wrapper<Tbanner> wrapper = new EntityWrapper<Tbanner>();
        if (!StringUtils.isEmpty(tbanner.getSubTitle())) {
            wrapper.like("sub_title", tbanner.getSubTitle());
        }
        Page<Tbanner> tbannerPage = tbannerService.selectPage(
                new Page<Tbanner>(page, limit), wrapper.orderBy("id", false));
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
        Tbanner old=tbannerService.selectById(id);
        if(old==null){
            return JsonResp.toFail("未找到该记录");
        }
        if(old!=null&&!StringUtils.isEmpty(old.getPicUrl())){
            FileUtil.deleteSingleFile(old.getPicUrl());//删除原图片
        }
        tbannerService.deleteById(id);
        return JsonResp.ok("删除成功");
    }

}
