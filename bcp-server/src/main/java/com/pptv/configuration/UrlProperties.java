package com.pptv.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(locations = "classpath:urls.properties")  
public class UrlProperties {  
     private String listApi;

	public String getListApi() {
		return listApi;
	}

	public void setListApi(String listApi)  {
		this.listApi = listApi;
	}

	
}  