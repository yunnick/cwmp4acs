package com.tr069.common.message;

import org.apache.log4j.Logger;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.handler.HandlerFactory;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;


/**
 * 所有soap消息的父类，根据soap结构，分为header和body（对应不同的rpc包）
 *
 * @param <T>
 */
public abstract class SoapMessage<Req, Resp> {

	Logger logger = Logger.getLogger(this.getClass());
	//ENVELOPE命名空间
	static String envNs = NameSpace.ENVELOPE_NAME_SPACE.getResponseNs();
	//SOAP-ENCODING命名空间
	static String encNs = NameSpace.SOAP_ENC_NAME_SPACE.getResponseNs();
			
	static String root = "<" + envNs + ":Envelope " +
			"xmlns:" + envNs + "=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
			"xmlns:" + encNs + "=\"http://schemas.xmlsoap.org/soap/encoding/\" " +
			"xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\" " +
			"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
			"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
			"SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">";
	
	private SoapHeader header;
	public SoapHeader getHeader() {
		return header;
	}
	public void setHeader(SoapHeader header) {
		this.header = header;
	}
	private Req body;
	
	public Req getBody() {
		return body;
	}
	public void setBody(Req body) {
		this.body = body;
	}
	/**
	 * 
	 */
	public void parse(String xml){
		parserHeader(xml);
		this.setBody(parseBody(xml));
	}
	public String build(Resp responseMessage){
		String header = buildHeader(getHeader());
		String body = buildBody(responseMessage);
		return header + body;
	}
	/**
	 * 解析soap头消息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void parserHeader(String xml){
		logger.info("begin parse header");
		RequestParser<SoapHeader> parser = HandlerFactory.generateParser(MessageType.HEAD);
		SoapHeader header = parser.parseXml(xml);
		logger.info("end parse header");
		this.setHeader(header);
	}
	@SuppressWarnings("unchecked")
	public String buildHeader(SoapHeader header){
		logger.info("begin build header");
		ResponseBuilder<SoapHeader> builder = HandlerFactory.generateBuilder(MessageType.HEAD);
		String soapHeader = builder.build(header);
		logger.info("end build header");
		return soapHeader;
	}
	/**
	 * 解析soap消息，返回相关rpc对应的message
	 * @return
	 */
	public abstract Req parseBody(String xml);
	public abstract String buildBody(Resp responseMessage);
	
}
