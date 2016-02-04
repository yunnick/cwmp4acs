package com.tr069.cpe.message.response;

import java.util.List;

import org.apache.log4j.Logger;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.common.handler.HandlerFactory;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.message.SoapMessage;
import com.tr069.cpe.message.request.GetParameterNames;
import com.tr069.cpe.po.ParameterInfoStruct;

public class GetParameterNamesResponse extends SoapMessage<GetParameterNamesResponse, GetParameterNames>{

	static Logger logger = Logger.getLogger(GetParameterNamesResponse.class);
	private List<ParameterInfoStruct> parameterList;

	public List<ParameterInfoStruct> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<ParameterInfoStruct> parameterList) {
		this.parameterList = parameterList;
	}

	@Override
	public GetParameterNamesResponse parseBody(String xml) {
		logger.info("begin parse GetParameterNamesResponse body");
		@SuppressWarnings("unchecked")
		RequestParser<GetParameterNamesResponse> parser = HandlerFactory.generateParser(MessageType.GET_PARAMETER_NAMES);
		GetParameterNamesResponse getParameterNamesResponse = parser.parseXml(xml);
		logger.info("end parse GetParameterNamesResponse body");
		return getParameterNamesResponse;
	}

	@Override
	public String buildBody(GetParameterNames message) {
		logger.info("begin build GetParameterNames body");
		@SuppressWarnings("unchecked")
		ResponseBuilder<GetParameterNames> builder = HandlerFactory.generateBuilder(MessageType.GET_PARAMETER_NAMES);
		String body = builder.build(message);
		logger.info("end build GetParameterNames body");
		return body;
	}
}
