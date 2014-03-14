package com.the3.base.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * BaseEntity.java
 *
 * @author Ethan Wong
 * @time 2014年3月14日下午8:23:18
 */
@Document
public class BaseEntity extends IdEntity {
	
	private Date createTime = new Date();//创建时间
	private Date modifyTime = new Date();//更新时间
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
}


