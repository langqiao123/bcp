package com.pptv.util;

import java.io.Serializable;

public class Page implements Serializable{

	private static final long serialVersionUID = -6704048308961828660L;
	
	//每页显示条目数
	protected int itemsPerPage = 20;
	//当前页码
	protected int curPage = 1;
	//关键字
	protected String keywords;
	
	public int getItemsPerPage() {
		return itemsPerPage;
	}
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
