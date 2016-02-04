package com.tr069.cpe.handler;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.cpe.handler.DownloadHandler;
import com.tr069.cpe.message.request.Download;
import com.tr069.cpe.message.response.DownloadResponse;
import com.ximpleware.XMLByteOutputStream;

public class DownloadHandlerTest {
	public String xml;
	@Before
	public void init(){
		xml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">429</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:DownloadResponse>" +
				"      <Staus>0</Staus>" +
				"	   <StartTime>time1</StartTime>" +
				"      <CompleteTime>time2</CompleteTime>" +
				"    </cwmp:DownloadResponse>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
	}
	
	@Test
	public void parseTest(){
		RequestParser<DownloadResponse> rp = new DownloadHandler();
		DownloadResponse msg = rp.parseXml(xml);
		Assert.assertEquals("Staus wrong", 0, msg.getStatus());
		Assert.assertEquals("StartTime wrong", "time1", msg.getStartTime());
		Assert.assertEquals("CompleteTime wrong", "time2", msg.getCompleteTime());
		
		XMLByteOutputStream xbo = new XMLByteOutputStream(xml.length());
		try {
			xbo.write(xml.getBytes());
			System.out.println(xbo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void buildTest(){
		ResponseBuilder<Download> rb = new DownloadHandler();
		Download ir = new Download();
		
		ir.setCommandKey("key");
		ir.setFileType("ft");
		ir.setUrl("url");
		ir.setUsername("un");
		ir.setPassword("pwd");
		ir.setFileSize(1);
		ir.setTargetFileName("2");
		ir.setDelaySeconds(3);
		ir.setSuccessURL("s");
		ir.setFailureURL("f");
		String soap = rb.build(ir);
		Assert.assertEquals("soap wrong", "<soap:Body>" +
				"<cwmp:Download>" +
				"<CommandKey>key</CommandKey>" +
				"<FileType>ft</FileType>" +
				"<URL>url</URL>" +
				"<Username>un</Username>" +
				"<Password>pwd</Password>" +
				"<FileSize>1</FileSize>" +
				"<TargetFileName>2</TargetFileName>" +
				"<DelaySeconds>3</DelaySeconds>" +
				"<SuccessURL>s</SuccessURL>" +
				"<FailureURL>f</FailureURL>" +
				"</cwmp:Download>" +
				"</soap:Body>", soap);
	}
}
