package com.shiro.service.test;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shiro.demo3.controller.UserController;
import com.shiro.demo3.pojo.SysUser;

public class ServiceTest {

	@Test
	public void test() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		UserController sysUserService = applicationContext.getBean(UserController.class);

		SysUser sysUser = sysUserService.selectUser("lisi");
		System.out.println(sysUser.getId());
	}

}
