package com.whenling.module.domain.json.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.google.common.base.Objects;
import com.whenling.module.domain.model.Node;
import com.whenling.module.domain.model.Tree;

public class TreeSerializer implements ObjectSerializer {

	public static TreeSerializer instance = new TreeSerializer();
	
	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		SerializeWriter out = serializer.getWriter();
		if (object == null) {
			out.writeNull();
			return;
		}

		Tree<?> tree = (Tree<?>) object;
		if (tree.isCheckable()) {
			visit(tree.getRoots(), tree.getChecked());
		}

		serializer.write(tree.getRoots());
	}

	private void visit(List<? extends Node<?>> nodes, Set<?> checked) {
		if (nodes != null) {
			for (Node<?> node : nodes) {
				node.setChecked(isChecked(node, checked));
				visit(node.getChildren(), checked);
			}
		}
	}

	private boolean isChecked(Node<?> node, Set<?> checked) {
		if (checked != null) {
			for (Object c : checked) {
				if (Objects.equal(c, node.getData())) {
					return true;
				}
			}
		}

		return false;
	}

}
