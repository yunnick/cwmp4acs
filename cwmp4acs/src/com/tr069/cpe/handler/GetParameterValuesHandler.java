package com.tr069.cpe.handler;



import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.util.CwmpSoapUtils;
import com.tr069.cpe.message.request.GetParameterValues;
import com.tr069.cpe.message.response.GetParameterValuesResponse;
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

public class GetParameterValuesHandler implements RequestParser<GetParameterValuesResponse>,
		ResponseBuilder<GetParameterValues> {

	@Override
	public String build(GetParameterValues message) {
		//ENVELOPE命名空间
		String envNs = NameSpace.ENVELOPE_NAME_SPACE.getResponseNs();
		/**
		 * 拼接body
		 */
		StringBuffer body = new StringBuffer();
		body.append("<" + envNs + ":Body>");
		body.append("<cwmp:GetParameterValues>");
		/**
		 * 拼接ParameterNames
		 */
		body.append(CwmpSoapUtils.buildParameterNameList(message.getParameterName()));
		
		body.append("</cwmp:GetParameterValues>");
		body.append("</" + envNs + ":Body>");
		return body.toString();
	}

	@Override
	public GetParameterValuesResponse parseXml(String xml) {
		GetParameterValuesResponse getParameterValuesResponse = null;
   	 	try {
   	 		// instantiate the parser
   	 		VTDGen vg = new VTDGen();
   	 		AutoPilot ap = new AutoPilot();
   	 		//declare a namespace prefix
   	 		ap.declareXPathNameSpace("soapNs",NameSpace.ENVELOPE_NAME_SPACE.getDesc());
   	 		ap.declareXPathNameSpace("cwmpNs",NameSpace.CWMP_NAME_SPACE.getDesc());
   	 		// get to the inform node
			ap.selectXPath("/soapNs:Envelope/soapNs:Body/cwmpNs:GetParameterValuesResponse/ParameterList");
			
			vg.setDoc(xml.getBytes());
   		 	vg.parse(true);  // set namespace awareness to true 
   		 	VTDNav vn = vg.getNav();
   		 	ap.bind(vn); // bind calls resetXPath() so
   		 	/**
   		 	 * 获取GetParameterValuesResponse报文的每个子节点
   		 	 */
   		 	if(ap.evalXPath() != -1){
   		 		getParameterValuesResponse = new GetParameterValuesResponse();
   		 		getParameterValuesResponse.setParameterList(CwmpSoapUtils.getParams(vn, ap));
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
		return getParameterValuesResponse;
	}

}
