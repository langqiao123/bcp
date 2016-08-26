package com.pptv.dataoper.controller;

import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pptv.configuration.UrlProperties;
import com.pptv.dataoper.service.BcpResourceService;
import com.pptv.entity.BcpResource;

@RestController
@RequestMapping("/resource") 
@EnableConfigurationProperties({UrlProperties.class})  

public class BcpResourceController {
	
	@Autowired
	private UrlProperties properties;  
	
	@Autowired
	private BcpResourceService bcpResourceService;
	
	@RequestMapping("/list")
	public String index(){
		BcpResource resource = new BcpResource();
		String url = properties.getListApi();
		bcpResourceService.spliderEpgList();
		String json = null;
		try{
			json = getURL(url);	
			if(json == null){
				json = "{}";
				return json;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(json);
			String listJson = rootNode.path("vlist").toString();
			resource.setClassId(1);
			resource.setDetailJson("123");
			resource.setListJson(listJson);
			resource.setDownshelf(1);
			resource.setOriginalId("123");
			resource.setSort(123);
			resource.setUpdateAt(12333333);
			int falg = bcpResourceService.save(resource);
			System.out.println(falg);
			System.out.println(1);
			rootNode = objectMapper.readTree(listJson);
			
			Iterator<String> keys = rootNode.fieldNames();     
	        while(keys.hasNext()){    
	            String fieldName = keys.next();    
	            System.out.println(fieldName + ": " + rootNode.path(fieldName).toString());    
	        }  
	        System.out.println(objectMapper.writeValueAsString(rootNode)); 
		}catch(Exception e){
			System.out.println(e.getMessage()) ;
		}
		return json;
	}
	/** 
    * 针对于URL进行封装的Resource，可用来从URL获取资源内容 
    * @throws Exception 
    */  
   public String getURL(String url) throws Exception {  
      UrlResource resource = new UrlResource(url); 
      String json = null;
      if (resource.isReadable()) {  
    	  json = IOUtils.toString(resource.getInputStream());
      }  
      return json;
   }  
}
