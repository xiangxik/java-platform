package com.whenling.core.web.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.core.web.system.vo.AppInfo;

@Controller
@RequestMapping("/appinfo")
public class SystemController {

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public AppInfo get() {
		AppInfo appInfo = new AppInfo();
		appInfo.setName("通用管理系统");
		appInfo.setCompany("当凌信息科技有限公司");
		appInfo.setVersion("1.0");
		return appInfo;
	}
}
