package com.whenling.extension.cms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whenling.extension.cms.model.AdPosition;
import com.whenling.module.web.controller.EntityController;

@Controller
@RequestMapping("/admin/adPosition")
public class AdPositionController extends EntityController<AdPosition, Long> {

}
