package com.tr069.cpe.message.request;

import java.util.List;

import com.tr069.cpe.po.SetParameterAttributesStruct;

public class SetParameterAttributes {
	
	private List<SetParameterAttributesStruct> parameterList;

	public List<SetParameterAttributesStruct> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<SetParameterAttributesStruct> parameterList) {
		this.parameterList = parameterList;
	}
}
