package com.whenling.module.web.multipart;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.whenling.module.domain.model.Result;

/**
 * 附件控制器
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午7:09:41
 */
@Controller
@RequestMapping("${multipart.url?:/multipart}")
public class MultipartController {

	@Autowired
	private MultipartService multipartService;

	@RequestMapping(method = RequestMethod.POST, params = "action=upload")
	@ResponseBody
	public Result upload(@RequestPart("file") MultipartFile[] parts) {
		String storePath = null;
		if (parts != null && parts.length > 0) {
			for (MultipartFile part : parts) {
				try {
					storePath = multipartService.upload(part);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return Result.success().setData(storePath);
	}
}
