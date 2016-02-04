package com.tr069.acs.po;

import java.util.List;

public class FaultDetail {

	private String faultCode;
	private String faultString;
	private List<ParamFaultStruct> faultList;
	public String getFaultCode() {
		return faultCode;
	}
	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}
	public String getFaultString() {
		return faultString;
	}
	public void setFaultString(String faultString) {
		this.faultString = faultString;
	}
	public List<ParamFaultStruct> getFaultList() {
		return faultList;
	}
	public void setFaultList(List<ParamFaultStruct> faultList) {
		this.faultList = faultList;
	}
}
