package com.tr069.common.handler;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tr069.acs.po.FaultDetail;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.handler.SoapFaultHandler;
import com.tr069.common.message.SoapFault;

public class SoapFaultHandlerTest {

	public String xml;
	@Before
	public void init(){
		xml = "<?xml version=\"1.0\"?> " +
				"<soap:Envelope " +
				" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"" +
				" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"" +
				" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"<soap:Header>" +
				"<cwmp:ID soap:mustUnderstand=\"1\">1234</cwmp:ID>" +
				"</soap:Header>" +
				"<soap:Body>" +
				"<soap:Fault>" +
				"<faultcode>Client</faultcode>" +
				"<faultstring>CWMP fault</faultstring>" +
				"<detail>" +
				"<cwmp:Fault>" +
				"<FaultCode>9003</FaultCode>" +
				"<FaultString>Invalid arguments</FaultString>" +
				"<SetParameterValuesFault>" +
				"<ParameterName>" +
				"InternetGatewayDevice.Time.LocalTimeZone" +
				"</ParameterName>" +
				"<FaultCode>9012</FaultCode>" +
				"<FaultString>Not a valid time zone value</FaultString>" +
				"</SetParameterValuesFault>" +
				"<SetParameterValuesFault>" +
				"<ParameterName>" +
				"InternetGatewayDevice.Time.LocalTimeZoneName" +
				"</ParameterName>" +
				"<FaultCode>9012</FaultCode>" +
				"<FaultString>String too long</FaultString>" +
				"</SetParameterValuesFault>" +
				"</cwmp:Fault>" +
				"</detail>" +
				"</soap:Fault>" +
				"</soap:Body>" +
				"</soap:Envelope>";
	}
	
	@Test
	public void parseXmlTest(){
		RequestParser<SoapFault> rp = new SoapFaultHandler();
		SoapFault sf = rp.parseXml(xml);
		Assert.assertEquals("faultcode wrong", "Client", sf.getFaultCode());
		Assert.assertEquals("faultstring wrong", "CWMP fault", sf.getFaultString());
		Assert.assertEquals("detail faultcode wrong", "9003", sf.getDetail().getFaultCode());
		Assert.assertEquals("detail faultstring wrong", "Invalid arguments", sf.getDetail().getFaultString());
		Assert.assertEquals("detail size wrong", 2, sf.getDetail().getFaultList().size());
		Assert.assertEquals("detail0 faultcode wrong", "9012", sf.getDetail().getFaultList().get(0).getFaultCode());
		Assert.assertEquals("detail0 faultstring wrong", "Not a valid time zone value", sf.getDetail().getFaultList().get(0).getFaultString());
		Assert.assertEquals("detail0 paramname wrong", "InternetGatewayDevice.Time.LocalTimeZone", sf.getDetail().getFaultList().get(0).getParameterName());
		Assert.assertEquals("detail_1 faultcode wrong", "9012", sf.getDetail().getFaultList().get(1).getFaultCode());
		Assert.assertEquals("detail_1 faultstring wrong", "String too long", sf.getDetail().getFaultList().get(1).getFaultString());
		Assert.assertEquals("detail_1 paramname wrong", "InternetGatewayDevice.Time.LocalTimeZoneName", sf.getDetail().getFaultList().get(1).getParameterName());
	}
	@Test
	public void buildTest(){
		//构造fault，只需要添加FaultDetail参数即可。
		ResponseBuilder<SoapFault> rp = new SoapFaultHandler();
		SoapFault message = new SoapFault();
		FaultDetail fd = new FaultDetail();
		/**
		 * 1、第一个参数
		 */
		fd.setFaultCode("8002");
		/**
		 * 2、第二个参数
		 */
		fd.setFaultString("Method not supported");
		message.setDetail(fd);
		String fault = rp.build(message);
		Assert.assertEquals("fault wrong", "<soap:Body>" +
				"<soap:Fault>" +
				"<faultcode>Server</faultcode>" +
				"<faultstring>CWMP fault</faultstring>" +
				"<detail>" +
				"<cwmp:Fault>" +
				"<FaultCode>8002</FaultCode>" +
				"<FaultString>Method not supported</FaultString>" +
				"</cwmp:Fault>" +
				"</detail>" +
				"</soap:Fault>" +
				"</soap:Body>", fault);
	}
}
