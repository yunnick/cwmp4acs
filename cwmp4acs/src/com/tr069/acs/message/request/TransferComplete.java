package com.tr069.acs.message.request;

import org.apache.log4j.Logger;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.acs.message.response.TransferCompleteResponse;
import com.tr069.acs.po.FaultStruct;
import com.tr069.common.handler.HandlerFactory;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.message.SoapMessage;

public class TransferComplete extends SoapMessage<TransferComplete, TransferCompleteResponse>{
	Logger logger = Logger.getLogger(TransferComplete.class);
	
	private String commandKey;
	private FaultStruct faultStruct;
	private String startTime;
	private String completeTime;
	public String getCommandKey() {
		return commandKey;
	}
	public void setCommandKey(String commandKey) {
		this.commandKey = commandKey;
	}
	public FaultStruct getFaultStruct() {
		return faultStruct;
	}
	public void setFaultStruct(FaultStruct faultStruct) {
		this.faultStruct = faultStruct;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TransferComplete parseBody(String xml) {
		logger.info("begin parse TransferComplete body");
		RequestParser<TransferComplete> parser = HandlerFactory.generateParser(MessageType.TRANSFER_COMPLETE);
		TransferComplete transferComplete = parser.parseXml(xml);
		logger.info("end parse TransferComplete body");
		return transferComplete;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String buildBody(TransferCompleteResponse message) {
		logger.info("begin build TransferComplete body");
		ResponseBuilder<TransferCompleteResponse> builder = HandlerFactory.generateBuilder(MessageType.TRANSFER_COMPLETE);
		String body = builder.build(message);
		logger.info("end build TransferComplete body");
		return body;
	}
	
	
}
