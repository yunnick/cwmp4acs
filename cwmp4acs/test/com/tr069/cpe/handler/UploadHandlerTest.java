package com.tr069.cpe.handler;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.cpe.handler.UploadHandler;
import com.tr069.cpe.message.request.Upload;
import com.tr069.cpe.message.response.UploadResponse;

public class UploadHandlerTest {
	public String xml;
	@Before
	public void init(){
		xml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">429</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:UploadResponse>" +
				"      <Status>1</Status>" +
				"      <StartTime></StartTime>" +
				"      <CompleteTime>time2</CompleteTime>" +
				"    </cwmp:UploadResponse>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
	}
	
	@Test
	public void parseTest(){
		RequestParser<UploadResponse> rp = new UploadHandler();
		UploadResponse msg = rp.parseXml(xml);
		Assert.assertEquals("Staus wrong", 1, msg.getStatus());
		Assert.assertEquals("StartTime wrong", "", msg.getStartTime());
		Assert.assertEquals("CompleteTime wrong", "time2", msg.getCompleteTime());
	}
	
	@Test
	public void buildTest(){
		ResponseBuilder<Upload> rb = new UploadHandler();
		Upload ir = new Upload();
		
		ir.setCommandKey("UPLOAD_FILE13488078290381");
		ir.setFileType("3 CU SystemLog");
		ir.setUrl("http://61.181.23.193:9090/web/upload/log/");
		ir.setPassword("pwd");
		String soap = rb.build(ir);
		Assert.assertEquals("soap wrong", "<soap:Body>" +
				"<cwmp:Upload>" +
				"<CommandKey>UPLOAD_FILE13488078290381</CommandKey>" +
				"<FileType>3 CU SystemLog</FileType><URL>http://61.181.23.193:9090/web/upload/log/</URL>" +
				"<Username></Username>" +
				"<Password>pwd</Password>" +
				"<DelaySeconds>0</DelaySeconds>" +
				"</cwmp:Upload>" +
				"</soap:Body>", soap);
	}
}
