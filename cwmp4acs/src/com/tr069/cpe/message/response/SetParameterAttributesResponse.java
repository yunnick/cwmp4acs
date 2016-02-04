package com.tr069.cpe.message.response;

import org.apache.log4j.Logger;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.common.handler.HandlerFactory;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.message.SoapMessage;
import com.tr069.cpe.message.request.SetParameterAttributes;


public class SetParameterAttributesResponse extends SoapMessage<SetParameterAttributesResponse, SetParameterAttributes>{
	static Logger logger = Logger.getLogger(SetParameterAttributesResponse.class);
	@Override
	public SetParameterAttributesResponse parseBody(String xml) {
		logger.info("begin parse SetParameterAttributesResponse body");
		@SuppressWarnings("unchecked")
		RequestParser<SetParameterAttributesResponse> parser = HandlerFactory.generateParser(MessageType.SET_PARAMETER_ATTRIBUTES);
		SetParameterAttributesResponse setParameterAttributesResponse = parser.parseXml(xml);
		logger.info("end parse SetParameterAttributesResponse body");
		return setParameterAttributesResponse;
	}

	@Override
	public String buildBody(SetParameterAttributes message) {
		logger.info("begin build SetParameterAttributes body");
		@SuppressWarnings("unchecked")
		ResponseBuilder<SetParameterAttributes> builder = HandlerFactory.generateBuilder(MessageType.SET_PARAMETER_ATTRIBUTES);
		String body = builder.build(message);
		logger.info("end build SetParameterAttributes body");
		return body;
	}

}
