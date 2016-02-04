package com.tr069.acs.handler;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tr069.acs.message.request.TransferComplete;
import com.tr069.acs.message.response.TransferCompleteResponse;
import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
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

public class TransferCompleteHandler implements RequestParser<TransferComplete>,
		ResponseBuilder<TransferCompleteResponse> {
	Logger logger = Logger.getLogger(TransferCompleteHandler.class);
	
	@Override
	public String build(TransferCompleteResponse message) {
		//ENVELOPE命名空间别名
		String envNs = NameSpace.ENVELOPE_NAME_SPACE.getResponseNs();
		
		StringBuffer body = new StringBuffer();
		body.append("<" + envNs + ":Body><cwmp:TransferCompleteResponse/></" + envNs + ":Body>");
		return body.toString();
	}

	@Override
	public TransferComplete parseXml(String xml) {
		TransferComplete transferComplete = null;
   	 	try {
   	 		// instantiate the parser
   	 		VTDGen vg = new VTDGen();
   	 		AutoPilot ap = new AutoPilot();
   	 		//declare a namespace prefix
   	 		ap.declareXPathNameSpace("soapNs",NameSpace.ENVELOPE_NAME_SPACE.getDesc());
   	 		ap.declareXPathNameSpace("cwmpNs",NameSpace.CWMP_NAME_SPACE.getDesc());
   	 		// get to the inform node
			ap.selectXPath("/soapNs:Envelope/soapNs:Body/cwmpNs:TransferComplete");
			
			vg.setDoc(xml.getBytes());
   		 	vg.parse(true);  // set namespace awareness to true 
   		 	VTDNav vn = vg.getNav();
   		 	ap.bind(vn); // bind calls resetXPath() so
   		 	int index = -1;//the node index
   		 	/**
   		 	 * 获取Inform报文的每个子节点
   		 	 */
   		 	if(ap.evalXPath() != -1){
   		 		transferComplete = new TransferComplete();
   		 		// to first child
   	   		 	if (vn.toElement(VTDNav.FC)){ 
   	   		 		do{
	   	   		 		/**
	   	   		 		 * 当前节点索引
	   	   		 		 */
	   	   		 		index = vn.getCurrentIndex();
	   	   		 		/**
	   	   		 		 * 节点名称
	   	   		 		 */
	   	   		 		String tagName = vn.toString(index);
	   	   		 		if(logger.isDebugEnabled()){
	   	   		 			logger.debug("transferComplete child tagName :"+tagName);
	   	   		 		}
	   	   		 		if(StringUtils.containsIgnoreCase(tagName, "CommandKey")){
		   	   		 		String value = VtdUtils.getText(vn);
	   	   		 			if(StringUtils.isNotBlank(value)){
	   	   		 				transferComplete.setCommandKey(value);
	   	   		 			}else{
	   	   		 				logger.error("CommandKey empty!");
	   	   		 			}
	   	   		 		}else if(StringUtils.containsIgnoreCase(tagName, "FaultStruct")){
	   	   		 			transferComplete.setFaultStruct(CwmpSoapUtils.parseFaultStruct(vn, ap));
	   	   		 		}else if(StringUtils.containsIgnoreCase(tagName, "StartTime")){
	   	   		 			String value = VtdUtils.getText(vn);
	   	   		 			transferComplete.setStartTime(value);
	   	   		 		}else if(StringUtils.containsIgnoreCase(tagName, "CompleteTime")){
	   	   		 			String value = VtdUtils.getText(vn);
	   	   		 			transferComplete.setCompleteTime(value);
	   	   		 		}
   	   		 		}while (vn.toElement(VTDNav.NS));
   	   		 	}
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
		return transferComplete;
	}

	
}
