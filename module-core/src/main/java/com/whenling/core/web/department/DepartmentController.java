package com.whenling.core.web.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.core.service.DepartmentService;
import com.whenling.core.web.department.vo.DepartmentNode;

@Controller
@RequestMapping("/admin/department")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public DepartmentNode[] getAllDepartment() {
		return DepartmentNode.converts(departmentService.findAll());
	}

}
