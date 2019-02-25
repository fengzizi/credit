package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TmanageLoginAccountMapper;
import com.credit.diversion.model.TmanageLoginAccount;
import com.credit.diversion.service.TmanageLoginAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TmanageLoginAccountServiceImpl
 * @后台登录帐号ServiceImpl
 * @version : Ver 1.0
 */
@Service
@Slf4j
public class TmanageLoginAccountServiceImpl extends ServiceImpl<TmanageLoginAccountMapper, TmanageLoginAccount> implements TmanageLoginAccountService {

    @Autowired
    private TmanageLoginAccountMapper tmanageLoginAccountMapper;

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public TmanageLoginAccount selectLoginIng() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        if (subject.getPrincipal() != null) {
            String code = subject.getPrincipal().toString();
            if (!code.equals("")) {
                TmanageLoginAccount admin = super.selectOne(new EntityWrapper<TmanageLoginAccount>()
                        .eq("login_name", code)
                );
                admin.setToken(session.getId().toString());
                return admin;
            }else{
                log.info("session过期需重新登录");
                subject.logout();
            }
        } else {
            log.info("session过期需重新登录");
            subject.logout();
        }
        return null;
    }
}
