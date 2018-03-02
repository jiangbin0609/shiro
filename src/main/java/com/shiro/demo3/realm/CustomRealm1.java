package com.shiro.demo3.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.shiro.demo3.pojo.ActiveUser;
import com.shiro.demo3.pojo.SysPermission;
import com.shiro.demo3.pojo.SysUser;
import com.shiro.demo3.service.ISysUserService;

public class CustomRealm1 extends AuthorizingRealm {

	@Autowired
	private ISysUserService sysUserService;

	@Override
	public String getName() {
		return "customRealm";
	}

	// 支持什么类型的token
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取用户身份信息
		ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();
		String userId = activeUser.getUserid();

		// 查询权限
		List<SysPermission> permissions = null;
		try {
			permissions = new ArrayList<>();
			for (int i = 0; i < 5; i++) {

				SysPermission sysPermission = new SysPermission();
				sysPermission.setName("name" + i);
				sysPermission.setUrl("/kjadhsksh/hhjh"+1);
				permissions.add(sysPermission);
			}
			// permissions = sysUserService.findSysPermissionList(userId);
		} catch (Exception e) {
			System.out.println("权限查询错误");
		}

		// 构建授权信息
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		for (SysPermission sysPermission : permissions) {
			simpleAuthorizationInfo.addStringPermission(sysPermission.getPercode());
		}
		return simpleAuthorizationInfo;
	}

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 1.从token中获取用户身份信息
		String username = (String) token.getPrincipal();
		// 2.查询数据库
		SysUser sysUser = null;
		try {
			sysUser = sysUserService.selectSysUserById(username);

		} catch (Exception e) {
			System.out.println("用户名不存在！");
		}

		// 如果账号不存在
		if (sysUser == null) {
			return null;
		}

		List<SysPermission> menus = null;

		try {
			// menus = sysUserService.findSysPermissionList(sysUser.getId());
			menus = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				SysPermission sysPermission = new SysPermission();
				sysPermission.setName("name" + i);
				sysPermission.setUrl("/item/list.action");
				menus.add(sysPermission);
			}

		} catch (Exception e) {
			System.out.println("权限获取失败");
		}

		// 用户密码
		String password = sysUser.getPassword();
		// 盐
		String salt = sysUser.getSalt();

		// 构建用户身份信息
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid(sysUser.getId());
		activeUser.setUsername(sysUser.getUsername());
		activeUser.setUsercode(sysUser.getUsercode());
		activeUser.setMenus(menus);

		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, password,
				ByteSource.Util.bytes(salt), getName());
		return simpleAuthenticationInfo;
	}

	// 清除缓存
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}
}
