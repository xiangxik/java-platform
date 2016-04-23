package com.whenling.centralize.support.web.extjs.image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.BeetlKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.whenling.centralize.support.util.ImageHelper;
import com.whenling.module.domain.model.Result;
import com.whenling.module.web.storage.StorageService;

@Controller
@RequestMapping("/image")
public class ImageController {

	public static final String STORE_PATH_PATTERN = "/upload/image/${year}/${month}/${uuid}";

	@Autowired
	private StorageService storageService;

	@Autowired
	private ImageInfoService imageInfoService;

	@RequestMapping(method = RequestMethod.POST, params = "action=upload")
	@ResponseBody
	public Result upload(@RequestPart("file") MultipartFile[] parts) {
		List<ImageInfo> imageInfos = new ArrayList<>();
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
				String path = BeetlKit.render(STORE_PATH_PATTERN, paras);

				String extension = FilenameUtils.getExtension(part.getOriginalFilename());
				String sourcePath = path + "." + extension;
				String thumbnailPath = path + "-thumb." + extension;
				try {
					storageService.store(sourcePath, part);
				} catch (IOException e) {
					e.printStackTrace();
				}

				File sourceFile = storageService.getFile(sourcePath);

				ImageHelper.zoom(sourceFile, storageService.getFile(thumbnailPath), 64, 64);

				String source = storageService.getUrl(sourcePath);
				String thumbnail = storageService.getUrl(thumbnailPath);

				ImageInfo imageInfo = imageInfoService.newEntity();
				imageInfo.setName(part.getOriginalFilename());
				imageInfo.setSource(source);
				imageInfo.setThumbnail(thumbnail);

				imageInfos.add(imageInfoService.save(imageInfo));
			}
		}

		return Result.success().data(imageInfos.size() == 1 ? imageInfos.get(0) : imageInfos)
				.addExtraProperties("total", imageInfos.size()).addExtraProperties("errors", "");
	}

	@RequestMapping(method = RequestMethod.POST, params = "action=delete")
	@ResponseBody
	public Result delete(String path) {
		return Result.success();
	}

	@RequestMapping(method = RequestMethod.POST, params = "action=resize")
	@ResponseBody
	public Result resize(String path, Integer width, Integer height) {
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, params = "action=rotate")
	@ResponseBody
	public Result rotate(String path) {
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, params = "action=crop")
	@ResponseBody
	public Result crop(String path, Double zoom, Integer width, Integer height, Integer offsetLeft, Integer offsetTop) {
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, params = "action=imagesList")
	@ResponseBody
	public Page<ImageInfo> browser(String path, Pageable pageable) {
		return imageInfoService.findAll(pageable).map((info) -> {
			String baseName = FilenameUtils.getBaseName(info.getName());
			info.setName(StringUtils.abbreviateMiddle(baseName, "...", 12) + "."
					+ FilenameUtils.getExtension(info.getName()));
			return info;
		});
	}

}
