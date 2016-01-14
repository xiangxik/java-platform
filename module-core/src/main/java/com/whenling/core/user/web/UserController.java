package com.whenling.core.user.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list() {
		return "/user/list";
	}
	
}
