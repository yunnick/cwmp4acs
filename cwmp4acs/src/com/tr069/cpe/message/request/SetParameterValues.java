package com.tr069.cpe.message.request;

import java.util.List;

import com.tr069.acs.po.ParameterValueStruct;
import com.tr069.common.message.SoapMessage;
import com.tr069.cpe.message.response.SetParameterValuesResponse;

public class SetParameterValues extends SoapMessage<SetParameterValuesResponse, SetParameterValues>{

	private List<ParameterValueStruct> parameterList;
	private String parameterKey;
	public List<ParameterValueStruct> getParameterList() {
		return parameterList;
	}
	public void setParameterList(List<ParameterValueStruct> parameterList) {
		this.parameterList = parameterList;
	}
	public String getParameterKey() {
		return parameterKey;
	}
	public void setParameterKey(String parameterKey) {
		this.parameterKey = parameterKey;
	}
	@Override
	public SetParameterValuesResponse parseBody(String xml) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String buildBody(SetParameterValues responseMessage) {
		// TODO Auto-generated method stub
		return null;
	}
}
