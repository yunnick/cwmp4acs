package com.tr069.cpe.handler;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.cpe.handler.GetParameterAttributesHandler;
import com.tr069.cpe.message.request.GetParameterAttributes;
import com.tr069.cpe.message.response.GetParameterAttributesResponse;

public class GetParameterAttributesHandlerTest {
	public String xml;
	@Before
	public void init(){
		xml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">429</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:GetParameterAttributesResponse>" +
				"      <ParameterList soap:arrayType=\"cwmp:SetParameterAttributesStruct[1]\">"+
				"		  <ParameterAttributesStruct>" +
				"			 <Name>Device.ManagementServer.NATDetected</Name>" +
				"			 <Notification>2</Notification>" +
				"			 <AccessList soap:arrayType=\"cwmp:string[0]\">" +
				"				<string>acc</string>" +
				"			 </AccessList>"+
				"		  </ParameterAttributesStruct>" +
				"     </ParameterList>" +
				"    </cwmp:GetParameterAttributesResponse>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
	}
	
	@Test
	public void parseTest(){
		RequestParser<GetParameterAttributesResponse> rp = new GetParameterAttributesHandler();
		GetParameterAttributesResponse msg = rp.parseXml(xml);
		Assert.assertEquals("size wrong", 1, msg.getParameterList().size());
		Assert.assertEquals("name wrong", "Device.ManagementServer.NATDetected", msg.getParameterList().get(0).getName());
		Assert.assertEquals("Notification wrong", 2, msg.getParameterList().get(0).getNotification());
		Assert.assertEquals("size wrong", 1, msg.getParameterList().get(0).getAccessList().size());
	}
	
	@Test
	public void buildTest(){
		ResponseBuilder<GetParameterAttributes> rb = new GetParameterAttributesHandler();
		GetParameterAttributes ir = new GetParameterAttributes();
		
		List<String> parameterNames = new ArrayList<String>();
		parameterNames.add("sss");
		ir.setParameterNames(parameterNames);
		
		String soap = rb.build(ir);
		Assert.assertEquals("soap wrong", "<soap:Body>" +
				"<cwmp:GetParameterAttributes>" +
				"<ParameterNames SOAP-ENC:arrayType=\"xsd:string[1]\">" +
				"<string>sss</string>" +
				"</ParameterNames>" +
				"</cwmp:GetParameterAttributes>" +
				"</soap:Body>", soap);
	}
}
