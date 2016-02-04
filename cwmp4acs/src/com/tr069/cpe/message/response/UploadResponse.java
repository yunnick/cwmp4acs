package com.tr069.cpe.message.response;

import org.apache.log4j.Logger;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.common.handler.HandlerFactory;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.message.SoapMessage;
import com.tr069.cpe.message.request.Upload;

public class UploadResponse extends SoapMessage<UploadResponse, Upload>{
	static Logger logger = Logger.getLogger(UploadResponse.class);

	private int status;
	private String startTime;
	private String completeTime;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	@Override
	public UploadResponse parseBody(String xml) {
		logger.info("begin parse UploadResponse body");
		@SuppressWarnings("unchecked")
		RequestParser<UploadResponse> parser = HandlerFactory.generateParser(MessageType.UPLOAD);
		UploadResponse uploadResponse = parser.parseXml(xml);
		logger.info("end parse UploadResponse body");
		return uploadResponse;
	}
	@Override
	public String buildBody(Upload message) {
		logger.info("begin build Upload body");
		@SuppressWarnings("unchecked")
		ResponseBuilder<Upload> builder = HandlerFactory.generateBuilder(MessageType.UPLOAD);
		String body = builder.build(message);
		logger.info("end build Upload body");
		return body;
	}
}
