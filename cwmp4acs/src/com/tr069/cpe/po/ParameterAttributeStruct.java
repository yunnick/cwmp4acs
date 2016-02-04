package com.tr069.cpe.po;

import java.util.List;

public class ParameterAttributeStruct {

	private String name;
	private int notification;
	private List<String> accessList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNotification() {
		return notification;
	}
	public void setNotification(int notification) {
		this.notification = notification;
	}
	public List<String> getAccessList() {
		return accessList;
	}
	public void setAccessList(List<String> accessList) {
		this.accessList = accessList;
	}
}
