package com.credit.diversion.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.credit.diversion.model.TmanageLoginAccount;
import com.credit.diversion.service.TmanageLoginAccountService;
import com.credit.diversion.util.encryption.Encodes;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 管理员的认证,角色,权限控制
 */
public class AccountAuthorizationRealm extends AuthorizingRealm {

    @Autowired
    private TmanageLoginAccountService tmanageLoginAccountService;

    private String name = "AccountAuthorizationRealm";

    public String getName() {
        return name;
    }

    /**
     * 查询获得用户信息 AuthenticationToken 用于收集用户提交的身份（如用户名）及凭据（如密码
     * <p>
     * AuthenticationInfo有两个作用： 1、如果Realm 是AuthenticatingRealm
     * 子类，则提供给AuthenticatingRealm 内部使用
     * CredentialsMatcher进行凭据验证；（如果没有继承它需要在自己的Realm中自己实现验证）
     * 2、提供给SecurityManager来创建Subject（提供身份信息）
     *
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        TmanageLoginAccount admin = tmanageLoginAccountService.selectOne(new EntityWrapper<TmanageLoginAccount>()
                .eq("login_name", token.getUsername()));
        if (admin == null) {
            throw new UnknownAccountException();// 用户不存在
        } else if (StringUtils.isEmpty(admin.getLoginPassword())) {//未设置密码
            throw new NoSetPasswordException("请先设置密码");
        } else if (admin.getAccountStatus() == null || admin.getAccountStatus() != 0) {//账号被禁用
            throw new NoSetPasswordException("该账号已锁定");
        } else {
            byte[] salt = Encodes.decodeHex(admin.getLoginPassword().substring(0, 16));
            return new SimpleAuthenticationInfo(new Principal(admin, token.isMobileLogin()), admin.getLoginPassword(),
                    ByteSource.Util.bytes(salt), getName());
        }
    }

    @Override
    protected void checkPermission(Permission permission, AuthorizationInfo info) {
        authorizationValidate(permission);
        super.checkPermission(permission, info);
    }

    @Override
    protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermitted(permissions, info);
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        authorizationValidate(permission);
        return super.isPermitted(principals, permission);
    }

    @Override
    protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermittedAll(permissions, info);
    }

    /**
     * 授权验证方法
     *
     * @param permission
     */
    private void authorizationValidate(Permission permission) {
        // 模块授权预留接口
    }

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(Object principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清楚所有用户授权信息
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

    /**
     * 表示根据用户身份获取授权信息 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调�?.在配有缓存的情况下，只加载一�?.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // String loginName = (String)
        // principalCollection.getPrimaryPrincipal();
        // Admin admin = adminService.findByAdminName(loginName);
        // if(admin==null)
        // log.error(admin.toString());
        //
        // String[] strs = admin.getRoleid().split(",");
        // Set<String> roleNameList = Sets.newHashSet();
        // for (String str : strs) {
        // if(StringUtils.isNumeric(str)){
        // Roles roles = roleService.findShopRoleById(Integer.valueOf(str));
        // roleNameList.add(roles.getRoleAlias());
        // }
        // }

        // List<Roles> roleList = roleDao.getRoleByAcctId(account.getId());
        // Set<String> roleNameList = Sets.newHashSet();
        // for (Roles role : roleList) {
        // roleNameList.add(role.getRoleName());
        // }
        // List<Permission> permissionList =
        // permissionDao.getPermByRoleList(roleList);
        // Set<String> permNameList = Sets.newHashSet();`
        // for (Permission permission : permissionList) {
        // permNameList.add(permission.getPermName());
        // }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("admin");
        /*List<MenuClass> menuList = UserUtils.getMenuList();
        for (MenuClass menuClass : menuList) {
			// 循环菜单列表，将premission加入到权限信息中
			if (StringUtils.isNotEmpty(menuClass.getPermission())) {
				for (String premission : menuClass.getPermission().split(",")) {
					info.addStringPermission(premission);
				}
			}
		}*/
        return info;
    }

    /**
     * 授权用户信息
     */
    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id; // 编号
        private String loginName; // 登录名
        private String name; // 姓名
        private boolean mobileLogin; // 是否手机登录

        public Principal(TmanageLoginAccount admin, boolean mobileLogin) {
            this.id = admin.getId() + "";
            this.loginName = admin.getLoginName();
            this.name = admin.getUserName();
            this.mobileLogin = mobileLogin;
        }

        public String getId() {
            return id;
        }

        public String getLoginName() {
            return loginName;
        }

        public String getName() {
            return name;
        }

        public boolean isMobileLogin() {
            return mobileLogin;
        }

        public String toString() {
            return loginName;
        }

    }

}
