/**
 * 
 */
package com.tenderlitch.controller.upc;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tenderlitch.core.json.CustomObjectMapper;
import com.tenderlitch.core.util.WebUtil;
import com.tenderlitch.core.web.AjaxResponse;
import com.tenderlitch.core.web.LoginUtil;
import com.tenderlitch.entity.upc.UpcUser;
import com.tenderlitch.service.upc.UpcUserService;

/**
 * 管理登陆和权限的控制器
 * @author tenderliTch
 *
 */
@Controller
@Scope("prototype")
public class UpcAction {
	
	@RequestMapping(value="/login")
	public void login(@RequestParam("username") String username,@RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		UpcUser user=upcUserService.findUser(username, password);
		if(user!=null){//校验通过
			//在session中保存user信息
			LoginUtil.save2Session(request.getSession(),user);
			//如果是ajax请求,返回约定好的JSON串
			if(WebUtil.isRequestAjax(request)){
				customObjectMapper.writeValue(response.getWriter(), AjaxResponse.success());
			}else{
				response.sendRedirect("index.html");//将浏览器重定向到主页
			}
		}else{
			if(WebUtil.isRequestAjax(request)){
				customObjectMapper.writeValue(response.getWriter(), AjaxResponse.failure("账号不存在或密码错误"));
			}else{
				response.sendRedirect("login.html");//将浏览器地址重定向到登陆页
			}
		}
	}
	
	@Resource
	private UpcUserService upcUserService;
	
	@Resource
	private CustomObjectMapper customObjectMapper;
}
