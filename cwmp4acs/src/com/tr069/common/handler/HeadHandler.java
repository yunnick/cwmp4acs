package com.tr069.common.handler;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.message.SoapHeader;
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

public class HeadHandler implements RequestParser<SoapHeader> , ResponseBuilder<SoapHeader>{

	Logger logger = Logger.getLogger(HeadHandler.class);
	
	/**
	 * 获取头部对象
	 */
	@Override
	public SoapHeader parseXml(String xml) {
		
		SoapHeader soapHeader = new SoapHeader();
   	 	try {
   	 		// instantiate the parser
   	 		VTDGen vg = new VTDGen();
   	 		AutoPilot ap = new AutoPilot();
   	 		//declare a namespace prefix
   	 		ap.declareXPathNameSpace("soapNs",NameSpace.ENVELOPE_NAME_SPACE.getDesc());
   	 		ap.declareXPathNameSpace("cwmpNs",NameSpace.CWMP_NAME_SPACE.getDesc());
   	 		// get to the SOAP header
			ap.selectXPath("/soapNs:Envelope/soapNs:Header/cwmpNs:*");
			
			vg.setDoc(xml.getBytes());
   		 	vg.parse(true);  // set namespace awareness to true 
   		 	VTDNav vn = vg.getNav();
   		 	ap.bind(vn); // bind calls resetXPath() so
   		 	
   		 	int index = -1;//the node index
   		 	int textIndex = 0;//the text index
   		 	/**
   		 	 * 获取头部标签和对应的值
   		 	 */
   		 	while((index = ap.evalXPath()) != -1){
   		 		//文本对应位置
   		 		textIndex = vn.getText();
				String tagName = vn.toString(index).trim();
				String value = vn.toString(textIndex);
				if(logger.isDebugEnabled()){
					logger.debug(index + " tagName:" +tagName );
					logger.debug("value :" + value);
				}
				/**
				 * tagName去掉命名空间
				 */
				if(StringUtils.isNotBlank(tagName)){
					String[] tag = tagName.split(":");
					if(tag.length > 1){
						tagName = tag[1].trim();
						if(StringUtils.equalsIgnoreCase(tagName, "id")){
							soapHeader.setId(value);
						}else if(StringUtils.equalsIgnoreCase(tagName, "holdRequests")){
							soapHeader.setHoldRequests(value);
						}else if(StringUtils.equalsIgnoreCase(tagName, "noMoreRequests")){
							soapHeader.setNoMoreRequests(value);
						}else{
							logger.error("get an irregular header field:" + tagName);
						}
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
		} catch (XPathEvalException e) {
			e.printStackTrace();
		} catch (NavException e) {
			e.printStackTrace();
		}
		return soapHeader;
	}

	/**
	 * 构造头部，并添加命名空间等根节点信息
	 */
	@Override
	public String build(SoapHeader message) {
		//ENVELOPE命名空间
		String envNs = NameSpace.ENVELOPE_NAME_SPACE.getResponseNs();
				
		StringBuffer header = new StringBuffer();
		header.append("<" + envNs + ":Header>");
		if(StringUtils.isNotBlank(message.getId())){
			header.append("<cwmp:ID soap:mustUnderstand=\"1\">" + message.getId() + "</cwmp:ID>");
		}
		if(StringUtils.isNotBlank(message.getHoldRequests())){
			header.append("<cwmp:HoldRequests soap:mustUnderstand=\"1\">" + message.getHoldRequests() + "</cwmp:HoldRequests>");
		}
		if(StringUtils.isNotBlank(message.getHoldRequests())){
			header.append("<cwmp:NoMoreRequests>" + message.getHoldRequests() + "</cwmp:NoMoreRequests>");
		}
		header.append("</" + envNs + ":Header>");
		return header.toString();
	}

}
