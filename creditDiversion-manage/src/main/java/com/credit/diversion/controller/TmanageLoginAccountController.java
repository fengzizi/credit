package com.credit.diversion.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TmanageLoginAccount;
import com.credit.diversion.model.TmanageResource;
import com.credit.diversion.service.TmanageLoginAccountService;
import com.credit.diversion.service.TmanageResourceService;
import com.credit.diversion.shiro.NoSetPasswordException;
import com.credit.diversion.shiro.ShiroSecurityHelper;
import com.credit.diversion.shiro.UsernamePasswordToken;
import com.credit.diversion.util.UserAgentUtil;
import com.credit.diversion.util.commons.JsonCodeEnum;
import com.credit.diversion.util.commons.JsonResp;
import com.credit.diversion.util.encryption.Digests;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version : Ver 1.0
 * @TmanageLoginAccountController
 * @后台登录帐号Controller
 */
@RestController
@RequestMapping(value = "/manage/tmanageLoginAccount")
@Transactional
@Slf4j
public class TmanageLoginAccountController {

    @Autowired
    private TmanageLoginAccountService tmanageLoginAccountService;

    @Autowired
    private TmanageResourceService tmanageResourceService;

    @Autowired
    private SessionDAO sessionDAO;

    /**
     * @param admin
     * @return 返回值JsonResp
     * @添加管理员列
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResp addAdmin(@RequestBody TmanageLoginAccount admin) {
        log.debug("添加管理员列");
        tmanageLoginAccountService.insert(admin);
        return JsonResp.ok(admin);
    }

    /**
     * @param id
     * @return 返回值JsonResp
     * @根据id查找管理员列
     */
    @RequestMapping(value = "/selectOne", method = RequestMethod.GET)
    public JsonResp selectAdmin(Long id) {
        log.debug("查找管理员列");
        TmanageLoginAccount admin = tmanageLoginAccountService.selectById(id);
        return JsonResp.ok(admin);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "/logout")
    public JsonResp logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return JsonResp.ok("退出成功");
    }

    /**
     * 修改当前用户登录密码
     *
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @RequestMapping(value = "/updatePwd")
    public JsonResp updatePwd(String oldPwd, String newPwd) {
        TmanageLoginAccount admin = tmanageLoginAccountService.selectLoginIng();
        boolean flag = Digests.validatePassword(String.valueOf(oldPwd), admin.getLoginPassword());
        if (flag) {
            TmanageLoginAccount admin1 = new TmanageLoginAccount();
            admin1.setId(admin.getId());
            admin1.setLoginPassword(Digests.entryptPassword(newPwd));
            flag = tmanageLoginAccountService.updateById(admin1);
            if (!flag) {
                return JsonResp.toFail("修改失败");
            } else {
                ShiroSecurityHelper.kickOutUser(admin.getLoginName(), sessionDAO);//剔出
                Subject subject = SecurityUtils.getSubject();
                subject.logout();//需重新登录
            }
        } else {
            return JsonResp.toFail("原密码错误");
        }
        return JsonResp.ok("修改成功");
    }


    /**
     * 登录
     *
     * @param admin
     * @param response
     * @param rememberMe
     * @return
     */
    @RequestMapping(value = "/login")
    public JsonResp login(TmanageLoginAccount admin, HttpServletResponse response, String rememberMe, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        if (admin == null || StringUtils.isEmpty(admin.getLoginName()) || StringUtils.isEmpty(admin.getLoginPassword())) {
            return JsonResp.toFail("请输入账号/密码");
        }
        boolean rememberMeFlag = false;
        if (!StringUtils.isEmpty(rememberMe) && rememberMe.equals("yes")) {
            rememberMeFlag = true;
        }
        String host = UserAgentUtil.getIp(request);
        subject.logout();
        UsernamePasswordToken token = new UsernamePasswordToken(admin.getLoginName(), admin.getLoginPassword().toCharArray(), rememberMeFlag, host,
                "", false);
        try {
            token.setRememberMe(rememberMeFlag);
            subject.login(token);
        } catch (UnknownAccountException e) {
            return JsonResp.toFail("账号不存在");
        } catch (IncorrectCredentialsException e) {
            return JsonResp.toFail("密码错误");
        } catch (NoSetPasswordException e) {
            return JsonResp.toFail(e.getMessage());
        } catch (AuthenticationException e) {
            // 其他错误，比如锁定，如果想单独处理请单独catch 处理
            log.debug("其他错误", e);
            return JsonResp.toFail("其他错误：" + e.getMessage());
        }
        Session session = subject.getSession();
        Serializable sessionId = session.getId();

        TmanageLoginAccount newAdmin = tmanageLoginAccountService.selectOne(new EntityWrapper<TmanageLoginAccount>()
                .eq("login_name", admin.getLoginName()));
        newAdmin.setToken(sessionId.toString());
        Cookie cookie = new Cookie("JSESSIONID", sessionId.toString());//重写cookie里的JSESSIONID 防止用户浏览器关闭session被销毁
        cookie.setMaxAge(360 * 60);//有效期
        cookie.setPath("/");
        response.addCookie(cookie);
        TmanageLoginAccount param = new TmanageLoginAccount();
        param.setLastLoginTime(new Date());
        param.setLastLoginIp(UserAgentUtil.getIp(request));
        param.setId(newAdmin.getId());
        tmanageLoginAccountService.updateById(param);
        return JsonResp.ok(newAdmin);
    }

    /**
     * @param admin
     * @return 返回值JsonResp
     * @修改风控合作商 id=0:添加；!=0:修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResp updateRiskCompany(TmanageLoginAccount admin) {
        log.debug("修改风控合作商");
        if (!StringUtils.isEmpty(admin.getLoginPassword()) && !admin.getLoginPassword().equals("******")) {
            admin.setLoginPassword(Digests.entryptPassword(admin.getLoginPassword()));
        }
        TmanageLoginAccount oldAdmin = tmanageLoginAccountService.selectOne(new EntityWrapper<TmanageLoginAccount>()
                .eq("login_name", admin.getLoginName())
        );//该账号account_name 使用人
        String flag = "no";
        if (admin.getId() != null && !admin.getId().toString().equals("0")) {
            TmanageLoginAccount old;
            if (oldAdmin != null) {//该用户名存在的情况
                if (!oldAdmin.getId().equals(admin.getId())) {
                    return JsonResp.toFail("该登录名已被使用");
                }
                old = oldAdmin;//id一致代表账号使用人是当前登录人
            } else {
                old = tmanageLoginAccountService.selectById(admin.getId());//获取原用户信息 主要是原登录名
            }
            //该用户账号或者密码被修改或者状态被禁用的情况下剔出该用户在线状态 1-已停用
            if (admin.getAccountStatus() == 1 || !old.getLoginName().equals(admin.getLoginName()) || (!StringUtils.isEmpty(admin.getLoginPassword()) && !admin.getLoginPassword().equals("******")
                    && !old.getLoginPassword().equals(admin.getLoginPassword()))) {
                ShiroSecurityHelper.kickOutUser(old.getLoginName(), sessionDAO);//剔出
                TmanageLoginAccount adminIng = tmanageLoginAccountService.selectLoginIng();//当前登录用户
                if (adminIng.getId().toString().equals(old.getId().toString())) {//修改的是当前登录人的信息
                    Subject subject = SecurityUtils.getSubject();
                    subject.logout();
                    flag = "yes";
                }
            }
            tmanageLoginAccountService.updateById(admin);
        } else {
            if (oldAdmin != null) {//该用户名存在的情况
                return JsonResp.toFail("该登录名已被使用");
            }
            tmanageLoginAccountService.insert(admin);
        }
        return JsonResp.ok(flag, admin);
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    @RequestMapping(value = "/selectLoginIng")
    public JsonResp selectLoginIng(TmanageResource resource) {
        TmanageLoginAccount admin = tmanageLoginAccountService.selectLoginIng();
        if (admin == null) {
            return JsonResp.toFail(JsonCodeEnum.LOGIN_EXPIRED.getMessage(), JsonCodeEnum.LOGIN_EXPIRED.getCode());
        }
        Wrapper<TmanageResource> wrapper = new EntityWrapper<TmanageResource>();
        if (!StringUtils.isEmpty(resource.getResourceType())) {
            wrapper.eq("resource_type", resource.getResourceType());
        }
        wrapper.eq("resource_status", 0);
        List<TmanageResource> list = tmanageResourceService.selectList(wrapper
                .orderBy("resource_order", false)
                .orderBy("id", false));
        JSONArray jsonArray = tmanageResourceService.getChildren(list, 0L);

        Map<String,Object> map=new HashMap<String, Object>();
        map.put("admin",admin);
        map.put("resource",jsonArray);
        return JsonResp.ok(map);
    }

    /**
     * 列表查询
     *
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping(value = "/selectList")
    public JsonResp selectList(Integer limit, Integer page, TmanageLoginAccount admin) {
        Wrapper<TmanageLoginAccount> wrapper = new EntityWrapper<TmanageLoginAccount>()
                .like("login_name", admin.getLoginName());
        if (!StringUtils.isEmpty(admin.getAccountStatus())) {
            wrapper.eq("account_status", admin.getAccountStatus());
        }
        if (!StringUtils.isEmpty(admin.getLoginName())) {
            wrapper.like("login_name", admin.getLoginName());
        }
        Page<TmanageLoginAccount> riskOperatorPage = tmanageLoginAccountService.selectPage(
                new Page<TmanageLoginAccount>(page, limit), wrapper.orderBy("id", false));
        return JsonResp.dataPageForAuto(riskOperatorPage);
    }


}
