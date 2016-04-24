package com.whenling.plugin.payment.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whenling.plugin.payment.model.AlipayDirectPlugin;
import com.whenling.plugin.web.PluginController;

@Controller
@RequestMapping("/admin/payment/alipay_direct")
public class AlipayDirectController extends PluginController<AlipayDirectPlugin> {

}
