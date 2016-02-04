package com.tr069.acs.handler;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tr069.acs.handler.TransferCompleteHandler;
import com.tr069.acs.message.request.TransferComplete;
import com.tr069.acs.message.response.InformResponse;
import com.tr069.acs.message.response.TransferCompleteResponse;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;

public class TransferCompleteHandlerTest {

	public String xml;
	@Before
	public void init(){
		xml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">0</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:TransferComplete>" +
				"      <CommandKey>UPLOAD_FILE13488078290381</CommandKey>" +
				"      <FaultStruct>" +
				"        <FaultCode>0</FaultCode>" +
				"        <FaultString></FaultString>" +
				"      </FaultStruct>" +
				"      <StartTime xsi:type=\"xsd:dateTime\">2000-01-01T00:14:00</StartTime>" +
				"      <CompleteTime xsi:type=\"xsd:dateTime\">2000-01-01T00:15:00</CompleteTime>" +
				"    </cwmp:TransferComplete>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
	}
	
	@Test
	public void parseTest(){
		RequestParser<TransferComplete> rp = new TransferCompleteHandler();
		TransferComplete msg = rp.parseXml(xml);
		Assert.assertEquals("CommandKey wrong", "UPLOAD_FILE13488078290381", msg.getCommandKey());
		Assert.assertEquals("FaultCode wrong", 0, msg.getFaultStruct().getFaultCode());
		Assert.assertEquals("FaultString wrong", "", msg.getFaultStruct().getFaultString());
		Assert.assertEquals("StartTime wrong", "2000-01-01T00:14:00", msg.getStartTime());
		Assert.assertEquals("CompleteTime wrong", "2000-01-01T00:15:00", msg.getCompleteTime());
	}
	
	@Test
	public void buildTest(){
		ResponseBuilder<TransferCompleteResponse> rb = new TransferCompleteHandler();
		TransferCompleteResponse ir = new TransferCompleteResponse();
		String soap = rb.build(ir);
		Assert.assertEquals("soap wrong", "<soap:Body><cwmp:TransferCompleteResponse/></soap:Body>", soap);
	}
}
