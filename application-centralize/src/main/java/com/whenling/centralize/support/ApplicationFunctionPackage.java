package com.whenling.centralize.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whenling.centralize.model.User;
import com.whenling.centralize.service.UserService;
import com.whenling.module.web.view.FunctionPackage;

@Component("app")
public class ApplicationFunctionPackage implements FunctionPackage {

	@Autowired
	private UserService userService;

	public User getCurrentUser() {
		return userService.getCurrentUser();
	}

}
