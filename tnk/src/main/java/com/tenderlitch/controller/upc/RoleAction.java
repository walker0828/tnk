package com.tenderlitch.controller.upc;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tenderlitch.core.action.BaseAction;
import com.tenderlitch.core.web.AjaxResponse;
import com.tenderlitch.entity.upc.UpcRole;
import com.tenderlitch.entity.upc.UpcUrlGroup;
import com.tenderlitch.service.upc.UpcUrlService;

/**
 * 维护角色的控制器
 * @author tenderliTch
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/page/role")
public class RoleAction extends BaseAction<UpcRole>{
	
	/**
	 * 跳转至角色主页面(ajax)
	 * @return
	 */
	@RequestMapping("/view")
	public String view(){
		return "role";
	}
	
	/**
	 * 获得所有需要管理权限的页面信息(用来填充角色维护界面的"页面"多选菜单)
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUrls")
	public List<UpcUrlGroup> getUrls(){
		return upcPageService.findAllGroup();
	}
	
	@RequestMapping("/save")
	@Override
	public void save(UpcRole upcRole){
		super.save(upcRole);
	}
	
	@RequestMapping("/destroy")
	@Override
	public void destroy(UpcRole upcRole) {
		super.destroy(upcRole);
	}

	@RequestMapping("/findByPage")
	@Override
	public AjaxResponse findByPage() throws Exception {
		return super.findByPage();
	}

	/**
	 * 页面数据接口
	 */
	@Resource
	private UpcUrlService upcPageService;
	
}
