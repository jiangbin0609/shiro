package com.shiro.demo3.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiro.demo3.mapper.SysUserMapper;
import com.shiro.demo3.pojo.SysPermission;
import com.shiro.demo3.pojo.SysUser;
import com.shiro.demo3.service.ISysUserService;

@Service
public class ISysUserServiceImpl implements ISysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public SysUser selectSysUserById(String sysUserId) {
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(sysUserId);
		return sysUser;
	}

	@Override
	public boolean addSysUser(SysUser sysUser) {
		int insert = sysUserMapper.insert(sysUser);
		if (insert > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<SysPermission> findSysPermissionList(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
