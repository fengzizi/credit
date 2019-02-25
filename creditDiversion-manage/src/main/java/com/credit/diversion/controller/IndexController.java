package com.credit.diversion.controller;

import com.credit.diversion.util.commons.JsonResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version : Ver 1.0
 * @RiskCompanyController
 * @后台首页
 */
@RestController
@Transactional
@Slf4j
public class IndexController {

    /**
     * 获取当前登录用户
     *
     * @return
     */
    @RequestMapping(value = "/html")
    public JsonResp index() {
        Subject subject = SecurityUtils.getSubject();
        //登录信息已过期
        boolean isAuthenticated = subject.isAuthenticated();//用户身份认证登录
        boolean isRemembered = subject.isRemembered();//记住我登录
        boolean flag = false;
        if (isRemembered || isAuthenticated) {
            flag = true;
        }
        if (subject.getPrincipal() == null) {
            flag = false;
        }
        return JsonResp.ok(flag);
    }

}
