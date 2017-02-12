package com.tenderlitch.controller.upc;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tenderlitch.core.action.BaseAction;
import com.tenderlitch.core.web.AjaxResponse;
import com.tenderlitch.entity.upc.UpcUser;
import com.tenderlitch.service.upc.UpcUserService;

/**
 * 维护系统登陆用户的控制器
 * @author tenderliTch
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/page/user")
public class UserAction extends BaseAction<UpcUser>{
	
	/**
	 * 跳转至用户主页面(ajax)
	 * @return
	 */
	@RequestMapping("/view")
	public String view(){
		//ModelAndView view=new ModelAndView("user");//返回/webapp/user.html
		//return view;
		return "user";
	}
	
	/**
	 * ajax请求user分页数据
	 * @throws Exception 
	 */
	@RequestMapping(value="/findByPage")
	@Override
	public AjaxResponse findByPage() throws Exception{
		return super.findByPage();
	}
	
	/**
	 * 新增或修改用户
	 */
	@RequestMapping(value="/save")
	@Override
	public void save(UpcUser user){
		super.save(user);
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping(value="/destroy")
	@Override
	public void destroy(UpcUser entity){
		super.destroy(entity);
	}
	
	/**
	 * 验证账号是否合格(目前只是验证了账号是否存在)
	 * @param account
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/accountValid")
	public String accountValid(String account){
		return upcUserService.isAccountExist(account)?ACCOUNT_VALIDATION_EXIST:ACCOUNT_VALIDATION_SUCCESS;
	}
	
	@Resource
	private UpcUserService upcUserService;
	
	/**
	 * 校验用户名是否存在时,给jquery.validation框架的返回值
	 */
	private static final String ACCOUNT_VALIDATION_SUCCESS="true";
	private static final String ACCOUNT_VALIDATION_EXIST="\"账户已存在,请更换\"";
}
