package com.whenling.module.domain.json.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.whenling.module.domain.model.Node;
import com.whenling.module.domain.model.Tree;

/**
 * tree类型的解析器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:51:58
 */
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
			visitCheck(tree.getRoots(), tree.getChecked());
		}

		if (!Strings.isNullOrEmpty(tree.getIconProperty())) {
			visitIcon(tree.getRoots(), tree.getIconProperty());
		}

		if (tree.isExpandAll()) {
			expandAll(tree.getRoots());
		}

		serializer.write(tree.getRoots());
	}

	private void expandAll(List<? extends Node<?>> nodes) {
		if (nodes != null) {
			nodes.forEach((node) -> {
				node.setExpanded(true);
				expandAll(node.getChildren());
			});
		}
	}

	private void visitIcon(List<? extends Node<?>> nodes, String iconProperty) {
		if (nodes != null) {
			nodes.forEach((node) -> {
				try {
					node.setIcon(BeanUtils.getProperty(node.getData(), iconProperty));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				visitIcon(node.getChildren(), iconProperty);
			});
		}
	}

	private void visitCheck(List<? extends Node<?>> nodes, Set<?> checked) {
		if (nodes != null) {
			nodes.forEach((node) -> {
				node.setChecked(isChecked(node, checked));
				visitCheck(node.getChildren(), checked);
			});
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
