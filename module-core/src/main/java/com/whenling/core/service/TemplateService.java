package com.whenling.core.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.comparator.DirectoryFileComparator;
import org.apache.commons.io.comparator.NameFileComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.whenling.core.model.TemplateFile;

@Service
public class TemplateService {

	@Autowired
	private ApplicationContext applicationContext;

	@Value("${template.location}")
	private String templateLocation;

	public List<TemplateFile> getFiles() {
		Resource resource = applicationContext.getResource(templateLocation);
		if (resource.exists()) {
			try {
				File dir = resource.getFile();
				return Lists.newArrayList(toTemplates(dir, ""));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	public File getFile(String path) {
		Resource resource = applicationContext.getResource(templateLocation);
		if (resource.exists()) {
			try {
				File dir = resource.getFile();
				File file = new File(dir, path);
				if (file.exists() && file.isFile()) {
					return file;
				}
				return null;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	private TemplateFile[] toTemplates(File dir, String parentPath) {
		List<TemplateFile> list = new ArrayList<>();
		File[] files = dir.listFiles();

		Arrays.sort(files, NameFileComparator.NAME_COMPARATOR);
		Arrays.sort(files, DirectoryFileComparator.DIRECTORY_COMPARATOR);

		for (File file : files) {
			TemplateFile templateFile = new TemplateFile();
			templateFile.setText(file.getName());
			String path = parentPath + "/" + file.getName();
			templateFile.setPath(path);
			if (file.isDirectory()) {
				templateFile.setChildren(toTemplates(file, path));
				templateFile.setIconCls("Folder");
			} else {
				String extension = FilenameUtils.getExtension(file.getName());
				if ("html".equals(extension)) {
					templateFile.setIconCls("Html");
				} else if ("js".equals(extension)) {
					templateFile.setIconCls("Script");
				} else if ("css".equals(extension)) {
					templateFile.setIconCls("Css");
				} else {
					templateFile.setIconCls("Page");
				}
			}
			list.add(templateFile);
		}

		return Iterables.toArray(list, TemplateFile.class);
	}

}
