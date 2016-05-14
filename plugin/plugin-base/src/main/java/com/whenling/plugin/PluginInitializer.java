package com.whenling.plugin;

import javax.annotation.PostConstruct;

import org.beetl.core.Context;
import org.beetl.core.VirtualAttributeEval;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.whenling.plugin.model.Plugin;

@Component
public class PluginInitializer {

	@Autowired
	private BeetlGroupUtilConfiguration beetlConfig;

	@PostConstruct
	public void init() {
		beetlConfig.getGroupTemplate().registerVirtualAttributeEval(new VirtualAttributeEval() {

			@Override
			public Object eval(Object o, String attributeName, Context ctx) {
				Plugin plugin = (Plugin) o;
				return plugin.getAttribute(attributeName);
			}

			@Override
			public boolean isSupport(@SuppressWarnings("rawtypes") Class c, String attributeName) {
				return ClassUtils.isAssignable(Plugin.class, c);
			}
		});
	}

}
