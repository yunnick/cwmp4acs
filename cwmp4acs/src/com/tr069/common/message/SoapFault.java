package com.tr069.common.message;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.acs.po.FaultDetail;
import com.tr069.common.handler.HandlerFactory;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;

public class SoapFault extends SoapMessage<SoapFault, SoapFault>{

	private String faultCode;
	private String faultString;
	private FaultDetail detail;
	public String getFaultCode() {
		return faultCode;
	}
	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}
	public String getFaultString() {
		return faultString;
	}
	public void setFaultString(String faultString) {
		this.faultString = faultString;
	}
	public FaultDetail getDetail() {
		return detail;
	}
	public void setDetail(FaultDetail detail) {
		this.detail = detail;
	}
	@Override
	public SoapFault parseBody(String xml) {
		logger.info("begin parse SoapFault body");
		@SuppressWarnings("unchecked")
		RequestParser<SoapFault> parser = HandlerFactory.generateParser(MessageType.FAULT);
		SoapFault fault = parser.parseXml(xml);
		logger.info("end parse SoapFault body");
		return fault;
	}
	@Override
	public String buildBody(SoapFault responseMessage) {
		logger.info("begin build SoapFault body");
		@SuppressWarnings("unchecked")
		ResponseBuilder<SoapFault> builder = HandlerFactory.generateBuilder(MessageType.FAULT);
		String body = builder.build(responseMessage);
		logger.info("end build SoapFault body");
		return body;
	}
}
