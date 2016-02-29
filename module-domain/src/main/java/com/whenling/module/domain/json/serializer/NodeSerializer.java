package com.whenling.module.domain.json.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.google.common.collect.Iterables;
import com.whenling.module.domain.model.Node;

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

		List<? extends SerializeFilter> serializeFilters = serializer.getPropertyPreFilters();
		String dataString = serializeFilters == null ? JSON.toJSONString(node.getData(), serializeConfig)
				: JSON.toJSONString(node.getData(), serializeConfig,
						Iterables.toArray(serializeFilters, SerializeFilter.class));
		dataString = StringUtils.substring(dataString, 1);
		out.write(dataString);
	}

}
