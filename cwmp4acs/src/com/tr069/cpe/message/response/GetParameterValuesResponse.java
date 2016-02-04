package com.tr069.cpe.message.response;

import java.util.List;

import org.apache.log4j.Logger;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.acs.po.ParameterValueStruct;
import com.tr069.common.handler.HandlerFactory;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.message.SoapMessage;
import com.tr069.cpe.message.request.GetParameterValues;

public class GetParameterValuesResponse extends SoapMessage<GetParameterValuesResponse, GetParameterValues>{

	static Logger logger = Logger.getLogger(GetParameterValuesResponse.class);
	private List<ParameterValueStruct> parameterList;

	public List<ParameterValueStruct> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<ParameterValueStruct> parameterList) {
		this.parameterList = parameterList;
	}

	@Override
	public GetParameterValuesResponse parseBody(String xml) {
		logger.info("begin parse GetParameterValuesResponse body");
		@SuppressWarnings("unchecked")
		RequestParser<GetParameterValuesResponse> parser = HandlerFactory.generateParser(MessageType.GET_PARAMETER_VALUES);
		GetParameterValuesResponse getParameterValuesResponse = parser.parseXml(xml);
		logger.info("end parse GetParameterValuesResponse body");
		return getParameterValuesResponse;
	}

	@Override
	public String buildBody(GetParameterValues message) {
		logger.info("begin build GetParameterValues body");
		@SuppressWarnings("unchecked")
		ResponseBuilder<GetParameterValues> builder = HandlerFactory.generateBuilder(MessageType.GET_PARAMETER_VALUES);
		String body = builder.build(message);
		logger.info("end build GetParameterValues body");
		return body;
	}
}
