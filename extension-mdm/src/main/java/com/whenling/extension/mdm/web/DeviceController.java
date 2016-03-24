package com.whenling.extension.mdm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whenling.extension.mdm.model.Device;
import com.whenling.module.web.controller.EntityController;

@Controller
@RequestMapping("/admin/device")
public class DeviceController extends EntityController<Device, Long> {

}
