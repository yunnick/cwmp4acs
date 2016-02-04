package com.tr069.cpe.message.response;

import org.apache.log4j.Logger;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.common.handler.HandlerFactory;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.message.SoapMessage;
import com.tr069.cpe.message.request.Download;

public class DownloadResponse extends SoapMessage<DownloadResponse, Download>{

	Logger logger = Logger.getLogger(DownloadResponse.class);
	
	private int Status;
	private String startTime;
	private String completeTime;
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
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
	public DownloadResponse parseBody(String xml) {
		logger.info("begin parse DownloadResponse body");
		@SuppressWarnings("unchecked")
		RequestParser<DownloadResponse> parser = HandlerFactory.generateParser(MessageType.DOWNLOAD);
		DownloadResponse downloadResponse = parser.parseXml(xml);
		logger.info("end parse DownloadResponse body");
		return downloadResponse;
	}
	@Override
	public String buildBody(Download message) {
		logger.info("begin build DownloadResponse body");
		@SuppressWarnings("unchecked")
		ResponseBuilder<Download> builder = HandlerFactory.generateBuilder(MessageType.DOWNLOAD);
		String body = builder.build(message);
		logger.info("end build DownloadResponse body");
		return body;
	}
}
