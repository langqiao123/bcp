package com.pptv.bcp.frontend.controller;

import org.springframework.ui.Model;

public class BaseController {
	public String render(String view,Model model){
		model.addAttribute("page", "/jsp/"+view+".jsp");  
		return "main";
	}
	public String render1(String view,Model model){
		model.addAttribute("page", "/jsp/"+view+".jsp");  
		return "layout";
	}
}
