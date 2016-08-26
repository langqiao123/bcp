package com.pptv.bcp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pptv.bcp.api.service.BcpResourceService;
import com.pptv.util.CallResult;

/**
 * 对外提供的接口
 * @author mingyangyang
 *
 */

@RestController
@RequestMapping("/video")
public class VideoController {
	
	@Autowired
	private BcpResourceService bcpResouceService;
	
	@RequestMapping("/{id}/list")
	public CallResult videoList(@PathVariable("id") Long id){
		return bcpResouceService.getBcpResourceList();
	}
}
