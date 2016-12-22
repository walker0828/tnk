package com.tenderlitch.controller.su;

import com.tenderlitch.core.action.BaseAction;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tenderlitch.entity.su.User;

@RestController
@Scope("prototype")
@RequestMapping("/su/user")
public class UserController extends BaseAction<User> {
	
}
