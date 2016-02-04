package com.tr069.acs.po;

/**
 * 参数结构
 *
 */
public class ParameterValueStruct {

	private String name;
	private String value;
	private ParameterTypeStruct parameterTypeStruct;
	
	public ParameterValueStruct() {
	}
	
	public ParameterValueStruct(String name, String value, ParameterTypeStruct parameterTypeStruct) {
		this.name = name;
		this.value = value;
		this.parameterTypeStruct = parameterTypeStruct;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public ParameterTypeStruct getParameterTypeStruct() {
		return parameterTypeStruct;
	}

	public void setParameterTypeStruct(ParameterTypeStruct parameterTypeStruct) {
		this.parameterTypeStruct = parameterTypeStruct;
	}
}
