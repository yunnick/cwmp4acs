package com.tr069.common.handler;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.acs.message.request.InformMessage;
import com.tr069.acs.message.request.TransferComplete;
import com.tr069.acs.message.response.InformResponse;
import com.tr069.acs.message.response.TransferCompleteResponse;
import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.message.SoapFault;
import com.tr069.common.message.SoapMessage;
import com.tr069.cpe.message.request.Download;
import com.tr069.cpe.message.request.FactoryReset;
import com.tr069.cpe.message.request.GetParameterAttributes;
import com.tr069.cpe.message.request.GetParameterNames;
import com.tr069.cpe.message.request.GetParameterValues;
import com.tr069.cpe.message.request.Reboot;
import com.tr069.cpe.message.request.SetParameterAttributes;
import com.tr069.cpe.message.request.SetParameterValues;
import com.tr069.cpe.message.request.Upload;
import com.tr069.cpe.message.response.DownloadResponse;
import com.tr069.cpe.message.response.FactoryResetResponse;
import com.tr069.cpe.message.response.GetParameterAttributesResponse;
import com.tr069.cpe.message.response.GetParameterNamesResponse;
import com.tr069.cpe.message.response.GetParameterValuesResponse;
import com.tr069.cpe.message.response.RebootResponse;
import com.tr069.cpe.message.response.SetParameterAttributesResponse;
import com.tr069.cpe.message.response.SetParameterValuesResponse;
import com.tr069.cpe.message.response.UploadResponse;
import com.ximpleware.AutoPilot;
import com.ximpleware.EOFException;
import com.ximpleware.EncodingException;
import com.ximpleware.EntityException;
import com.ximpleware.NavException;
import com.ximpleware.ParseException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

public class SoapMessageFactory {

	@SuppressWarnings("rawtypes")
	public static SoapMessage initSoap(String xml){
		/**
		 * 1、获取rpc报文的类型
		 */
		MessageType msgType = getRpcType(xml);
		/**
		 * 2、根据类型初始化
		 */
		return initSoap(msgType);
	}
	/**
	 * 预处理soap信息，主要用于匹配对应的泛型
	 * @return；
	 */
	@SuppressWarnings("rawtypes")
	public static SoapMessage initSoap(MessageType msgType){
		
		/**
		 * 1、初始化报文，匹配请求与返回的报文类型
		 */
		switch (msgType) {
		case INFORM:
			SoapMessage<InformMessage, InformResponse> inform = new InformMessage();
			return inform;
		case TRANSFER_COMPLETE:
			SoapMessage<TransferComplete, TransferCompleteResponse> trans = new TransferComplete();
			return trans;
		case ADD_OBJECT:
			return null;
		case DELETE_OBJECT:
			return null;
		case DOWNLOAD:
			SoapMessage<DownloadResponse, Download> download = new DownloadResponse();
			return download;
		case FACTORY_RESET:
			SoapMessage<FactoryResetResponse, FactoryReset> facReset = new FactoryResetResponse();
			return facReset;
		case GET_PARAMETER_ATTRIBUTES:
			SoapMessage<GetParameterAttributesResponse, GetParameterAttributes> getParameterAttributes = new GetParameterAttributesResponse();
			return getParameterAttributes;
		case GET_PARAMETER_NAMES:
			SoapMessage<GetParameterNamesResponse, GetParameterNames> getParameterNames = new GetParameterNamesResponse();
			return getParameterNames;
		case GET_PARAMETER_VALUES:
			SoapMessage<GetParameterValuesResponse, GetParameterValues> getParameterValues = new GetParameterValuesResponse();
			return getParameterValues;
		case REBOOT:
			SoapMessage<RebootResponse, Reboot> reboot = new RebootResponse();
			return reboot;
		case SET_PARAMETER_ATTRIBUTES:
			SoapMessage<SetParameterAttributesResponse, SetParameterAttributes> setParameterAttributes = new SetParameterAttributesResponse();
			return setParameterAttributes;
		case SET_PARAMETER_VALUES:
			SoapMessage<SetParameterValuesResponse, SetParameterValues> setParameterValues = new SetParameterValuesResponse();
			return setParameterValues;
		case UPLOAD:
			SoapMessage<UploadResponse, Upload> upload = new UploadResponse();
			return upload;
		case FAULT:
			SoapMessage<SoapFault, SoapFault> fault = new SoapFault();
			return fault;
		}
		return null;
	}
	
	/**
	 * 获取报文类型，位于请求报文的body内,如以下为GetParameterValuesResponse报文：
	 * <pre>
	 *	&lt;soap:Body>
     *  	&lt;cwmp:GetParameterValuesResponse>
	 *		 ...
     *		&lt;/cwmp:GetParameterValuesResponse>
  	 *	&lt;/soap:Body>
  	 *</pre>
	 * @return MessageType
	 */
	public static MessageType getRpcType(String xml){
   	 	try {
   	 		// instantiate the parser
   	 		VTDGen vg = new VTDGen();
   	 		AutoPilot ap = new AutoPilot();
   	 		//declare a namespace prefix
   	 		ap.declareXPathNameSpace("soapNs",NameSpace.ENVELOPE_NAME_SPACE.getDesc());
   	 		ap.declareXPathNameSpace("cwmpNs",NameSpace.CWMP_NAME_SPACE.getDesc());
   	 		// get to the SOAP header
			ap.selectXPath("/soapNs:Envelope/soapNs:Body/cwmpNs:*");
			
			vg.setDoc(xml.getBytes());
   		 	vg.parse(true);  // set namespace awareness to true 
   		 	VTDNav vn = vg.getNav();
   		 	ap.bind(vn); // bind calls resetXPath() so
   		 	
   		 	int index = -1;//the node index
   		 	/**
   		 	 * 获取rpc类型
   		 	 */
   		 	if((index = ap.evalXPath()) != -1){
				String tagName = vn.toString(index).trim();
				//去掉命名空间
				if(tagName.indexOf(":") != -1){
					tagName = tagName.split(":")[1];
				}
				return MessageType.getEnum(tagName);
   		 	}
		} catch (XPathParseException e) {
			e.printStackTrace();
		} catch (EncodingException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (XPathEvalException e) {
			e.printStackTrace();
		} catch (NavException e) {
			e.printStackTrace();
		}
		return null;
	}
}
