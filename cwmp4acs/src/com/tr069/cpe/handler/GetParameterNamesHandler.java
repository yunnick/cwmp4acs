package com.tr069.cpe.handler;

import org.apache.commons.lang.StringUtils;

import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.util.CwmpSoapUtils;
import com.tr069.cpe.message.request.GetParameterNames;
import com.tr069.cpe.message.response.GetParameterNamesResponse;
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

public class GetParameterNamesHandler implements RequestParser<GetParameterNamesResponse>,
ResponseBuilder<GetParameterNames>{

	@Override
	public String build(GetParameterNames message) {
		//ENVELOPE命名空间
		String envNs = NameSpace.ENVELOPE_NAME_SPACE.getResponseNs();
		/**
		 * 拼接body
		 */
		StringBuffer body = new StringBuffer();
		body.append("<" + envNs + ":Body>");
		body.append("<cwmp:GetParameterNames>");
		/**
		 * ParameterPath
		 */
		String path = StringUtils.isBlank(message.getParameterPath())?"":message.getParameterPath();
		body.append("<ParameterPath>" + path + "</ParameterPath>");
		/**
		 * NextLevel
		 */
		String nl = message.isNextLevel()?"true":"false";
		body.append("<NextLevel>" + nl + "</NextLevel>");
		
		body.append("</cwmp:GetParameterNames>");
		body.append("</" + envNs + ":Body>");
		return body.toString();
	}

	@Override
	public GetParameterNamesResponse parseXml(String xml) {
		GetParameterNamesResponse getParameterNamesResponse = null;
   	 	try {
   	 		// instantiate the parser
   	 		VTDGen vg = new VTDGen();
   	 		AutoPilot ap = new AutoPilot();
   	 		//declare a namespace prefix
   	 		ap.declareXPathNameSpace("soapNs",NameSpace.ENVELOPE_NAME_SPACE.getDesc());
   	 		ap.declareXPathNameSpace("cwmpNs",NameSpace.CWMP_NAME_SPACE.getDesc());
   	 		// get to the inform node
			ap.selectXPath("/soapNs:Envelope/soapNs:Body/cwmpNs:GetParameterNamesResponse/ParameterList");
			
			vg.setDoc(xml.getBytes());
   		 	vg.parse(true);  // set namespace awareness to true 
   		 	VTDNav vn = vg.getNav();
   		 	ap.bind(vn); // bind calls resetXPath() so
   		 	/**
   		 	 * 获取SetParameterValuesResponse报文的每个子节点
   		 	 */
   		 	if(ap.evalXPath() != -1){
   		 		getParameterNamesResponse = new GetParameterNamesResponse();
   		 		getParameterNamesResponse.setParameterList(CwmpSoapUtils.getParamNames(vn, ap));
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
		return getParameterNamesResponse;
	}

}
