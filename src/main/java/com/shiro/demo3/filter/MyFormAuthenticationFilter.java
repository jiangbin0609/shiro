package com.shiro.demo3.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {

		// 校验验证码
		// 从session获取正确的验证码
		HttpSession session = ((HttpServletRequest) request).getSession();
		// 页面输入的验证码
		String randomcode = request.getParameter("randomcode");
		// 从session中取出验证码
		String validateCode = (String) session.getAttribute("validateCode");
		if (randomcode != null && validateCode != null) {
			if (!randomcode.equals(validateCode)) {
				// randomCodeError表示验证码错误
				request.setAttribute("shiroLoginFailure", "randomCodeError");
				// 拒绝访问，不再校验账号和密码
				return true;
			}
		}
		return super.onAccessDenied(request, response, mappedValue);
	}
}
