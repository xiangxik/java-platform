package com.whenling.module.web.extjs.json.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.google.common.collect.Iterables;
import com.whenling.module.domain.json.IncludesPropertyPreFilter;
import com.whenling.module.domain.model.Node;
import com.whenling.module.domain.model.Treeable;

/**
 * node类型的解析器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:51:38
 */
public class NodeSerializer implements ObjectSerializer {

	private SerializeConfig serializeConfig;

	public NodeSerializer(SerializeConfig serializeConfig) {
		this.serializeConfig = serializeConfig;
	}

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		SerializeWriter out = serializer.getWriter();
		if (object == null) {
			out.writeNull();
			return;
		}

		Node<?> node = (Node<?>) object;

		out.write('{');

		out.writeFieldName("children");
		serializer.write(node.getChildren());
		out.write(",");

		out.writeFieldName("leaf");
		out.write(node.getLeaf());
		out.write(",");

		if (node.getChecked() != null) {
			out.writeFieldName("checked");
			out.write(node.getChecked());
			out.write(",");
		}

		if (node.getExpanded() != null) {
			out.writeFieldName("expanded");
			out.write(node.getExpanded());
			out.write(",");
		}

		List<? extends SerializeFilter> serializeFilters = serializer.getPropertyPreFilters();

		Object data = node.getData();
		if (data != null && data instanceof Treeable && serializeFilters != null && serializeFilters.size() > 0) {
			boolean parentProcess = false;
			for (SerializeFilter filter : serializeFilters) {
				if (filter instanceof IncludesPropertyPreFilter) {
					Set<String> includes = ((IncludesPropertyPreFilter) filter).getIncludes();
					parentProcess = includes.contains("parent");
					if (parentProcess) {
						break;
					}
				}
			}
			if (parentProcess) {
				Object parent = ((Treeable<?>) data).getParent();
				if (parent != null) {
					out.writeFieldName("parent");
					out.write(JSON.toJSONString(parent, serializeConfig,
							Iterables.toArray(serializeFilters, SerializeFilter.class)));
					out.write(",");
				}
			}
		}

		String dataString = serializeFilters == null ? JSON.toJSONString(data, serializeConfig)
				: JSON.toJSONString(data, serializeConfig, Iterables.toArray(serializeFilters, SerializeFilter.class));
		dataString = StringUtils.substring(dataString, 1);
		out.write(dataString);
	}

}
