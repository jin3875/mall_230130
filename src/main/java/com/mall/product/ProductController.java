package com.mall.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/product")
@Controller
public class ProductController {
	
	// 메인 화면
	@GetMapping("/product_main_view")
	public String productMainView(Model model) {
		model.addAttribute("viewName", "product/productMain");
		return "template/layout";
	}

}
