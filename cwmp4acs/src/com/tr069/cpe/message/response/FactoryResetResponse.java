package com.tr069.cpe.message.response;

import org.apache.log4j.Logger;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.common.handler.HandlerFactory;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.message.SoapMessage;
import com.tr069.cpe.message.request.FactoryReset;

public class FactoryResetResponse extends SoapMessage<FactoryResetResponse, FactoryReset>{
	Logger logger = Logger.getLogger(FactoryResetResponse.class);
	
	@Override
	public FactoryResetResponse parseBody(String xml) {
		logger.info("begin parse FactoryResetResponse body");
		@SuppressWarnings("unchecked")
		RequestParser<FactoryResetResponse> parser = HandlerFactory.generateParser(MessageType.FACTORY_RESET);
		FactoryResetResponse factoryResetResponse = parser.parseXml(xml);
		logger.info("end parse FactoryResetResponse body");
		return factoryResetResponse;
	}

	@Override
	public String buildBody(FactoryReset message) {
		logger.info("begin build FactoryReset body");
		@SuppressWarnings("unchecked")
		ResponseBuilder<FactoryReset> builder = HandlerFactory.generateBuilder(MessageType.FACTORY_RESET);
		String body = builder.build(message);
		logger.info("end build FactoryReset body");
		return body;
	}

}
