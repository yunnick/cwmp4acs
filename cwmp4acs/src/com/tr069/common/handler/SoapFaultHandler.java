package com.tr069.common.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tr069.acs.po.FaultDetail;
import com.tr069.acs.po.ParamFaultStruct;
import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.message.SoapFault;
import com.tr069.common.util.CwmpSoapUtils;
import com.tr069.common.util.VtdUtils;
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

public class SoapFaultHandler implements RequestParser<SoapFault>, ResponseBuilder<SoapFault> {

	Logger logger = Logger.getLogger(SoapFaultHandler.class);
	@Override
	public String build(SoapFault message) {
		//ENVELOPE命名空间
		String envNs = NameSpace.ENVELOPE_NAME_SPACE.getResponseNs();
		/**
		 * 拼接body
		 */
		StringBuffer body = new StringBuffer();
		body.append("<" + envNs + ":Body>");
		body.append("<" + envNs + ":Fault>");
		/**
		 * faultcode
		 */
		body.append("<faultcode>Server</faultcode>");
		/**
		 * faultstring
		 */
		body.append("<faultstring>CWMP fault</faultstring>");
		/**
		 * detail
		 */
		body.append("<detail>");
		body.append("<cwmp:Fault>");
		body.append("<FaultCode>"+message.getDetail().getFaultCode()+"</FaultCode>");
		body.append("<FaultString>"+message.getDetail().getFaultString()+"</FaultString>");
		body.append("</cwmp:Fault>");
		body.append("</detail>");
		
		body.append("</" + envNs + ":Fault>");
		body.append("</" + envNs + ":Body>");
		return body.toString();
	}

	@Override
	public SoapFault parseXml(String xml) {
		SoapFault soapFault = null;
   	 	try {
   	 		// instantiate the parser
   	 		VTDGen vg = new VTDGen();
   	 		AutoPilot ap = new AutoPilot();
   	 		//declare a namespace prefix
   	 		ap.declareXPathNameSpace("soapNs",NameSpace.ENVELOPE_NAME_SPACE.getDesc());
   	 		ap.declareXPathNameSpace("cwmpNs",NameSpace.CWMP_NAME_SPACE.getDesc());
   	 		// get to the inform node
			ap.selectXPath("/soapNs:Envelope/soapNs:Body/soapNs:Fault");
			
			vg.setDoc(xml.getBytes());
   		 	vg.parse(true);  // set namespace awareness to true 
   		 	VTDNav vn = vg.getNav();
   		 	ap.bind(vn); // bind calls resetXPath() so
   		 	/**
   		 	 * 获取SetParameterValuesResponse报文的每个子节点
   		 	 */
   		 	if(ap.evalXPath() != -1){
   		 		soapFault = new SoapFault();
   		 		FaultDetail fd = null;
   		 		// to first child
   	   		 	if (vn.toElement(VTDNav.FC)){
   	   		 		do{
   	   		 			int index = vn.getCurrentIndex();
   	   		 			String tagName = vn.toString(index);
   	   		 			if(StringUtils.containsIgnoreCase(tagName, "faultcode")){
   	   		 				String value = VtdUtils.getText(vn);
   	   		 				soapFault.setFaultCode(CwmpSoapUtils.getString(value));
   	   		 			}else if(StringUtils.containsIgnoreCase(tagName, "faultstring")){
   	   		 				String value = VtdUtils.getText(vn);
	   		 				soapFault.setFaultString(value);
   	   		 			}else if(StringUtils.containsIgnoreCase(tagName, "detail")){
   	   		 				fd = new FaultDetail();
   	   		 				//选取<cwmp:Fault>所有子节点
   	   		 				ap.selectXPath("cwmpNs:Fault/child::*");
	   	   		 			int m = 0;
	   	   		 			List<ParamFaultStruct> faultList = new ArrayList<ParamFaultStruct>();
		   	 		 		while ((m = ap.evalXPath()) != -1){
		   	 					String paramTag = vn.toString(m);
		   	 					if(StringUtils.containsIgnoreCase(paramTag, "FaultCode")){
		   	 						int t = vn.getText();
		   	 						String paramValue = vn.toString(t).trim();
		   	 						fd.setFaultCode(paramValue);
		   	 					}else if(StringUtils.containsIgnoreCase(paramTag, "FaultString")){
		   	 						int t = vn.getText();
		   	 						String paramValue = vn.toString(t).trim();
	   	 							fd.setFaultString(paramValue);
		   	 					}else{
		   	 						//解析详细的错误列表（参照TR069协议V1.1，应该是对SetParameterValues方法的错误列表）
		   	 						logger.info("detail Fault tagName:" + paramTag);
		   	 						ParamFaultStruct pfs = null; 
		   	 						if(vn.toElement(VTDNav.FC)){
		   	 							pfs = new ParamFaultStruct();
		   	 							do{
		   	 								int faultIndex = vn.getCurrentIndex();
		   	 								String faultTagName = vn.toString(faultIndex);
		   	 								String faultTagValue = VtdUtils.getText(vn);
		   	 								if(StringUtils.containsIgnoreCase(faultTagName, "ParameterName")){
		   	 									pfs.setParameterName(faultTagValue);
		   	 								}else if(StringUtils.containsIgnoreCase(faultTagName, "FaultCode")){
		   	 									pfs.setFaultCode(faultTagValue);
		   	 								}else if(StringUtils.containsIgnoreCase(faultTagName, "FaultString")){
		   	 									pfs.setFaultString(faultTagValue);
		   	 								}else{
		   	 									logger.error("error field for CWMP fault detail :" + faultTagName);
		   	 								}
		   	 							}while(vn.toElement(VTDNav.NS));
		   	 						}
		   	 						vn.toElement(VTDNav.P);
		   	 						faultList.add(pfs);
		   	 					}
		   	 		 		}
		   	 		 		if(CwmpSoapUtils.isIterable(faultList)){
		   	 		 			fd.setFaultList(faultList);
		   	 		 		}
   	   		 			}
   	   		 		}while(vn.toElement(VTDNav.NS));
   	   		 	}
   	   		 	soapFault.setDetail(fd);
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
		} catch (NavException e) {
			e.printStackTrace();
		} catch (XPathEvalException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		return soapFault;
	}

}
