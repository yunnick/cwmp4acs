package com.tr069.common.message;

public class SoapHeader {

	private String id;
	private String holdRequests;
	private String noMoreRequests;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHoldRequests() {
		return holdRequests;
	}
	public void setHoldRequests(String holdRequests) {
		this.holdRequests = holdRequests;
	}
	public String getNoMoreRequests() {
		return noMoreRequests;
	}
	public void setNoMoreRequests(String noMoreRequests) {
		this.noMoreRequests = noMoreRequests;
	}
}
