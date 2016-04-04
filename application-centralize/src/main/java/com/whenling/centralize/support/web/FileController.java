package com.whenling.centralize.support.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.beetl.core.BeetlKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.whenling.module.domain.model.Result;
import com.whenling.module.web.storage.StorageService;

@Controller
@RequestMapping("/file")
public class FileController {

	public static final String STORE_PATH_PATTERN = "/upload/${type}/${year}/${month}/${uuid}";

	@Autowired
	private StorageService storageService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Result upload(@RequestPart("file") MultipartFile[] parts) {
		List<String> paths = new ArrayList<>();
		if (parts != null && parts.length > 0) {
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH);
			int day = now.get(Calendar.DAY_OF_MONTH);

			for (MultipartFile part : parts) {
				Map<String, Object> paras = new HashMap<>();
				paras.put("now", now);
				paras.put("year", year);
				paras.put("month", month);
				paras.put("day", day);
				paras.put("part", part);
				paras.put("uuid", UUID.randomUUID().toString());
				String extension = FilenameUtils.getExtension(part.getOriginalFilename());
				String type = MoreObjects.firstNonNull(extension, "file");
				paras.put("type", type);
				String path = BeetlKit.render(STORE_PATH_PATTERN, paras);

				String sourcePath = path + (Strings.isNullOrEmpty(extension) ? "" : ("." + extension));
				try {
					storageService.store(sourcePath, part);
				} catch (IOException e) {
					e.printStackTrace();
				}

				paths.add(sourcePath);
			}
		}

		return Result.success().data(paths.size() == 1 ? paths.get(0) : paths);
	}

}
