package com.tenderlitch.controller.upc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping("/page/privilege")
public class PrivilegeAction {
	
	@RequestMapping("/main")
	public ModelAndView main(){
		ModelAndView view=new ModelAndView("page/privilege/privilege");
		return view;
	}
}
