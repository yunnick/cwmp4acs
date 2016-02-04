package com.tr069.cpe.message.response;

import java.util.List;

import org.apache.log4j.Logger;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.common.handler.HandlerFactory;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.message.SoapMessage;
import com.tr069.cpe.message.request.GetParameterAttributes;
import com.tr069.cpe.po.ParameterAttributeStruct;

public class GetParameterAttributesResponse extends SoapMessage<GetParameterAttributesResponse, GetParameterAttributes> {

	static Logger logger = Logger.getLogger(GetParameterAttributesResponse.class);
	private List<ParameterAttributeStruct> parameterList;

	public List<ParameterAttributeStruct> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<ParameterAttributeStruct> parameterList) {
		this.parameterList = parameterList;
	}

	@Override
	public GetParameterAttributesResponse parseBody(String xml) {
		logger.info("begin parse GetParameterAttributesResponse body");
		@SuppressWarnings("unchecked")
		RequestParser<GetParameterAttributesResponse> parser = HandlerFactory.generateParser(MessageType.GET_PARAMETER_ATTRIBUTES);
		GetParameterAttributesResponse getParameterAttributesResponse = parser.parseXml(xml);
		logger.info("end parse GetParameterAttributesResponse body");
		return getParameterAttributesResponse;
	}

	@Override
	public String buildBody(GetParameterAttributes message) {
		logger.info("begin build GetParameterAttributes body");
		@SuppressWarnings("unchecked")
		ResponseBuilder<GetParameterAttributes> builder = HandlerFactory.generateBuilder(MessageType.GET_PARAMETER_ATTRIBUTES);
		String body = builder.build(message);
		logger.info("end build GetParameterAttributes body");
		return body;
	}
}
