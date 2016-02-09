package com.whenling.core.web.department.vo;

import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.whenling.core.model.Department;
import com.whenling.core.support.extjs.api.data.NodeInterface;

public class DepartmentNode extends NodeInterface<DepartmentNode> {

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static DepartmentNode convert(Department department) {
		DepartmentNode node = new DepartmentNode();

		node.setText(department.getName());
		node.setCode(department.getCode());

		node.setChildren(converts(department.getChildren()));

		return node;
	}

	public static DepartmentNode[] converts(List<Department> departments) {
		if (departments == null || departments.size() == 0) {
			return null;
		}

		return Iterables.toArray(Lists.transform(departments, DepartmentNode::convert), DepartmentNode.class);
	}
}
