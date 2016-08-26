package com.pptv.dataoper.service;

import org.springframework.transaction.annotation.Transactional;

import com.pptv.entity.BcpResource;
import com.pptv.util.CallResult;

public interface BcpResourceService extends JdbcBaseService<BcpResource>{
	
	//爬取epglist接口数据
	@Transactional
	public CallResult spliderEpgList();
	
	public CallResult getBcpResourceList();
}
