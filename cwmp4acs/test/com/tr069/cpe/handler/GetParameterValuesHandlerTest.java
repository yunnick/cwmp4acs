package com.tr069.cpe.handler;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.cpe.handler.GetParameterValuesHandler;
import com.tr069.cpe.message.request.GetParameterValues;
import com.tr069.cpe.message.response.GetParameterValuesResponse;

public class GetParameterValuesHandlerTest {
	public String xml;
	@Before
	public void init(){
		xml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">429</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:GetParameterValuesResponse>" +
				"      <ParameterList soap:arrayType=\"cwmp:ParameterValueStruct[2]\">" +
				"        <ParameterValueStruct>" +
				"          <Name xsi:type=\"xsd:string(256)\">Device.LAN.MACAddress</Name>" +
				"          <Value xsi:type=\"xsd:string(18)\">bc:20:ba:04:ac:8a</Value>" +
				"        </ParameterValueStruct>" +
				"        <ParameterValueStruct>" +
				"          <Name xsi:type=\"xsd:string(256)\">Device.X_CU_STB.AuthServiceInfo.UserID</Name>" +
				"          <Value xsi:type=\"xsd:string(36)\">1000000000178</Value>" +
				"        </ParameterValueStruct>" +
				"      </ParameterList>" +
				"    </cwmp:GetParameterValuesResponse>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
	}
	
	@Test
	public void parseTest(){
		RequestParser<GetParameterValuesResponse> rp = new GetParameterValuesHandler();
		GetParameterValuesResponse msg = rp.parseXml(xml);
		Assert.assertEquals("size wrong", 2, msg.getParameterList().size());
		Assert.assertEquals("size wrong", "Device.LAN.MACAddress", msg.getParameterList().get(0).getName());
		Assert.assertEquals("size wrong", "bc:20:ba:04:ac:8a", msg.getParameterList().get(0).getValue());
	}
	
	@Test
	public void buildTest(){
		ResponseBuilder<GetParameterValues> rb = new GetParameterValuesHandler();
		GetParameterValues ir = new GetParameterValues();
		
		List<String> paramName = new ArrayList<String>();
		paramName.add("Device.LAN.MACAddress");
		paramName.add("Device.X_CU_STB.AuthServiceInfo.UserID");
		ir.setParameterName(paramName);
		
		String soap = rb.build(ir);
		Assert.assertEquals("soap wrong", "<soap:Body>" +
				"<cwmp:GetParameterValues>" +
				"<ParameterNames SOAP-ENC:arrayType=\"xsd:string[2]\">" +
				"<string>Device.LAN.MACAddress</string>" +
				"<string>Device.X_CU_STB.AuthServiceInfo.UserID</string>" +
				"</ParameterNames>" +
				"</cwmp:GetParameterValues>" +
				"</soap:Body>", soap);
	}
}
