package com.tr069.cpe.handler;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.cpe.handler.GetParameterNamesHandler;
import com.tr069.cpe.message.request.GetParameterNames;
import com.tr069.cpe.message.response.GetParameterNamesResponse;

public class GetParameterNamesHandlerTest {

	public String xml;
	@Before
	public void init(){
		xml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">429</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:GetParameterNamesResponse>" +
				"      <ParameterList soap:arrayType=\"cwmp:ParameterInfoStruct[2]\">" +
				"        <ParameterInfoStruct>" +
				"          <Name xsi:type=\"xsd:string(256)\">Device.LAN.MACAddress</Name>" +
				"          <Writable xsi:type=\"xsd:boolean\">true</Writable>" +
				"        </ParameterInfoStruct>" +
				"        <ParameterInfoStruct>" +
				"          <Name xsi:type=\"xsd:string(256)\">Device.X_CU_STB.AuthServiceInfo.UserID</Name>" +
				"          <Writable xsi:type=\"xsd:boolean\">false</Writable>" +
				"        </ParameterInfoStruct>" +
				"      </ParameterList>" +
				"    </cwmp:GetParameterNamesResponse>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
	}
	
	@Test
	public void parseTest(){
		RequestParser<GetParameterNamesResponse> rp = new GetParameterNamesHandler();
		GetParameterNamesResponse msg = rp.parseXml(xml);
		Assert.assertEquals("size wrong", 2, msg.getParameterList().size());
		Assert.assertEquals("name wrong", "Device.LAN.MACAddress", msg.getParameterList().get(0).getName());
		Assert.assertEquals("writable wrong", true, msg.getParameterList().get(0).isWritable());
		Assert.assertEquals("name wrong", "Device.X_CU_STB.AuthServiceInfo.UserID", msg.getParameterList().get(1).getName());
		Assert.assertEquals("writable wrong", false, msg.getParameterList().get(1).isWritable());
	}
	
	@Test
	public void buildTest(){
		ResponseBuilder<GetParameterNames> rb = new GetParameterNamesHandler();
		GetParameterNames ir = new GetParameterNames();
		
		ir.setNextLevel(false);
		ir.setParameterPath("Device.X_CU_STB.AuthServiceInfo");
		
		String soap = rb.build(ir);
		Assert.assertEquals("soap wrong", "<soap:Body>" +
				"<cwmp:GetParameterNames>" +
				"<ParameterPath>Device.X_CU_STB.AuthServiceInfo</ParameterPath>" +
				"<NextLevel>false</NextLevel>" +
				"</cwmp:GetParameterNames>" +
				"</soap:Body>", soap);
	}
}
