package com.whenling.core.support;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.whenling.core.support.integration.Application;
import com.whenling.core.support.integration.Module;

@Component
public class CoreModule extends Module {

	@Override
	public String getName() {
		return "系统管理";
	}

	@Override
	public void init(Application app) {
		app.addNavMenuSetResource(new ClassPathResource("/com/whenling/core/support/menu-system.xml"));
		app.addNavMenuSetResource(new ClassPathResource("/com/whenling/core/support/menu-log.xml"));
	}

}
