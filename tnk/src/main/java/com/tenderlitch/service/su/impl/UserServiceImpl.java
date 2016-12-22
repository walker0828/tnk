package com.tenderlitch.service.su.impl;

import org.springframework.stereotype.Service;
import com.tenderlitch.core.service.BaseCRUDService;
import com.tenderlitch.entity.su.User;
import com.tenderlitch.service.su.UserService;
import com.tenderlitch.core.annotation.ServiceLogger;

@Service
@ServiceLogger(serviceModel = "", serviceDesc = "")
public class UserServiceImpl extends BaseCRUDService<User> implements UserService
{
	
}
