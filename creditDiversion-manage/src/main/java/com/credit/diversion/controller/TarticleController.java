package com.credit.diversion.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.Tarticle;
import com.credit.diversion.model.TmanageLoginAccount;
import com.credit.diversion.service.TarticleService;
import com.credit.diversion.service.TmanageLoginAccountService;
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
 * @TarticleController
 * @文章信息Controller
 * @version : Ver 1.0
 */
@RestController
@RequestMapping(value="/manage/tarticle")
@Transactional
@Slf4j
public class TarticleController {

    @Autowired
    private TarticleService tarticleService;

    @Autowired
    private TmanageLoginAccountService tmanageLoginAccountService;

    /**
     * @添加文章信息
     * @param tarticle
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JsonResp addTarticle(Tarticle tarticle){
        log.debug("添加文章信息");
        tarticle.setCreatedAt(new Date());
        TmanageLoginAccount tadmin=tmanageLoginAccountService.selectLoginIng();
        tarticle.setCreatedBy(tadmin.getUserName());
        tarticleService.insert(tarticle);
        return JsonResp.ok(tarticle);
    }

    /**
     * @修改文章信息
     * @param tarticle
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JsonResp updateTarticle(Tarticle tarticle){
        log.debug("修改文章信息");

        if(tarticle.getImage()!=null){
            Tarticle old=tarticleService.selectById(tarticle.getId());
            if(old!=null&&!StringUtils.isEmpty(old.getImage())){
                FileUtil.deleteSingleFile(old.getImage());//删除原图片
            }
        }

        tarticle.setUpdatedAt(new Date());
        TmanageLoginAccount tadmin=tmanageLoginAccountService.selectLoginIng();
        tarticle.setUpdatedBy(tadmin.getUserName());
        tarticleService.updateById(tarticle);
        return JsonResp.ok(tarticle);
    }

    /**
     * 上传图片
     * @param imageFile
     * @param request
     * @return
     */
    @RequestMapping(value="/uploadImg", method = RequestMethod.POST)
    public JsonResp uploadImg( MultipartFile imageFile, HttpServletRequest request){
        log.debug("修改文章信息");
        FileResultEntity entity=FileUtil.upload(imageFile,request,"article");
        if(!entity.isSuccess()){
            return JsonResp.toFail(entity.getErrorMsg());
        }
        return JsonResp.ok(entity);
    }

    /**
     * @根据id查找文章信息
     * @param id
     * @return 返回值JsonResp
     */
    @RequestMapping(value="/selectOne", method = RequestMethod.GET)
    public JsonResp selectTarticle(Integer id){
        log.debug("查找文章信息");
        Tarticle tarticle = tarticleService.selectById(id);
        return JsonResp.ok(tarticle);
    }

    /**
     * 查询列表
     * @param limit
     * @param page
     * @param tarticle
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, Tarticle tarticle) {
        Wrapper<Tarticle> wrapper = new EntityWrapper<Tarticle>();
        if (!StringUtils.isEmpty(tarticle.getCreatedBy())) {
            wrapper.like("created_by", tarticle.getCreatedBy());
        }
        if (!StringUtils.isEmpty(tarticle.getTitle())) {
            wrapper.like("title", tarticle.getTitle());
        }
        if (!StringUtils.isEmpty(tarticle.getUpdatedBy())) {
            wrapper.like("updated_by", tarticle.getUpdatedBy());
        }
        Page<Tarticle> tarticlePage = tarticleService.selectPage(
                new Page<Tarticle>(page, limit), wrapper.orderBy("id", false));
        return JsonResp.dataPageForAuto(tarticlePage);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/deleteById")
    public JsonResp deleteById(Integer id){
        log.debug("删除");
        Tarticle old=tarticleService.selectById(id);
        if(old==null){
            return JsonResp.toFail("未找到该记录");
        }
        if(old!=null&&!StringUtils.isEmpty(old.getImage())){
            FileUtil.deleteSingleFile(old.getImage());//删除原图片
        }
        tarticleService.deleteById(id);
        return JsonResp.ok("删除成功");
    }

}
