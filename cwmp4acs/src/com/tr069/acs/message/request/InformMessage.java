package com.tr069.acs.message.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.acs.message.response.InformResponse;
import com.tr069.acs.po.DeviceIdStruct;
import com.tr069.acs.po.EventStruct;
import com.tr069.acs.po.ParameterValueStruct;
import com.tr069.common.handler.HandlerFactory;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.message.SoapMessage;

public class InformMessage extends SoapMessage<InformMessage, InformResponse>{
	static Logger logger = Logger.getLogger(InformMessage.class);
	
	private DeviceIdStruct deviceId;
	private List<EventStruct> event;
	private int maxEnvelopes;
	private String currentTime;
	private int retryCount;
	private List<ParameterValueStruct> parameterList;
	private InformResponse informResponse;
	
	public DeviceIdStruct getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(DeviceIdStruct deviceId) {
		this.deviceId = deviceId;
	}
	public List<EventStruct> getEvent() {
		return event;
	}
	public void setEvent(List<EventStruct> event) {
		this.event = event;
	}
	public int getMaxEnvelopes() {
		return maxEnvelopes;
	}
	public void setMaxEnvelopes(int maxEnvelopes) {
		this.maxEnvelopes = maxEnvelopes;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public int getRetryCount() {
		return retryCount;
	}
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	public List<ParameterValueStruct> getParameterList() {
		return parameterList;
	}
	public void setParameterList(List<ParameterValueStruct> parameterList) {
		this.parameterList = parameterList;
	}
	/**
	 * 添加单个参数
	 * @param Param
	 */
	public void putParam(ParameterValueStruct Param){
		List<ParameterValueStruct> parameterList = 
				(getParameterList() == null ? 
						new ArrayList<ParameterValueStruct>() : getParameterList());
		parameterList.add(Param);
		setParameterList(parameterList);
	}
	public InformResponse getInformResponse() {
		return informResponse;
	}
	public void setInformResponse(InformResponse informResponse) {
		this.informResponse = informResponse;
	}
	@Override
	public InformMessage parseBody(String xml) {
		logger.info("begin parse inform body");
		@SuppressWarnings("unchecked")
		RequestParser<InformMessage> parser = HandlerFactory.generateParser(MessageType.INFORM);
		InformMessage inform = parser.parseXml(xml);
		logger.info("end parse inform body");
		return inform;
	}
	@Override
	public String buildBody(InformResponse message) {
		logger.info("begin build inform body");
		@SuppressWarnings("unchecked")
		ResponseBuilder<InformResponse> builder = HandlerFactory.generateBuilder(MessageType.INFORM);
		String inform = builder.build(message);
		logger.info("end build inform body");
		return inform;
	}
}
