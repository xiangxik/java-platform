package com.whenling.extension.cms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whenling.extension.cms.model.Ad;
import com.whenling.module.web.controller.EntityController;

@Controller
@RequestMapping("/admin/ad")
public class AdController extends EntityController<Ad, Long> {

}
