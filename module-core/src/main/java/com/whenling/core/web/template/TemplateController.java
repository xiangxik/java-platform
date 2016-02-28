package com.whenling.core.web.template;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.whenling.core.model.TemplateFile;
import com.whenling.core.service.TemplateService;
import com.whenling.core.support.entity.Result;

@Controller
@RequestMapping("/admin/template")
public class TemplateController {

	@Autowired
	private TemplateService templateService;

	@RequestMapping(value = "/files", method = RequestMethod.GET)
	@ResponseBody
	public List<TemplateFile> getFiles() {
		return templateService.getFiles();
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String get(@RequestParam(name = "path") String path) {
		File file = templateService.getFile(path);
		if (file != null) {
			try {
				return Files.toString(file, Charset.forName("UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Result save(@RequestParam(name = "path") String path, @RequestParam(name = "content") String content) {
		if (Strings.isNullOrEmpty(path) || Strings.isNullOrEmpty(content)) {
			return Result.validateError(null);
		}

		File file = templateService.getFile(path);
		if (file == null) {
			return Result.failure();
		}

		try {
			Files.write(content, file, Charset.forName("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Result.success();
	}

}
