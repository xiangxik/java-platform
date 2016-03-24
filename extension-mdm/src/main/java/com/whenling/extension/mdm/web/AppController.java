package com.whenling.extension.mdm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whenling.extension.mdm.model.App;
import com.whenling.module.web.controller.EntityController;

@Controller
@RequestMapping("/admin/app")
public class AppController extends EntityController<App, Long> {

}
