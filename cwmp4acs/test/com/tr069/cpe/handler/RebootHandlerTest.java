package com.tr069.cpe.handler;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tr069.common.handler.ResponseBuilder;
import com.tr069.cpe.handler.RebootHandler;
import com.tr069.cpe.message.request.Reboot;

public class RebootHandlerTest {
	public String xml;
	@Before
	public void init(){
		xml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">429</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:RebootResponse>" +
				"    </cwmp:RebootResponse>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
	}
	
	@Test
	public void parseTest(){
	}
	
	@Test
	public void buildTest(){
		ResponseBuilder<Reboot> rb = new RebootHandler();
		Reboot ir = new Reboot();
		
		ir.setCommandKey("key");
		
		String soap = rb.build(ir);
		Assert.assertEquals("soap wrong", "<soap:Body>" +
				"<cwmp:Reboot>" +
				"<CommandKey>key</CommandKey>" +
				"</cwmp:Reboot>" +
				"</soap:Body>", soap);
	}
}
