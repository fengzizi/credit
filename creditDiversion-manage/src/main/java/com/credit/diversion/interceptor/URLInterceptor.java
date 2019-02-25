package com.credit.diversion.interceptor;

import com.credit.diversion.util.commons.JsonCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.credit.diversion.util.commons.JsonResp;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;

/**
 * 所有web端未登录拦截
 * URL拦截
 */
public class URLInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Subject subject = SecurityUtils.getSubject();
        //登录信息已过期
        boolean isAuthenticated = subject.isAuthenticated();//用户身份认证登录
        boolean isRemembered = subject.isRemembered();//记住我登录
        if (isRemembered || isAuthenticated) {

        }
        //登录信息已过期
        if (subject.getPrincipal() == null) {
            return kickOut(response);
        }
        Session session=subject.getSession();
        if(session!=null){
            Object kickOutObj=session.getAttribute("kickOut");//剔出状态 kickOut=剔出 其余正常
            if(kickOutObj!=null){
                String kickOut=kickOutObj.toString();
                if(!StringUtils.isEmpty(kickOut)&&kickOut.equals("kickOut")){
                    subject.logout();
                    return kickOut(response);
                }
            }
        }
        return true;
    }

    /**
     * 未登录或登录过期操作
     * @param response
     * @return
     */
    public static boolean kickOut(HttpServletResponse response){
        JsonResp jsonResp = JsonResp.toFail(JsonCodeEnum.LOGIN_EXPIRED.getMessage(), JsonCodeEnum.LOGIN_EXPIRED.getCode());
        response.addHeader("content-type", "text/javascript");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        OutputStreamWriter out;
        try {
            out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, jsonResp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
