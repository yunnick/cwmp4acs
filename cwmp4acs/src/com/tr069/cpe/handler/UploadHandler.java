package com.tr069.cpe.handler;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.util.CwmpSoapUtils;
import com.tr069.cpe.message.request.Upload;
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

public class UploadHandler implements RequestParser<UploadResponse>, ResponseBuilder<Upload> {

	Logger logger = Logger.getLogger(UploadHandler.class);
	@Override
	public String build(Upload message) {
		//ENVELOPE命名空间
		String envNs = NameSpace.ENVELOPE_NAME_SPACE.getResponseNs();
		/**
		 * 拼接body
		 */
		StringBuffer body = new StringBuffer();
		body.append("<" + envNs + ":Body>");
		body.append("<cwmp:Upload>");
		/**
		 * CommandKey
		 */
		body.append("<CommandKey>" + CwmpSoapUtils.getString(message.getCommandKey())+ "</CommandKey>");
		/**
		 * FileType
		 */
		body.append("<FileType>"+CwmpSoapUtils.getString(message.getFileType())+"</FileType>");
		body.append("<URL>"+CwmpSoapUtils.getString(message.getUrl())+"</URL>");
		body.append("<Username>"+CwmpSoapUtils.getString(message.getUsername())+"</Username>");
		body.append("<Password>"+CwmpSoapUtils.getString(message.getPassword())+"</Password>");
		body.append("<DelaySeconds>"+message.getDelaySeconds()+"</DelaySeconds>");
		
		body.append("</cwmp:Upload>");
		body.append("</" + envNs + ":Body>");
		return body.toString();
	}

	@Override
	public UploadResponse parseXml(String xml) {
		UploadResponse uploadResponse = null;
   	 	try {
   	 		// instantiate the parser
   	 		VTDGen vg = new VTDGen();
   	 		AutoPilot ap = new AutoPilot();
   	 		//declare a namespace prefix
   	 		ap.declareXPathNameSpace("soapNs",NameSpace.ENVELOPE_NAME_SPACE.getDesc());
   	 		ap.declareXPathNameSpace("cwmpNs",NameSpace.CWMP_NAME_SPACE.getDesc());
   	 		// get to the inform node
			ap.selectXPath("/soapNs:Envelope/soapNs:Body/cwmpNs:UploadResponse");
			
			vg.setDoc(xml.getBytes());
   		 	vg.parse(true);  // set namespace awareness to true 
   		 	VTDNav vn = vg.getNav();
   		 	ap.bind(vn); // bind calls resetXPath() so
   		 	/**
   		 	 * 获取SetParameterValuesResponse报文的每个子节点
   		 	 */
   		 	if(ap.evalXPath() != -1){
   		 		uploadResponse = new UploadResponse();
   		 		// to first child
   	   		 	if (vn.toElement(VTDNav.FC)){ 
   	   		 		do{
	   	   		 		int t = vn.getText();
	   	   		 		int m = vn.getCurrentIndex();
						String paramTag = vn.toString(m);
						String paramValue = vn.toString(t).trim();
				
	   		 			if(StringUtils.containsIgnoreCase(paramTag, "Status")){
		   	   		 		if(StringUtils.isNotBlank(paramValue)){
		   	   		 			uploadResponse.setStatus(Integer.parseInt(paramValue));
		   	   		 		}else{
		   	   		 			logger.error("Upload status is null!");
		   	   		 		}
	   		 			}else if(StringUtils.containsIgnoreCase(paramTag, "StartTime")){
   	   		 				uploadResponse.setStartTime(CwmpSoapUtils.getString(paramValue));
	   		 			}else if(StringUtils.containsIgnoreCase(paramTag, "CompleteTime")){
   	   		 				uploadResponse.setCompleteTime(CwmpSoapUtils.getString(paramValue));
	   		 			}
	   		 		}while(vn.toElement(VTDNav.NS));
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
		return uploadResponse;
	}

}
