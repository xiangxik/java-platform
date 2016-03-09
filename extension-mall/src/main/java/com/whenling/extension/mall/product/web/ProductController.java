package com.whenling.extension.mall.product.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whenling.extension.mall.product.model.Product;
import com.whenling.module.web.controller.EntityController;

@Controller
@RequestMapping("/admin/product")
public class ProductController extends EntityController<Product, Long> {
}
