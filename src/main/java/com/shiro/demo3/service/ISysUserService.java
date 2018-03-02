package com.shiro.demo3.service;

import java.util.List;

import com.shiro.demo3.pojo.SysPermission;
import com.shiro.demo3.pojo.SysUser;

public interface ISysUserService {
	
	SysUser selectSysUserById(String sysUserId);
    
	boolean addSysUser(SysUser sysUser);
	
	List<SysPermission> findSysPermissionList(String userId);
}
