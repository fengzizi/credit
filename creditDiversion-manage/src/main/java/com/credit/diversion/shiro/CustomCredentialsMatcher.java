package com.credit.diversion.shiro;

import com.credit.diversion.util.encryption.Digests;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * 自定义shiro验证时使用的加密算法
 */
public class CustomCredentialsMatcher extends HashedCredentialsMatcher {

    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Object accountCredentials = getCredentials(info); // 数据库密码
        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
        return Digests.validatePassword(String.valueOf(token.getPassword()), accountCredentials + "");
    }

}
