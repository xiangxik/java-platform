package com.whenling.module.web.extjs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.whenling.module.domain.model.Result;
import com.whenling.module.web.multipart.MultipartService;
import com.whenling.module.web.multipart.model.FileEntity;
import com.whenling.module.web.multipart.service.FileService;

@Controller
@RequestMapping("${multipart.url}")
public class MultipartController {

	@Autowired
	private MultipartService multipartService;

	@Autowired
	private FileService fileService;

	@RequestMapping(method = RequestMethod.POST, params = "action=upload")
	@ResponseBody
	public Result upload(@RequestPart("file") MultipartFile[] parts) {
		List<ImageVo> data = new ArrayList<>();
		int total = 0;
		if (parts != null && parts.length > 0) {
			for (MultipartFile part : parts) {
				try {
					FileEntity fileEntity = multipartService.upload(part);
					ImageVo fileVo = new ImageVo();
					fileVo.setSrc(fileEntity.getStorePath());
					fileVo.setId(fileEntity.getId());
					data.add(fileVo);
					total++;
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		return Result.success().setData(data.size() == 1 ? data.get(0) : data).addExtraProperties("total", total)
				.addExtraProperties("errors", "");
	}

	@RequestMapping(method = RequestMethod.POST, params = "action=delete")
	@ResponseBody
	public Result delete(@RequestParam("id") FileEntity fileEntity) {
		fileService.delete(fileEntity);
		return Result.success();
	}

	@RequestMapping(method = RequestMethod.POST, params = "action=rotate")
	@ResponseBody
	public Result rotate(@RequestParam("id") FileEntity fileEntity) {

		return Result.success();
	}

	@RequestMapping(method = RequestMethod.POST, params = "action=resize")
	@ResponseBody
	public Result resize(@RequestParam("id") FileEntity fileEntity, Integer width, Integer height) {

		return Result.success();
	}

	@RequestMapping(method = RequestMethod.POST, params = "action=crop")
	@ResponseBody
	public Result crop(@RequestParam("id") FileEntity fileEntity, Double zoom, Integer width, Integer height,
			Integer offsetLeft, Integer offsetTop) {
		return Result.success();
	}

	@RequestMapping(method = RequestMethod.GET, params = "action=imagesList")
	@ResponseBody
	public Page<ImageVo> imagesList(Pageable pageable) {
		return fileService.findAll(pageable).map((fileEntity) -> {
			ImageVo imageVo = new ImageVo();
			imageVo.setId(fileEntity.getId());
			imageVo.setFullname(fileEntity.getFilename());
			imageVo.setName(fileEntity.getFilename());
			imageVo.setSrc(fileEntity.getStorePath());
			imageVo.setThumbSrc(fileEntity.getStorePath());
			return imageVo;
		});
	}

	public static class ImageVo {
		private Long id;
		private String fullname;
		private String name;
		private String src;
		private String thumbSrc;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getFullname() {
			return fullname;
		}

		public void setFullname(String fullname) {
			this.fullname = fullname;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSrc() {
			return src;
		}

		public void setSrc(String src) {
			this.src = src;
		}

		public String getThumbSrc() {
			return thumbSrc;
		}

		public void setThumbSrc(String thumbSrc) {
			this.thumbSrc = thumbSrc;
		}

	}
}
