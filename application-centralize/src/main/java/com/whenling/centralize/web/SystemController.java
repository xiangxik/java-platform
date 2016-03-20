package com.whenling.centralize.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.centralize.MainSetting;

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

	@Autowired
	private MainSetting mainSetting;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> get() {
		Map<String, Object> appInfo = new HashMap<>();
		appInfo.put("name", mainSetting.getName());
		appInfo.put("company", mainSetting.getCompany());
		appInfo.put("version", mainSetting.getVersion());
		return appInfo;
	}
}
