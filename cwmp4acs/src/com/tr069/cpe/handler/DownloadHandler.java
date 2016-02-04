package com.tr069.cpe.handler;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.cpe.message.request.Download;
import com.tr069.cpe.message.response.DownloadResponse;
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


public class DownloadHandler implements RequestParser<DownloadResponse>,
		ResponseBuilder<Download> {

	Logger logger = Logger.getLogger(DownloadHandler.class);
	@Override
	public String build(Download message) {
		//ENVELOPE命名空间
		String envNs = NameSpace.ENVELOPE_NAME_SPACE.getResponseNs();
		/**
		 * 拼接body
		 */
		StringBuffer body = new StringBuffer();
		body.append("<" + envNs + ":Body>");
		body.append("<cwmp:Download>");
		/**
		 * CommandKey
		 */
		body.append("<CommandKey>" + message.getCommandKey() + "</CommandKey>");
		/**
		 * FileType
		 */
		body.append("<FileType>" + message.getFileType() + "</FileType>");
		body.append("<URL>" + message.getUrl() + "</URL>");
		body.append("<Username>" + message.getUsername() + "</Username>");
		body.append("<Password>" + message.getPassword() +"</Password>");
		body.append("<FileSize>" + message.getFileSize() + "</FileSize>");
		body.append("<TargetFileName>" + message.getTargetFileName() + "</TargetFileName>");
		body.append("<DelaySeconds>" + message.getDelaySeconds() + "</DelaySeconds>");
		body.append("<SuccessURL>" + message.getSuccessURL() + "</SuccessURL>");
		body.append("<FailureURL>" + message.getFailureURL() + "</FailureURL>");
		body.append("</cwmp:Download>");
		body.append("</" + envNs + ":Body>");
		return body.toString();
	}

	@Override
	public DownloadResponse parseXml(String xml) {
		DownloadResponse downloadResponse = null;
   	 	try {
   	 		// instantiate the parser
   	 		VTDGen vg = new VTDGen();
   	 		AutoPilot ap = new AutoPilot();
   	 		//declare a namespace prefix
   	 		ap.declareXPathNameSpace("soapNs",NameSpace.ENVELOPE_NAME_SPACE.getDesc());
   	 		ap.declareXPathNameSpace("cwmpNs",NameSpace.CWMP_NAME_SPACE.getDesc());
   	 		// get to the inform node
			ap.selectXPath("/soapNs:Envelope/soapNs:Body/cwmpNs:DownloadResponse");
			
			vg.setDoc(xml.getBytes());
   		 	vg.parse(true);  // set namespace awareness to true 
   		 	VTDNav vn = vg.getNav();
   		 	ap.bind(vn); // bind calls resetXPath() so
   		 	/**
   		 	 * 获取SetParameterValuesResponse报文的每个子节点
   		 	 */
   		 	if(ap.evalXPath() != -1){
   		 		downloadResponse = new DownloadResponse();
   		 		// to first child
   	   		 	if (vn.toElement(VTDNav.FC)){ 
   	   		 		do{
	   	   		 		int t = vn.getText();
	   	   		 		int m = vn.getCurrentIndex();
						String paramTag = vn.toString(m);
						String paramValue = vn.toString(t).trim();
					
   	   		 			if(StringUtils.containsIgnoreCase(paramTag, "Status")){
		   	   		 		if(StringUtils.isNotBlank(paramValue)){
		   	   		 			downloadResponse.setStatus(Integer.parseInt(paramValue));
		   	   		 		}else{
		   	   		 			logger.error("Download status is null!");
		   	   		 		}
   	   		 			}else if(StringUtils.containsIgnoreCase(paramTag, "StartTime")){
	   	   		 			if(StringUtils.isNotBlank(paramValue)){
		   	   		 			downloadResponse.setStartTime(paramValue);
		   	   		 		}else{
		   	   		 			logger.error("Download StartTime is null!");
		   	   		 		}
   	   		 			}else if(StringUtils.containsIgnoreCase(paramTag, "CompleteTime")){
	   	   		 			if(StringUtils.isNotBlank(paramValue)){
		   	   		 			downloadResponse.setCompleteTime(paramValue);
		   	   		 		}else{
		   	   		 			logger.error("Download CompleteTime is null!");
		   	   		 		}
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
		return downloadResponse;
	}

}
