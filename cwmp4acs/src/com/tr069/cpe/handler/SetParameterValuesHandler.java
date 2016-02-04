package com.tr069.cpe.handler;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.util.CwmpSoapUtils;
import com.tr069.common.util.VtdUtils;
import com.tr069.cpe.message.request.SetParameterValues;
import com.tr069.cpe.message.response.SetParameterValuesResponse;
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

public class SetParameterValuesHandler implements RequestParser<SetParameterValuesResponse>,
		ResponseBuilder<SetParameterValues> {

	Logger logger = Logger.getLogger(SetParameterValuesHandler.class);
	
	@Override
	public String build(SetParameterValues message) {
		//ENVELOPE命名空间
		String envNs = NameSpace.ENVELOPE_NAME_SPACE.getResponseNs();
		/**
		 * 拼接body
		 */
		StringBuffer body = new StringBuffer();
		body.append("<" + envNs + ":Body>");
		body.append("<cwmp:SetParameterValues>");
		/**
		 * ParameterList
		 */
		body.append(CwmpSoapUtils.buildParameterList(message.getParameterList()));
		/**
		 * ParameterKey
		 */
		if(StringUtils.isNotBlank(message.getParameterKey())){
			body.append("<ParameterKey>");
			body.append(message.getParameterKey());
			body.append("</ParameterKey>");
		}else{
			body.append("<ParameterKey/>");
		}
		
		body.append("</cwmp:SetParameterValues>");
		body.append("</" + envNs + ":Body>");
		return body.toString();
	}

	@Override
	public SetParameterValuesResponse parseXml(String xml) {
		SetParameterValuesResponse setParameterValuesResponse = null;
   	 	try {
   	 		// instantiate the parser
   	 		VTDGen vg = new VTDGen();
   	 		AutoPilot ap = new AutoPilot();
   	 		//declare a namespace prefix
   	 		ap.declareXPathNameSpace("soapNs",NameSpace.ENVELOPE_NAME_SPACE.getDesc());
   	 		ap.declareXPathNameSpace("cwmpNs",NameSpace.CWMP_NAME_SPACE.getDesc());
   	 		// get to the inform node
			ap.selectXPath("/soapNs:Envelope/soapNs:Body/cwmpNs:SetParameterValuesResponse");
			
			vg.setDoc(xml.getBytes());
   		 	vg.parse(true);  // set namespace awareness to true 
   		 	VTDNav vn = vg.getNav();
   		 	ap.bind(vn); // bind calls resetXPath() so
   		 	/**
   		 	 * 获取SetParameterValuesResponse报文的每个子节点
   		 	 */
   		 	if(ap.evalXPath() != -1){
   		 		setParameterValuesResponse = new SetParameterValuesResponse();
   		 		// to first child
   	   		 	if (vn.toElement(VTDNav.FC)){ 
   	   		 		String value = VtdUtils.getText(vn);
   	   		 		if(StringUtils.isNotBlank(value)){
   	   		 			setParameterValuesResponse.setStatus(Integer.parseInt(value));
   	   		 		}else{
   	   		 			logger.error("setParameterValues status is null!");
   	   		 		}
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
		return setParameterValuesResponse;
	}

}
