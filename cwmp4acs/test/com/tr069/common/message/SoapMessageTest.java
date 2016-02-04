package com.tr069.common.message;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.acs.message.request.InformMessage;
import com.tr069.acs.message.request.TransferComplete;
import com.tr069.acs.message.response.InformResponse;
import com.tr069.common.handler.SoapMessageFactory;
import com.tr069.common.message.SoapHeader;
import com.tr069.common.message.SoapMessage;
import com.tr069.cpe.message.response.DownloadResponse;
import com.tr069.cpe.message.response.FactoryResetResponse;
import com.tr069.cpe.message.response.GetParameterAttributesResponse;
import com.tr069.cpe.message.response.GetParameterNamesResponse;
import com.tr069.cpe.message.response.GetParameterValuesResponse;
import com.tr069.cpe.message.response.RebootResponse;
import com.tr069.cpe.message.response.SetParameterAttributesResponse;
import com.tr069.cpe.message.response.SetParameterValuesResponse;
import com.tr069.cpe.message.response.UploadResponse;

public class SoapMessageTest {

	public String informXml;
	public String transferCompleteXml;
	public String downLoadXml;
	public String factoryResetXml;
	public String getParameterAttributesXml;
	public String getParameterNamesXml;
	public String getParameterValuesXml;
	public String rebootXml;
	public String setParameterAttributesXml;
	public String setParameterValuesXml;
	public String uploadXml;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void buildTest(){
		SoapHeader header = new SoapHeader();
		header.setId("id001");
		/**
		 * 1、inform
		 */
		SoapMessage soap = SoapMessageFactory.initSoap(MessageType.INFORM);
		soap.setHeader(header);
		InformResponse infResp = new InformResponse();
		infResp.setMaxEnvelopes(1);
		String resp = soap.build(infResp);
		Assert.assertEquals("response wrong", "<soap:Header><cwmp:ID soap:mustUnderstand=\"1\">id001</cwmp:ID></soap:Header><soap:Body><cwmp:InformResponse><MaxEnvelopes>1</MaxEnvelopes></cwmp:InformResponse></soap:Body>", resp);
		/**
		 * 
		 */
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
//	@Test
	public void parseTest(){
		/**
		 * 1、inform
		 */
		SoapMessage soap = SoapMessageFactory.initSoap(informXml);
		soap.parse(informXml);
		Assert.assertEquals("id wrong", "1234", soap.getHeader().getId());
		Assert.assertEquals("HoldRequests wrong", "0", soap.getHeader().getHoldRequests());
		Assert.assertEquals("NoMoreRequests wrong", "1", soap.getHeader().getNoMoreRequests());
		Assert.assertEquals("body type wrong", true, soap.getBody() instanceof InformMessage);
		InformMessage inform = (InformMessage)soap.getBody();
		Assert.assertEquals("Manufacturer wrong", "STB", inform.getDeviceId().getManufacturer());
		Assert.assertEquals("OUI wrong", "00A900", inform.getDeviceId().getOui());
		Assert.assertEquals("ProductClass wrong", "S7200", inform.getDeviceId().getProductClass());
		Assert.assertEquals("SerialNumber wrong", "BC20BA05616C", inform.getDeviceId().getSerialNumber());
		Assert.assertEquals("event size wrong", 1, inform.getEvent().size());
		Assert.assertEquals("event code wrong", "1 BOOT", inform.getEvent().get(0).getEventCode().getValue());
		Assert.assertEquals("MaxEnvelopes wrong", 1, inform.getMaxEnvelopes());
		Assert.assertEquals("CurrentTime wrong", "2000-01-01T00:01:20", inform.getCurrentTime());
		Assert.assertEquals("RetryCount wrong", 0, inform.getRetryCount());
		Assert.assertEquals("param size wrong", 2, inform.getParameterList().size());
		Assert.assertEquals("param0 name wrong", "Device.ManagementServer.UDPConnectionRequestAddress", inform.getParameterList().get(0).getName());
		Assert.assertEquals("param0 value wrong", "Device.ManagementServer.UDPConnectionRequestAddress", inform.getParameterList().get(0).getValue());
		Assert.assertEquals("param1 name wrong", "Device.X_CU_STB.STBInfo.STBID", inform.getParameterList().get(1).getName());
		Assert.assertEquals("param1 value wrong", "00000100001500101151BC20BA04AC8A", inform.getParameterList().get(1).getValue());
		
		/**
		 * 2、TransferComplete
		 */
		SoapMessage transferSoap = SoapMessageFactory.initSoap(transferCompleteXml);
		transferSoap.parse(transferCompleteXml);
		Assert.assertEquals("id wrong", "0", transferSoap.getHeader().getId());
		Assert.assertEquals("body type wrong", true, transferSoap.getBody() instanceof TransferComplete);
		TransferComplete trans = (TransferComplete)transferSoap.getBody();
		Assert.assertEquals("key value wrong", "ee", trans.getCommandKey());
		Assert.assertEquals("fault code wrong", 0, trans.getFaultStruct().getFaultCode());
		Assert.assertEquals("fault string wrong", "", trans.getFaultStruct().getFaultString());
		Assert.assertEquals("start time wrong", "2000-01-01T00:08:27", trans.getStartTime());
		Assert.assertEquals("complete time wrong", "2000-01-01T00:08:27", trans.getCompleteTime());
		
		/**
		 * 3、downLoad
		 */
		SoapMessage downloadSoap = SoapMessageFactory.initSoap(downLoadXml);
		downloadSoap.parse(downLoadXml);
		Assert.assertEquals("id wrong", "429", downloadSoap.getHeader().getId());
		Assert.assertEquals("body type wrong", true, downloadSoap.getBody() instanceof DownloadResponse);
		DownloadResponse download = (DownloadResponse)downloadSoap.getBody();
		Assert.assertEquals("status wrong", 0, download.getStatus());
		Assert.assertEquals("start time wrong", "2000-01-01T00:08:27", download.getStartTime());
		Assert.assertEquals("CompleteTime wrong", "2000-01-01T00:09:27", download.getCompleteTime());
		
		/**
		 * 4、factoryReset
		 */
		SoapMessage factoryResetSoap = SoapMessageFactory.initSoap(factoryResetXml);
		factoryResetSoap.parse(factoryResetXml);
		Assert.assertEquals("id wrong", "231", factoryResetSoap.getHeader().getId());
		Assert.assertEquals("body type wrong", true, factoryResetSoap.getBody() instanceof FactoryResetResponse);
		FactoryResetResponse factoryReset = (FactoryResetResponse)factoryResetSoap.getBody();

		/**
		 * 5、GetParameterAttributes
		 */
		SoapMessage getParameterAttributesSoap = SoapMessageFactory.initSoap(getParameterAttributesXml);
		getParameterAttributesSoap.parse(getParameterAttributesXml);
		Assert.assertEquals("id wrong", "142", getParameterAttributesSoap.getHeader().getId());
		Assert.assertEquals("body type wrong", true, getParameterAttributesSoap.getBody() instanceof GetParameterAttributesResponse);
		GetParameterAttributesResponse getParameterAttributes = (GetParameterAttributesResponse)getParameterAttributesSoap.getBody();
		Assert.assertEquals("size wrong", 2, getParameterAttributes.getParameterList().size());
		Assert.assertEquals("name_0 wrong", "Device.ManagementServer.NATDetected", getParameterAttributes.getParameterList().get(0).getName());
		Assert.assertEquals("Notification_0 wrong", 2, getParameterAttributes.getParameterList().get(0).getNotification());
		Assert.assertEquals("AccessList_0 size wrong", 2, getParameterAttributes.getParameterList().get(0).getAccessList().size());
		Assert.assertEquals("AccessList_0_0 name wrong", "acc", getParameterAttributes.getParameterList().get(0).getAccessList().get(0));
		Assert.assertEquals("AccessList_0_1 name wrong", "ac", getParameterAttributes.getParameterList().get(0).getAccessList().get(1));
		Assert.assertEquals("name_1 wrong", "netgate.ManagementServer.NATDetected", getParameterAttributes.getParameterList().get(1).getName());
		Assert.assertEquals("Notification_1 wrong", 1, getParameterAttributes.getParameterList().get(1).getNotification());
		Assert.assertEquals("AccessList_1 size wrong", 1, getParameterAttributes.getParameterList().get(1).getAccessList().size());
		Assert.assertEquals("AccessList_1_0 name wrong", "accd", getParameterAttributes.getParameterList().get(1).getAccessList().get(0));

		/**
		 * 6、GetParameterAttributes
		 */
		SoapMessage getParameterNamesXmlSoap = SoapMessageFactory.initSoap(getParameterNamesXml);
		getParameterNamesXmlSoap.parse(getParameterNamesXml);
		Assert.assertEquals("id wrong", "409", getParameterNamesXmlSoap.getHeader().getId());
		Assert.assertEquals("body type wrong", true, getParameterNamesXmlSoap.getBody() instanceof GetParameterNamesResponse);
		GetParameterNamesResponse getParameterNamesXml = (GetParameterNamesResponse)getParameterNamesXmlSoap.getBody();
		Assert.assertEquals("size wrong", 2, getParameterNamesXml.getParameterList().size());
		Assert.assertEquals("name_0 wrong", "Device.LAN.MACAddress", getParameterNamesXml.getParameterList().get(0).getName());
		Assert.assertEquals("writable_0 wrong", true, getParameterNamesXml.getParameterList().get(0).isWritable());
		Assert.assertEquals("name_1 wrong", "Device.X_CU_STB.AuthServiceInfo.UserID", getParameterNamesXml.getParameterList().get(1).getName());
		Assert.assertEquals("writable_1 wrong", false, getParameterNamesXml.getParameterList().get(1).isWritable());
		
		/**
		 * 7、GetParameterValues
		 */
		SoapMessage getParameterValuesSoap = SoapMessageFactory.initSoap(getParameterValuesXml);
		getParameterValuesSoap.parse(getParameterValuesXml);
		Assert.assertEquals("id wrong", "129", getParameterValuesSoap.getHeader().getId());
		Assert.assertEquals("body type wrong", true, getParameterValuesSoap.getBody() instanceof GetParameterValuesResponse);
		GetParameterValuesResponse getParameterValuesXml = (GetParameterValuesResponse)getParameterValuesSoap.getBody();
		Assert.assertEquals("size wrong", 2, getParameterValuesXml.getParameterList().size());
		Assert.assertEquals("name_0 wrong", "Device.LAN.MACAddress", getParameterValuesXml.getParameterList().get(0).getName());
		Assert.assertEquals("value_0 wrong", "bc:20:ba:04:ac:8a", getParameterValuesXml.getParameterList().get(0).getValue());
		Assert.assertEquals("name_1 wrong", "Device.X_CU_STB.AuthServiceInfo.UserID", getParameterValuesXml.getParameterList().get(1).getName());
		Assert.assertEquals("value_1 wrong", "1000000000178", getParameterValuesXml.getParameterList().get(1).getValue());
		
		/**
		 * 8、GetParameterValues
		 */
		SoapMessage rebootSoap = SoapMessageFactory.initSoap(rebootXml);
		rebootSoap.parse(rebootXml);
		Assert.assertEquals("id wrong", "169", rebootSoap.getHeader().getId());
		Assert.assertEquals("body type wrong", true, rebootSoap.getBody() instanceof RebootResponse);
		RebootResponse rebootResponse = (RebootResponse)rebootSoap.getBody();
		
		/**
		 * 9、SetParameterAttributes
		 */
		SoapMessage setParameterAttributesSoap = SoapMessageFactory.initSoap(setParameterAttributesXml);
		setParameterAttributesSoap.parse(setParameterAttributesXml);
		Assert.assertEquals("id wrong", "109", setParameterAttributesSoap.getHeader().getId());
		Assert.assertEquals("body type wrong", true, setParameterAttributesSoap.getBody() instanceof SetParameterAttributesResponse);
		SetParameterAttributesResponse setParameterAttributes = (SetParameterAttributesResponse)setParameterAttributesSoap.getBody();
		
		/**
		 * 10、SetParameterValues
		 */
		SoapMessage setParameterValuesSoap = SoapMessageFactory.initSoap(setParameterValuesXml);
		setParameterValuesSoap.parse(setParameterValuesXml);
		Assert.assertEquals("id wrong", "431", setParameterValuesSoap.getHeader().getId());
		Assert.assertEquals("body type wrong", true, setParameterValuesSoap.getBody() instanceof SetParameterValuesResponse);
		SetParameterValuesResponse setParameterValues = (SetParameterValuesResponse)setParameterValuesSoap.getBody();

		/**
		 * 11、Upload
		 */
		SoapMessage uploadSoap = SoapMessageFactory.initSoap(uploadXml);
		uploadSoap.parse(uploadXml);
		Assert.assertEquals("id wrong", "389", uploadSoap.getHeader().getId());
		Assert.assertEquals("body type wrong", true, uploadSoap.getBody() instanceof UploadResponse);
		UploadResponse upload = (UploadResponse)uploadSoap.getBody();
		Assert.assertEquals("status wrong", 1, upload.getStatus());
		Assert.assertEquals("starttime wrong", "2000-01-01T00:08:27", upload.getStartTime());
		Assert.assertEquals("CompleteTime wrong", "2000-01-01T00:08:22", upload.getCompleteTime());
	}
	
	@Before
	public void init(){
		
		uploadXml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">389</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:UploadResponse>" +
				"      <Status>1</Status>" +
				"      <StartTime>2000-01-01T00:08:27</StartTime>" +
				"      <CompleteTime>2000-01-01T00:08:22</CompleteTime>" +
				"    </cwmp:UploadResponse>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
		
		setParameterValuesXml = "<?xml version=\"1.0\"?>" +
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
		
		setParameterAttributesXml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">109</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:SetParameterAttributesResponse>" +
				"    </cwmp:SetParameterAttributesResponse>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
		
		rebootXml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">169</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:RebootResponse>" +
				"    </cwmp:RebootResponse>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
		
		getParameterValuesXml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">129</cwmp:ID>" +
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
		
		getParameterNamesXml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">409</cwmp:ID>" +
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
		
		getParameterAttributesXml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">142</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:GetParameterAttributesResponse>" +
				"      <ParameterList soap:arrayType=\"cwmp:SetParameterAttributesStruct[1]\">"+
				"		  <ParameterAttributesStruct>" +
				"			 <Name>Device.ManagementServer.NATDetected</Name>" +
				"			 <Notification>2</Notification>" +
				"			 <AccessList soap:arrayType=\"cwmp:string[0]\">" +
				"				<string>acc</string>" +
				"				<string>ac</string>" +
				"			 </AccessList>"+
				"		  </ParameterAttributesStruct>" +
				"		  <ParameterAttributesStruct>" +
				"			 <Name>netgate.ManagementServer.NATDetected</Name>" +
				"			 <Notification>1</Notification>" +
				"			 <AccessList soap:arrayType=\"cwmp:string[0]\">" +
				"				<string>accd</string>" +
				"			 </AccessList>"+
				"		  </ParameterAttributesStruct>" +
				"     </ParameterList>" +
				"    </cwmp:GetParameterAttributesResponse>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
		
		factoryResetXml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">231</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:FactoryResetResponse>" +
				"    </cwmp:FactoryResetResponse>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
		
		downLoadXml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"  <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">429</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:DownloadResponse>" +
				"      <Staus>0</Staus>" +
				"	   <StartTime>2000-01-01T00:08:27</StartTime>" +
				"      <CompleteTime>2000-01-01T00:09:27</CompleteTime>" +
				"    </cwmp:DownloadResponse>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
		
		transferCompleteXml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				" <soap:Header>" +
				"    <cwmp:ID soap:mustUnderstand=\"1\">0</cwmp:ID>" +
				"  </soap:Header>" +
				"  <soap:Body>" +
				"    <cwmp:TransferComplete>" +
				"	      <CommandKey>ee</CommandKey>" +
				"		  <FaultStruct>" +
				"	         <FaultCode>0</FaultCode>" +
				"    	     <FaultString></FaultString>" +
				"		  </FaultStruct>" +
				"	      <StartTime xsi:type=\"xsd:dateTime\">2000-01-01T00:08:27</StartTime>" +
				"	      <CompleteTime xsi:type=\"xsd:dateTime\">2000-01-01T00:08:27</CompleteTime>" +
				"    </cwmp:TransferComplete>" +
				"  </soap:Body>" +
				"</soap:Envelope>";
		
		informXml = "<?xml version=\"1.0\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">" +
				"<soap:Header>" +
				"<cwmp:ID soap:mustUnderstand=\"1\">1234</cwmp:ID>" +
				"<cwmp:HoldRequests soap:mustUnderstand=\"1\">0</cwmp:HoldRequests>"+
				"<cwmp:NoMoreRequests>1</cwmp:NoMoreRequests>"+
				"</soap:Header>" +
				"<soap:Body>" +
				"<cwmp:Inform>" +
				"<DeviceId xsi:type=\"cwmp:DeviceIdStruct\">" +
				"<Manufacturer xsi:type=\"xsd:string(64)\" xsi:name=\"\">STB</Manufacturer>" +
				"<OUI xsi:type=\"xsd:string(6)\">00A900</OUI>" +
				"<ProductClass xsi:type=\"xsd:string(64)\">S7200</ProductClass>" +
				"<SerialNumber xsi:type=\"xsd:string(64)\">BC20BA05616C</SerialNumber>" +
				"</DeviceId>" +
				"<Event soap:arrayType=\"cwmp:EventStruct[1]\">" +
				"<EventStruct>" +
				"<EventCode xsi:type=\"xsd:string(64)\">1 BOOT</EventCode>" +
				"<CommandKey></CommandKey>" +
				"</EventStruct>" +
				"</Event>" +
				"<MaxEnvelopes xsi:type=\"xsd:unsigendInt\">1</MaxEnvelopes>" +
				"<CurrentTime xsi:type=\"xsd:dateTime\">2000-01-01T00:01:20</CurrentTime>" +
				"<RetryCount xsi:type=\"xsd:unsigendInt\">0</RetryCount>" +
				"<ParameterList soap:arrayType=\"cwmp:ParameterValueStruct[2]\">" +
				"<ParameterValueStruct>" +
				"<Name xsi:type=\"xsd:string(256)\">Device.ManagementServer.UDPConnectionRequestAddress</Name>" +
				"<Value xsi:type=\"xsd:string(256)\">Device.ManagementServer.UDPConnectionRequestAddress</Value>" +
				"</ParameterValueStruct>" +
				"<ParameterValueStruct>" +
				"<Name xsi:type=\"xsd:string(256)\">Device.X_CU_STB.STBInfo.STBID</Name>" +
				"<Value xsi:type=\"xsd:string(32)\">00000100001500101151BC20BA04AC8A</Value>" +
				"</ParameterValueStruct>" +
				"</ParameterList>" +
				"</cwmp:Inform>" +
				"</soap:Body>" +
				"</soap:Envelope>";
		
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(){
		/*******************************************
		 * 解析xml
		 *******************************************/
		/**
		 * 1、需要解析的XML
		 */
		String xml = "xml";
		/**
		 * 2、获取xml类型
		 */
		MessageType type = SoapMessageFactory.getRpcType(xml);
		/**
		 * 3、初始化soapMessage(最好先判断下xml类型)
		 */
		SoapMessage soap = SoapMessageFactory.initSoap(type);
		/**
		 * 4、解析xml，可通过header和body变量获取结果
		 */
		soap.parse(xml);
		
		/******************************************
		 * 构建xml
		 ******************************************/
		//返回的xml
		String response;
		/**
		 * 1、 构造头部
		 */
		SoapHeader head = new SoapHeader();
		head.setId("id");
		soap.setHeader(head);
		/**
		 * 2、根据类型构造body
		 */
		if(type.getRpc().equals("Inform")){
			//inform报文，相应报文为InformResponse
			InformResponse informResp = new InformResponse();
			informResp.setMaxEnvelopes(1);
			response = soap.build(informResp);
		}
		
	}
}
