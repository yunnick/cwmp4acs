package com.tr069.cpe.handler;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.tr069.common.handler.ResponseBuilder;
import com.tr069.cpe.handler.SetParameterAttributesHandler;
import com.tr069.cpe.message.request.SetParameterAttributes;
import com.tr069.cpe.po.SetParameterAttributesStruct;

public class SetParameterAttributesHandlerTest {
	@Test
	public void parseTest(){
	}
	
	@Test
	public void buildTest(){
		ResponseBuilder<SetParameterAttributes> rb = new SetParameterAttributesHandler();
		SetParameterAttributes ir = new SetParameterAttributes();
		
		List<SetParameterAttributesStruct> parameterList = new ArrayList<SetParameterAttributesStruct>();
		SetParameterAttributesStruct setParameterAttributesStruct = new SetParameterAttributesStruct();
		setParameterAttributesStruct.setName("Device.ManagementServer.NATDetected");
		setParameterAttributesStruct.setNotification(2);
		setParameterAttributesStruct.setNotificationChange(true);
		setParameterAttributesStruct.setAccessListChange(false);
		parameterList.add(setParameterAttributesStruct);
		ir.setParameterList(parameterList);
		
		String soap = rb.build(ir);
		Assert.assertEquals("soap wrong", "<soap:Body>" +
				"<cwmp:SetParameterAttributes><ParameterList SOAP-ENC:arrayType=\"cwmp:SetParameterAttributesStruct[1]\">" +
				"<SetParameterAttributesStruct><Name>Device.ManagementServer.NATDetected</Name>" +
				"<Notification>2</Notification><NotificationChange>true</NotificationChange>" +
				"<AccessListChange>false</AccessListChange><AccessList SOAP-ENC:arrayType=\"cwmp:string[0]\"/>" +
				"</SetParameterAttributesStruct></ParameterList>" +
				"</cwmp:SetParameterAttributes>" +
				"</soap:Body>", soap);
	}
}
