package com.tr069.cpe.po;

import java.util.List;

public class SetParameterAttributesStruct {

	private String name;
	private boolean notificationChange;
	private int notification;
	private boolean accessListChange;
	private List<String> accessList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isNotificationChange() {
		return notificationChange;
	}
	public void setNotificationChange(boolean notificationChange) {
		this.notificationChange = notificationChange;
	}
	public int getNotification() {
		return notification;
	}
	public void setNotification(int notification) {
		this.notification = notification;
	}
	public boolean isAccessListChange() {
		return accessListChange;
	}
	public void setAccessListChange(boolean accessListChange) {
		this.accessListChange = accessListChange;
	}
	public List<String> getAccessList() {
		return accessList;
	}
	public void setAccessList(List<String> accessList) {
		this.accessList = accessList;
	}
}
