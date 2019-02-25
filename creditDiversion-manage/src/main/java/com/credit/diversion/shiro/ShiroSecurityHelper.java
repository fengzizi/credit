package com.credit.diversion.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.subject.support.DefaultWebSubjectContext;

import java.util.Collection;

/**
 * shiro管理工具
 *
 * @Project:risk
 * @PackageName:com.exchange.shiro
 * @Author:fujian
 * @CreationDate:2017年12月21日16:15
 */
@Slf4j
public class ShiroSecurityHelper {

    /**
     * 剔出用户
     *
     * @param username
     * @return
     */
    public static void kickOutUser(String username, SessionDAO sessionDAO) {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            if (null != session && StringUtils.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)), username)) {
                //设置会话的kickOut属性表示踢出了
                session.setAttribute("kickOut", "kickOut");
            }
        }
    }

    /**
     * 获取在线用户信息列表
     */
    public static void getActiveSessions(SessionDAO sessionDAO) {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            System.out.println("登录ip:" + session.getHost());
            System.out.println("登录用户" + session.getAttribute(DefaultWebSubjectContext.PRINCIPALS_SESSION_KEY));
            System.out.println("最后操作日期:" + session.getLastAccessTime());
        }
    }

    /**
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * @return
     */
    public static String getSessionId() {
        Session session = getSession();
        if (null == session) {
            return null;
        }
        return getSession().getId().toString();
    }
}
