package com.tr069.cpe.handler;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tr069.acs.po.ParameterTypeStruct;
import com.tr069.acs.po.ParameterValueStruct;
import com.tr069.common.constants.CommonContants.DataStruct;
import com.tr069.common.constants.CommonContants.DataType;
import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.cpe.handler.SetParameterValuesHandler;
import com.tr069.cpe.message.request.SetParameterValues;
import com.tr069.cpe.message.response.SetParameterValuesResponse;

public class SetParameterValuesHandlerTest {
	public String xml;
	@Before
	public void init(){
		xml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">431</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:SetParameterValuesResponse>" +
				"      <Status> 1 </Status>" +
				"    </cwmp:SetParameterValuesResponse>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
	}
	
	@Test
	public void parseTest(){
		RequestParser<SetParameterValuesResponse> rp = new SetParameterValuesHandler();
		SetParameterValuesResponse msg = rp.parseXml(xml);
		Assert.assertEquals("status wrong", 1, msg.getStatus());
	}
	
	@Test
	public void buildTest(){
		ResponseBuilder<SetParameterValues> rb = new SetParameterValuesHandler();
		SetParameterValues ir = new SetParameterValues();
		List<ParameterValueStruct> parameterList = new ArrayList<ParameterValueStruct>();
		
		ParameterValueStruct pv1 = new ParameterValueStruct();
		pv1.setName("Device.ManagementServer.STUNServerAddress");
		pv1.setValue("60.29.150.126");
		ParameterTypeStruct pts1 = new ParameterTypeStruct();
		pts1.setDataStruct(DataStruct.TYPE);
		pts1.setDataStructNs(NameSpace.XSI_NAME_SPACE);
		pts1.setType(DataType.STRING);
		pts1.setTypeNs(NameSpace.XSD_NAME_SPACE);
		pv1.setParameterTypeStruct(pts1);
		parameterList.add(pv1);
		
		ParameterValueStruct pv2 = new ParameterValueStruct();
		pv2.setName("Device.ManagementServer.STUNEnable");
		pv2.setValue("true");
		ParameterTypeStruct pts2 = new ParameterTypeStruct();
		pts2.setDataStruct(DataStruct.TYPE);
		pts2.setDataStructNs(NameSpace.XSI_NAME_SPACE);
		pts2.setType(DataType.BOOLEAN);
		pts2.setTypeNs(NameSpace.XSD_NAME_SPACE);
		pv2.setParameterTypeStruct(pts2);
		parameterList.add(pv2);
		
		ir.setParameterList(parameterList);
		String soap = rb.build(ir);
		Assert.assertEquals("soap wrong", "<soap:Body>" +
				"<cwmp:SetParameterValues>" +
				"<ParameterList SOAP-ENC:arrayType=\"cwmp:ParameterValueStruct[2]\">" +
				"<ParameterValueStruct>" +
				"<Name>Device.ManagementServer.STUNServerAddress</Name>" +
				"<Value xsi:type=\"xsd:string\">60.29.150.126</Value>" +
				"</ParameterValueStruct>" +
				"<ParameterValueStruct>" +
				"<Name>Device.ManagementServer.STUNEnable</Name>" +
				"<Value xsi:type=\"xsd:boolean\">true</Value>" +
				"</ParameterValueStruct>" +
				"</ParameterList>" +
				"<ParameterKey/>" +
				"</cwmp:SetParameterValues>" +
				"</soap:Body>", soap);
	}
}
