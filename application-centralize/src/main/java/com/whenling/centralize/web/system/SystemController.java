package com.whenling.centralize.web.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.centralize.web.system.vo.AppInfo;

/**
 * 系统控制器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:31:35
 */
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
