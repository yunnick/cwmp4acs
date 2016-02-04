package com.tr069.cpe.message.response;

import org.apache.log4j.Logger;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.common.handler.HandlerFactory;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.message.SoapMessage;
import com.tr069.cpe.message.request.SetParameterValues;


public class SetParameterValuesResponse extends SoapMessage<SetParameterValuesResponse, SetParameterValues>{
	static Logger logger = Logger.getLogger(SetParameterValuesResponse.class);

	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public SetParameterValuesResponse parseBody(String xml) {
		logger.info("begin parse SetParameterValuesResponse body");
		@SuppressWarnings("unchecked")
		RequestParser<SetParameterValuesResponse> parser = HandlerFactory.generateParser(MessageType.SET_PARAMETER_VALUES);
		SetParameterValuesResponse setParameterValuesResponse = parser.parseXml(xml);
		logger.info("end parse SetParameterValuesResponse body");
		return setParameterValuesResponse;
	}

	@Override
	public String buildBody(SetParameterValues message) {
		logger.info("begin build SetParameterValues body");
		@SuppressWarnings("unchecked")
		ResponseBuilder<SetParameterValues> builder = HandlerFactory.generateBuilder(MessageType.SET_PARAMETER_VALUES);
		String body = builder.build(message);
		logger.info("end build SetParameterValues body");
		return body;
	}
}
