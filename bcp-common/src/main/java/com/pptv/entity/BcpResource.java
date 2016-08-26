package com.pptv.entity;

@SuppressWarnings("serial")
public class BcpResource implements java.io.Serializable {
	private Integer classId;
    private String originalId;
    private String listJson;
    private String detailJson;
    private Integer downshelf;
    private Long sort;
    private Long updateAt;
    
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getOriginalId() {
		return originalId;
	}
	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}
	public String getListJson() {
		return listJson;
	}
	public void setListJson(String listJson) {
		this.listJson = listJson;
	}
	public int getDownshelf() {
		return downshelf;
	}
	public void setDownshelf(int downshelf) {
		this.downshelf = downshelf;
	}
	public long getSort() {
		return sort;
	}
	public void setSort(long sort) {
		this.sort = sort;
	}
	public long getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(long updateAt) {
		this.updateAt = updateAt;
	}	   
}