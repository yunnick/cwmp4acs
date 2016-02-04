package com.tr069.cpe.handler;

import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.util.CwmpSoapUtils;
import com.tr069.cpe.message.request.GetParameterAttributes;
import com.tr069.cpe.message.response.GetParameterAttributesResponse;
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

public class GetParameterAttributesHandler implements RequestParser<GetParameterAttributesResponse>, ResponseBuilder<GetParameterAttributes> {

	@Override
	public String build(GetParameterAttributes message) {
		//ENVELOPE命名空间
		String envNs = NameSpace.ENVELOPE_NAME_SPACE.getResponseNs();
		//ENCODING 命名空间
		String encNs = NameSpace.SOAP_ENC_NAME_SPACE.getResponseNs();
		/**
		 * 拼接body
		 */
		StringBuffer body = new StringBuffer();
		body.append("<" + envNs + ":Body>");
		body.append("<cwmp:GetParameterAttributes>");
		/**
		 * ParameterNames
		 */
		if(CwmpSoapUtils.isIterable(message.getParameterNames())){
			body.append("<ParameterNames " + encNs + ":arrayType=\"xsd:string[" + message.getParameterNames().size() + "]\">");
			for(String param : message.getParameterNames()){
				body.append("<string>" + param + "</string>");
			}
		}
		body.append("</ParameterNames>");
		
		body.append("</cwmp:GetParameterAttributes>");
		body.append("</" + envNs + ":Body>");
		return body.toString();
	}

	@Override
	public GetParameterAttributesResponse parseXml(String xml) {
		GetParameterAttributesResponse getParameterAttributesResponse = null;
   	 	try {
   	 		// instantiate the parser
   	 		VTDGen vg = new VTDGen();
   	 		AutoPilot ap = new AutoPilot();
   	 		//declare a namespace prefix
   	 		ap.declareXPathNameSpace("soapNs",NameSpace.ENVELOPE_NAME_SPACE.getDesc());
   	 		ap.declareXPathNameSpace("cwmpNs",NameSpace.CWMP_NAME_SPACE.getDesc());
   	 		// get to the inform node
			ap.selectXPath("/soapNs:Envelope/soapNs:Body/cwmpNs:GetParameterAttributesResponse/ParameterList");
			
			vg.setDoc(xml.getBytes());
   		 	vg.parse(true);  // set namespace awareness to true 
   		 	VTDNav vn = vg.getNav();
   		 	ap.bind(vn); // bind calls resetXPath() so
   		 	/**
   		 	 * 获取SetParameterValuesResponse报文的每个子节点
   		 	 */
   		 	if(ap.evalXPath() != -1){
   		 		getParameterAttributesResponse = new GetParameterAttributesResponse();
   		 		getParameterAttributesResponse.setParameterList(CwmpSoapUtils.parseParameterAttributeList(vn, ap));
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
		return getParameterAttributesResponse;
	}

}
