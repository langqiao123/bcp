package com.pptv.bcp.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class IndexController extends BaseController{

	@RequestMapping("/index")
	public String index(Model model){
		return render1("tree",model);
	}	
}
