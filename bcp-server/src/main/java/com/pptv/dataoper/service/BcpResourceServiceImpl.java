package com.pptv.dataoper.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pptv.configuration.UrlProperties;
import com.pptv.dataoper.dao.BcpResourceDao;
import com.pptv.entity.BcpResource;
import com.pptv.util.CallResult;
import com.pptv.util.HttpUtil;

@Service
public class BcpResourceServiceImpl extends JdbcBaseServiceImpl<BcpResource> implements BcpResourceService{
	
	@Autowired
	private UrlProperties properties;  
	
	@Autowired
	private BcpResourceDao bcpResourceDao; 
	
	public CallResult spliderEpgList() {
		CallResult  result = new CallResult();
		try {
			//epg的list接口每页最大支持200条记录，需要分页获取，s:页码，c:每页大小
			JSONObject json_list_first = HttpUtil.httpGet(properties.getListApi()+"&s=1");
			int pages = json_list_first.getJSONObject("vlist").getInt("page_count");
			List<BcpResource> resourceList = new ArrayList<BcpResource>();
			for(int k = 1;k <= pages;k++){
				JSONObject json_list = new JSONObject();
				if(k == 1){
					json_list = json_list_first;
				}else{
					json_list = HttpUtil.httpGet(properties.getListApi()+"&s="+k);
				}
				JSONArray listArr = json_list.getJSONObject("vlist").getJSONArray("v");
				for(int i = 0;i < listArr.size();i++){
					BcpResource resource = new BcpResource();
					resource.setClassId(1);
					resource.setDetailJson(listArr.getJSONObject(i).toString());
					resource.setListJson(listArr.getJSONObject(i).toString());
					resource.setDownshelf(0);
					resource.setOriginalId("123");
					resource.setSort(System.currentTimeMillis());
					resource.setUpdateAt(System.currentTimeMillis());
					resourceList.add(resource);
				}
			}
			bcpResourceDao.batchSave(resourceList);
			//code:"0",操作成功
			result.setCode("0");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public CallResult getBcpResourceList() {
		CallResult result = new CallResult();
		List<BcpResource> bcpResourceList = bcpResourceDao.findAll();
		result.setRows(bcpResourceList);
		return result;
	}
}
