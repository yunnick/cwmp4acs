package com.tr069.cpe.message.response;

import org.apache.log4j.Logger;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.common.handler.HandlerFactory;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.message.SoapMessage;
import com.tr069.cpe.message.request.Reboot;

public class RebootResponse extends SoapMessage<RebootResponse, Reboot>{
	static Logger logger = Logger.getLogger(RebootResponse.class);
	@Override
	public RebootResponse parseBody(String xml) {
		logger.info("begin parse RebootResponse body");
		@SuppressWarnings("unchecked")
		RequestParser<RebootResponse> parser = HandlerFactory.generateParser(MessageType.REBOOT);
		RebootResponse rebootResponse = parser.parseXml(xml);
		logger.info("end parse RebootResponse body");
		return rebootResponse;
	}

	@Override
	public String buildBody(Reboot message) {
		logger.info("begin build Reboot body");
		@SuppressWarnings("unchecked")
		ResponseBuilder<Reboot> builder = HandlerFactory.generateBuilder(MessageType.REBOOT);
		String body = builder.build(message);
		logger.info("end build Reboot body");
		return body;
	}

}
