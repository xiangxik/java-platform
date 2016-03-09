package com.whenling.centralize.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public Map<String, Object> get() {
		Map<String, Object> appInfo = new HashMap<>();
		appInfo.put("name", "通用后台管理系统");
		appInfo.put("company", "广州当凌信息科技有限公司");
		appInfo.put("version", "1.0");
		return appInfo;
	}
}
