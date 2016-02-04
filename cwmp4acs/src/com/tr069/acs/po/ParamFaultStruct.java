package com.tr069.acs.po;

public class ParamFaultStruct {

	private String parameterName;
	private String faultCode;
	private String FaultString;
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getFaultCode() {
		return faultCode;
	}
	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}
	public String getFaultString() {
		return FaultString;
	}
	public void setFaultString(String faultString) {
		FaultString = faultString;
	}
	
}
