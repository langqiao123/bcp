package com.pptv.dataoper.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InfoController {

	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@RequestMapping("/index")
	public String index(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.message);
		return "index";
		/*** 当返回index字符串，会自动去WEB-INF JSP路径寻找index.jsp */
	}

	/***
	 * @author shaoyongyang
	 * @since 2014-10-13 13:47:46 例子流程
	 * **/
	@RequestMapping(value = "example")
	public ModelAndView example(HttpServletRequest request) {
		return new ModelAndView("index.jsp");
	}
}