package com.shiro.demo3.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shiro.demo3.pojo.ActiveUser;
import com.shiro.demo3.pojo.SysUser;
import com.shiro.demo3.service.ISysUserService;

@Controller
public class UserController {

	@Autowired
	private ISysUserService sysUserService;
	
	@RequestMapping("/findSysuser")
	@ResponseBody
	public SysUser selectUser(String userId) {
		SysUser sysUser = sysUserService.selectSysUserById(userId);
		System.out.println("555");
		return sysUser;
	}
	
	
	@RequestMapping("/add")
	@ResponseBody
	public SysUser addSysUser(SysUser sysUser) {
		sysUser=new SysUser();
		sysUser.setId("jiangbin");
		sysUser.setLocked("0");
		sysUser.setPassword("456");
		sysUser.setSalt("jiang");
		sysUser.setUsercode("jiangbin");
		sysUser.setUsername("蒋彬");
		boolean flag = sysUserService.addSysUser(sysUser);
		return null;
	}
	
	@RequestMapping("/login")
	public String loginsubmit(Model model,HttpServletRequest request) {
		String ecxeptionClassName=(String)request.getAttribute("shiroLoginFailure");
		if(ecxeptionClassName!=null) {
			if(UnknownAccountException.class.getName().equals(ecxeptionClassName)) {
				System.out.println("账号不存在");
			}else if (IncorrectCredentialsException.class.getName().equals(ecxeptionClassName)) {
				System.out.println("密码错误");
			}else if ("randomCodeError".equals(ecxeptionClassName)) {
				System.out.println("验证码错误");
			}else {
				System.out.println("未知错误");
			}
		}
		return "login";
	}
	
	@RequestMapping("/first")
	public String first(Model model) {
		
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser)subject.getPrincipal();
		model.addAttribute("activeUser", activeUser);
		return "first";
	}
	
	
	
	@RequestMapping("/item/list.action")
	public String tojsp() {
		
		return "index";
	}
}
