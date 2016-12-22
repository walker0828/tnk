package com.tenderlitch.controller.demo;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenderlitch.core.action.BaseAction;
import com.tenderlitch.entity.demo.UserDemo;

@RestController
@Scope("prototype")
@RequestMapping("/demo/user-demo")
public class UserDemoController extends BaseAction<UserDemo>
{
	
}


