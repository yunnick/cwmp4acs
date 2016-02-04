package com.tr069.acs.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tr069.acs.constants.InformConstants.CommandKey;
import com.tr069.acs.constants.InformConstants.EventCode;
import com.tr069.acs.message.request.InformMessage;
import com.tr069.acs.message.response.InformResponse;
import com.tr069.acs.po.DeviceIdStruct;
import com.tr069.acs.po.EventStruct;
import com.tr069.acs.po.ParameterValueStruct;
import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.util.CwmpSoapUtils;
import com.tr069.common.util.MatchUtils;
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

public class InformHandler implements RequestParser<InformMessage>, ResponseBuilder<InformResponse> {

	Logger logger = Logger.getLogger(InformHandler.class);
	@Override
	public String build(InformResponse message) {
		//ENVELOPE命名空间
		String envNs = NameSpace.ENVELOPE_NAME_SPACE.getResponseNs();
				
		StringBuffer body = new StringBuffer();
		body.append("<" + envNs + ":Body><cwmp:InformResponse><MaxEnvelopes>");
		body.append(message.getMaxEnvelopes());
		body.append("</MaxEnvelopes></cwmp:InformResponse></" + envNs + ":Body>");
		return body.toString();
	}

	@Override
	public InformMessage parseXml(String xml) {
		InformMessage inform = null;
   	 	try {
   	 		// instantiate the parser
   	 		VTDGen vg = new VTDGen();
   	 		AutoPilot ap = new AutoPilot();
   	 		//declare a namespace prefix
   	 		ap.declareXPathNameSpace("soapNs",NameSpace.ENVELOPE_NAME_SPACE.getDesc());
   	 		ap.declareXPathNameSpace("cwmpNs",NameSpace.CWMP_NAME_SPACE.getDesc());
   	 		// get to the inform node
			ap.selectXPath("/soapNs:Envelope/soapNs:Body/cwmpNs:Inform");
			
			vg.setDoc(xml.getBytes());
   		 	vg.parse(true);  // set namespace awareness to true 
   		 	VTDNav vn = vg.getNav();
   		 	ap.bind(vn); // bind calls resetXPath() so
   		 	int index = -1;//the node index
   		 	/**
   		 	 * 获取Inform报文的每个子节点
   		 	 */
   		 	if(ap.evalXPath() != -1){
   		 		inform = new InformMessage();
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
	   	   		 			logger.debug("inform child tagName :"+tagName);
	   	   		 		}
	   	   		 		/**
	   	   		 		 * 有些报文可能在一些子节点前加命名空间，所示使用contains函数进行匹配
	   	   		 		 */
	   	   		 		if(StringUtils.containsIgnoreCase(tagName, "DeviceId")){
	   	   		 			parseDeviceId(vn, inform);
	   	   		 		}else if(StringUtils.containsIgnoreCase(tagName, "Event")){
	   	   		 			parseEvent(vn, inform);
	   	   		 		}else if(StringUtils.containsIgnoreCase(tagName, "MaxEnvelopes")){
	   	   		 			String value = VtdUtils.getText(vn);
	   	   		 			if(StringUtils.isNotBlank(value)){
	   	   		 				int maxEnvelopes = Integer.parseInt(value); 
	   	   		 				inform.setMaxEnvelopes(maxEnvelopes);
	   	   		 			}else{
	   	   		 				logger.error("MaxEnvelopes empty!");
	   	   		 			}
	   	   		 		}else if(StringUtils.containsIgnoreCase(tagName, "CurrentTime")){
	   	   		 			String value = VtdUtils.getText(vn);
	   	   		 			if(StringUtils.isNotBlank(value)){
	   	   		 				inform.setCurrentTime(value);
	   	   		 			}else{
	   	   		 				logger.error("CurrentTime empty!");
	   	   		 			}
	   	   		 		}else if(StringUtils.containsIgnoreCase(tagName, "RetryCount")){
	   	   		 			String value = VtdUtils.getText(vn);
	   	   		 			if(StringUtils.isNotBlank(value)){
	   	   		 				int retryCount = Integer.parseInt(value);
	   	   		 				inform.setRetryCount(retryCount);
	   	   		 			}else{
	   	   		 				logger.error("RetryCount empty!");
	   	   		 			}
	   	   		 		}else if(StringUtils.containsIgnoreCase(tagName, "ParameterList")){
	   	   		 			List<ParameterValueStruct> parameterList = CwmpSoapUtils.getParams(vn, ap);
	   	   		 			inform.setParameterList(parameterList);
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
		return inform;
	}
	
	private void parseDeviceId(VTDNav vn, InformMessage inform) throws NavException{
		//获取所有子节点的名称和文本值
		Map<String, String> childs = VtdUtils.fetchAllChilds(vn);
		DeviceIdStruct deviceId = new DeviceIdStruct();
		for(String deviceTag : childs.keySet()){
			if(StringUtils.containsIgnoreCase(deviceTag, "Manufacturer")){
				deviceId.setManufacturer(childs.get(deviceTag));
			}else if(StringUtils.containsIgnoreCase(deviceTag, "OUI")){
				deviceId.setOui(childs.get(deviceTag));
			}else if(StringUtils.containsIgnoreCase(deviceTag, "ProductClass")){
				deviceId.setProductClass(childs.get(deviceTag));
			}else if(StringUtils.containsIgnoreCase(deviceTag, "SerialNumber")){
				deviceId.setSerialNumber(childs.get(deviceTag));
			}else{
				logger.error("get an irregular header field:" + deviceTag);
			}
		}
		inform.setDeviceId(deviceId);
	}
	
	private void parseEvent(VTDNav vn, InformMessage inform) throws NavException{
		List<EventStruct> eventList = null;
		//获取事件个数
		int att = vn.getAttrValNS(NameSpace.ENVELOPE_NAME_SPACE.getDesc(), "arrayType");
		String arrayType = vn.toString(att);
		if(logger.isDebugEnabled()){
			logger.debug("arrayType:"+arrayType);
		}
		//标识事件个数
		int arrayLen = 0;
		String lenInStr = MatchUtils.getFirstNum(arrayType);
		if(StringUtils.isNotBlank(lenInStr)){
			arrayLen = Integer.parseInt(lenInStr);
 			if(logger.isDebugEnabled()){
 				logger.debug("array length:" + arrayLen);
 			}
		}
		//遍历event子节点
		if(vn.toElement(VTDNav.FC)){
			eventList = new ArrayList<EventStruct>();
			do{
				//获取所有子节点的名称及文本值
				Map<String, String> childs = VtdUtils.fetchAllChilds(vn);
				EventStruct event = new EventStruct();
				for(String deviceTag : childs.keySet()){
					if(StringUtils.containsIgnoreCase(deviceTag, "EventCode")){
						String eventStr = childs.get(deviceTag);
						EventCode e = EventCode.getEnum(eventStr);
						if(logger.isDebugEnabled()){
			 				logger.debug("eventStr:" + eventStr);
			 			}
						if(null == e){
							logger.error("can not recognized event:" + eventStr);
						}else{
							event.setEventCode(e);
						}
					}else if(StringUtils.containsIgnoreCase(deviceTag, "CommandKey")){
						String keyStr = childs.get(deviceTag);
						CommandKey key = CommandKey.getEnum(keyStr);
						if(logger.isDebugEnabled()){
			 				logger.debug("CommandKey:" + keyStr);
			 			}
						if(null == key){
							logger.warn("CommandKey is null");
						}else{
							event.setCommandKey(key);
						}
					}else{
						logger.error("get an irregular header field:" + deviceTag);
					}
				}
				eventList.add(event);
			}while (vn.toElement(VTDNav.NS));
		}
		vn.toElement(VTDNav.P);
		if(arrayLen != eventList.size()){
			logger.error("the event length is not equal to the declared number");
		}
		inform.setEvent(eventList);
	}

}
