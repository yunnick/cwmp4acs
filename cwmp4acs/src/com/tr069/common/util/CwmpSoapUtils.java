package com.tr069.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tr069.acs.po.FaultStruct;
import com.tr069.acs.po.ParameterValueStruct;
import com.tr069.common.constants.CommonContants.DataStruct;
import com.tr069.common.constants.CommonContants.DataType;
import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.cpe.po.ParameterAttributeStruct;
import com.tr069.cpe.po.ParameterInfoStruct;
import com.tr069.cpe.po.SetParameterAttributesStruct;
import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.VTDNav;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

public class CwmpSoapUtils {
	static Logger logger = Logger.getLogger(CwmpSoapUtils.class);
	/**
	 * 解析FaultStruct:
	 * <pre>
	 * &lt;FaultStruct>
	 *	&lt;FaultCode>0&lt;/FaultCode>
	 *	&lt;FaultString>&lt;/FaultString>
	 *  &lt;/FaultStruct>
	 * </pre>
	 * @param vn
	 * @param ap
	 * @param transferComplete
	 * @throws XPathParseException
	 * @throws NumberFormatException
	 * @throws XPathEvalException
	 * @throws NavException
	 */
	public static FaultStruct parseFaultStruct(VTDNav vn, AutoPilot ap) throws XPathParseException, NumberFormatException, XPathEvalException, NavException{
		FaultStruct fs = new FaultStruct();
		//选取所有子节点
		ap.selectXPath("child::*");
 		int m = 0;
		while((m = ap.evalXPath()) != -1){
			int t = vn.getText();
			String paramTag = vn.toString(m);
			String paramValue = vn.toString(t);
			
			if(StringUtils.containsIgnoreCase(paramTag, "FaultCode")){
				if(StringUtils.isNotBlank(paramValue)){
					int faultCode = Integer.parseInt(paramValue); 
					fs.setFaultCode(faultCode);
				}else{
					logger.error("faultCode empty");
				}
				if(logger.isDebugEnabled()){
					logger.debug("faultCode :" + paramValue);
				}
			}else if(StringUtils.containsIgnoreCase(paramTag, "FaultString")){
				fs.setFaultString(paramValue);
				if(logger.isDebugEnabled()){
					logger.debug("FaultString :" + paramValue);
				}
			}
		}
		return fs;
	}
	
	/**
	 * 获取参数节点的所有参数，vn此刻应指向&lt;ParameterList>节点:
	 * <pre>
	 *  &lt;ParameterList SOAP-ENC:arrayType="cwmp:ParameterValueStruct[2]">
	 *		&lt;ParameterValueStruct>
	 *			&lt;Name>Device.ManagementServer.STUNServerAddress&lt;/Name>
	 *			&lt;Value xsi:type="xsd:string">60.29.150.126&lt;/Value>
	 *		&lt;/ParameterValueStruct>
	 *		&lt;ParameterValueStruct>
	 *			&lt;Name>Device.ManagementServer.STUNEnable&lt;/Name>
	 *			&lt;Value xsi:type="xsd:boolean">true&lt;/Value>
	 *		&lt;/ParameterValueStruct>
	 *	&lt;/ParameterList>
	 *	</pre>
	 * @param vn
	 * @param ap
	 * @return
	 * @throws XPathParseException
	 * @throws XPathEvalException
	 * @throws NavException
	 */
	public static List<ParameterValueStruct> getParams(VTDNav vn, AutoPilot ap) throws XPathParseException, XPathEvalException, NavException{
		List<ParameterValueStruct> parameterList = null;
		//遍历参数列表所有节点
		if(vn.toElement(VTDNav.FC)){
			parameterList = new ArrayList<ParameterValueStruct>();
			do{
				ParameterValueStruct parameter = new ParameterValueStruct();
				//共两个子节点
				ap.selectXPath("child::*");
				int m = 0;
				while((m = ap.evalXPath()) != -1){
					int t = vn.getText();
					String paramTag = vn.toString(m);
					String paramValue = vn.toString(t);
					
					if(StringUtils.containsIgnoreCase(paramTag, "Name")){
						parameter.setName(paramValue);
						if(logger.isDebugEnabled()){
							logger.debug("name :" + paramValue);
						}
					}else if(StringUtils.containsIgnoreCase(paramTag, "Value")){
						parameter.setValue(paramValue);
						if(logger.isDebugEnabled()){
							logger.debug("value :" + paramValue);
						}
					}
				}
				parameterList.add(parameter);
			}while(vn.toElement(VTDNav.NS));
		}
		return parameterList;
	}
	
	/**
	 * 获取参数节点的参数名及可写性，vn此刻应指向&lt;ParameterList>节点:
<pre>
&lt;ParameterList SOAP-ENC:arrayType="cwmp:ParameterInfoStruct[2]">
	&lt;ParameterInfoStruct>
		&lt;Name>Device.ManagementServer.STUNServerAddress</Name>
		&lt;Writable xsi:type="xsd:boolean">true</Writable>
	&lt;/ParameterInfoStruct>
	&lt;ParameterInfoStruct>
		&lt;Name>Device.ManagementServer.STUNEnable&lt;/Name>
		&lt;Writable xsi:type="xsd:boolean">true&lt;/Writable>
	&lt;/ParameterInfoStruct>
&lt;/ParameterList>
</pre>
	 * @param vn
	 * @param ap
	 * @return
	 * @throws XPathParseException
	 * @throws XPathEvalException
	 * @throws NavException
	 */
	public static List<ParameterInfoStruct> getParamNames(VTDNav vn, AutoPilot ap) throws XPathParseException, XPathEvalException, NavException{
		List<ParameterInfoStruct> parameterList = null;
		//遍历参数列表所有节点
		if(vn.toElement(VTDNav.FC)){
			parameterList = new ArrayList<ParameterInfoStruct>();
			do{
				ParameterInfoStruct parameter = new ParameterInfoStruct();
				//共两个子节点
				ap.selectXPath("child::*");
				int m = 0;
				while((m = ap.evalXPath()) != -1){
					int t = vn.getText();
					String paramTag = vn.toString(m);
					String paramValue = vn.toString(t).trim();
					
					if(StringUtils.containsIgnoreCase(paramTag, "Name")){
						parameter.setName(paramValue);
						if(logger.isDebugEnabled()){
							logger.debug("name :" + paramValue);
						}
					}else if(StringUtils.containsIgnoreCase(paramTag, "Writable")){
						if(StringUtils.equalsIgnoreCase(paramValue, "true")){
							parameter.setWritable(true);
						}else{
							parameter.setWritable(false);
						}
						if(logger.isDebugEnabled()){
							logger.debug("value :" + paramValue);
						}
					}
				}
				parameterList.add(parameter);
			}while(vn.toElement(VTDNav.NS));
		}
		return parameterList;
	}
	
	/**
	 * 构建参数列表，返回soap包文中的ParameterList结构的字符串
	 * @return
	 */
	public static String buildParameterList(List<ParameterValueStruct> parameterList){
		//SOAP-ENCODE命名空间
		String encNs = NameSpace.SOAP_ENC_NAME_SPACE.getResponseNs();
		//返回的数据
		StringBuffer param = new StringBuffer();
		int len = parameterList.size();
		param.append("<ParameterList " + encNs + ":arrayType=\"cwmp:ParameterValueStruct[" +len+ "]\">");
		if(isIterable(parameterList)){
			for(ParameterValueStruct pv : parameterList){
				param.append(buildParameterValue(pv));
			}
		}
		param.append("</ParameterList>");
		return param.toString();
	}
	
	/**
	 * 构建参数属性列表，返回soap包文中的ParameterList结构的字符串:
	 * <pre>
	 * &lt;ParameterList SOAP-ENC:arrayType="cwmp:SetParameterAttributesStruct[1]">
	 *		&lt;SetParameterAttributesStruct>
	 *			&lt;Name>Device.ManagementServer.NATDetected&lt;/Name>
	 *			&lt;Notification>2&lt;/Notification>
	 *			&lt;NotificationChange>true&lt;/NotificationChange>
	 *			&lt;AccessListChange>false&lt;/AccessListChange>
	 *			&lt;AccessList SOAP-ENC:arrayType="cwmp:string[0]"/>
	 *		&lt;/SetParameterAttributesStruct>
     * &lt;/ParameterList>
	 * </pre>
	 * @return
	 */
	public static String buildSetParameterAttributeList(List<SetParameterAttributesStruct> parameterList){
		//SOAP-ENCODE命名空间
		String encNs = NameSpace.SOAP_ENC_NAME_SPACE.getResponseNs();
		//返回的数据
		StringBuffer param = new StringBuffer();
		int len = parameterList.size();
		param.append("<ParameterList " + encNs + ":arrayType=\"cwmp:SetParameterAttributesStruct[" +len+ "]\">");
		if(isIterable(parameterList)){
			for(SetParameterAttributesStruct spav : parameterList){
				param.append(buildSetParameterAttributes(spav));
			}
		}
		param.append("</ParameterList>");
		return param.toString();
	}
	
	/**
	 * 解析ParameterAttributeStruct列表，返回list对象
	 * <pre>
	 * &lt;ParameterList SOAP-ENC:arrayType="cwmp:SetParameterAttributesStruct[1]">
	 *		&lt;ParameterAttributesStruct>
	 *			&lt;Name>Device.ManagementServer.NATDetected&lt;/Name>
	 *			&lt;Notification>2&lt;/Notification>
	 *			&lt;AccessList SOAP-ENC:arrayType="cwmp:string[0]"/>
	 *		&lt;/ParameterAttributesStruct>
     * &lt;/ParameterList>
	 * </pre>
	 * @param vn
	 * @param ap
	 * @return
	 * @throws NavException 
	 * @throws XPathParseException 
	 * @throws XPathEvalException 
	 * @throws NumberFormatException 
	 */
	public static List<ParameterAttributeStruct> parseParameterAttributeList(VTDNav vn, AutoPilot ap) throws NavException, XPathParseException, NumberFormatException, XPathEvalException{
		List<ParameterAttributeStruct> paraAttList = null;
		if(vn.toElement(VTDNav.FC)){
			paraAttList = new ArrayList<ParameterAttributeStruct>();
			do{
				//选取所有子节点
				ap.selectXPath("child::*");
		 		int m = 0;
		 		ParameterAttributeStruct pa = new ParameterAttributeStruct();
		 		while ((m = ap.evalXPath()) != -1){
					String paramTag = vn.toString(m);
					if(StringUtils.containsIgnoreCase(paramTag, "Name")){
						int t = vn.getText();
						String paramValue = vn.toString(t).trim();
						pa.setName(paramValue);
					}else if(StringUtils.containsIgnoreCase(paramTag, "Notification")){
						int t = vn.getText();
						String paramValue = vn.toString(t).trim();
						if(StringUtils.isNotBlank(paramValue)){
							pa.setNotification(Integer.parseInt(paramValue));
						}else{
							logger.error("GetParameterAttributesResponse Notification empty");
						}
					}else if(StringUtils.containsIgnoreCase(paramTag, "AccessList")){
						List<String> accessList = null;
						//遍历AccessList子节点
						if(vn.toElement(VTDNav.FC)){
							accessList = new ArrayList<String>();
							do{
								int t = vn.getText();
								String paramValue = vn.toString(t).trim();
								accessList.add(paramValue);
							}while(vn.toElement(VTDNav.NEXT_SIBLING));
							pa.setAccessList(accessList);
						}
						vn.toElement(VTDNav.P);
					}
		 		}
		 		paraAttList.add(pa);
					
			}while(vn.toElement(VTDNav.NEXT_SIBLING));
		}
		return paraAttList;
	}
	
	/**
	 * 构建具体的一个ParameterValueStruc参数对应字符串,形如：
	 * <pre>
	 *		&lt;ParameterValueStruct>
	 *		&lt;Name>Device.ManagementServer.STUNServerAddress&lt;/Name>
	 *			&lt;Value xsi:type="xsd:string">60.29.150.126&lt;/Value>
	 *		&lt;/ParameterValueStruct>
	 *  </pre>
	 * @param pv
	 * @return
	 */
	public static String buildParameterValue(ParameterValueStruct pv){
		StringBuffer pvBuf = new StringBuffer();
		pvBuf.append("<ParameterValueStruct>");
		pvBuf.append("<Name>");
		pvBuf.append(pv.getName());
		pvBuf.append("</Name>");
		//有参数类型
		if(pv.getParameterTypeStruct() != null
				&& pv.getParameterTypeStruct().getDataStruct() != null
				&& pv.getParameterTypeStruct().getType() != null){
			pvBuf.append("<Value ");
			pvBuf.append(pv.getParameterTypeStruct().getDataStructNs().getResponseNs());
			pvBuf.append(":" + pv.getParameterTypeStruct().getDataStruct().getDesc() + "=\"");
			pvBuf.append(pv.getParameterTypeStruct().getTypeNs().getResponseNs());
			pvBuf.append(":" + pv.getParameterTypeStruct().getType().getDesc());
			if(pv.getParameterTypeStruct().getDataStruct() == DataStruct.ARRAY_TYPE 
					&& StringUtils.isNotBlank(pv.getParameterTypeStruct().getLength())){
				//定义数组长度
				pvBuf.append("[" + Integer.parseInt(pv.getParameterTypeStruct().getLength()) + "]");
			}else if(pv.getParameterTypeStruct().getDataStruct() == DataStruct.TYPE
					&& (pv.getParameterTypeStruct().getType() == DataType.STRING || pv.getParameterTypeStruct().getType() == DataType.BASE64)
					&& StringUtils.isNotBlank(pv.getParameterTypeStruct().getLength())){
				//定义字符串和Base64长度
				pvBuf.append("(" + Integer.parseInt(pv.getParameterTypeStruct().getLength()) + ")");
			}else if(pv.getParameterTypeStruct().getDataStruct() == DataStruct.TYPE
					&& (pv.getParameterTypeStruct().getType() == DataType.INT || pv.getParameterTypeStruct().getType() == DataType.UNSIGNED_INT)
					&& StringUtils.isNotBlank(pv.getParameterTypeStruct().getMin())
					&& StringUtils.isNotBlank(pv.getParameterTypeStruct().getMax())){
				//定义整数型和无符号整数型范围
				pvBuf.append("[" + Integer.parseInt(pv.getParameterTypeStruct().getMin()) + ":" +
						Integer.parseInt(pv.getParameterTypeStruct().getMax()) + "]");
			}
			pvBuf.append("\">");
			pvBuf.append(pv.getValue());
			pvBuf.append("</Value>");
		}else{
			//参数无类型
			pvBuf.append("<Value>" + pv.getValue() + "</Value>");
		}
		pvBuf.append("</ParameterValueStruct>");
		return pvBuf.toString();
	}
	
	/**
	 * 构建具体的一个SetParameterAttributesStruct参数对应字符串,形如：
	 * <pre>
	 *		&lt;SetParameterAttributesStruct>
	 *			&lt;Name>Device.ManagementServer.NATDetected&lt;/Name>
	 *			&lt;Notification>2&lt;/Notification>
	 *			&lt;NotificationChange>true&lt;/NotificationChange>
	 *			&lt;AccessListChange>false&lt;/AccessListChange>
	 *			&lt;AccessList SOAP-ENC:arrayType="cwmp:string[0]"/>
	 *		&lt;/SetParameterAttributesStruct>
	 *  </pre>
	 * @param pv
	 * @return
	 */
	public static String buildSetParameterAttributes(SetParameterAttributesStruct spav){
		StringBuffer pvBuf = new StringBuffer();
		pvBuf.append("<SetParameterAttributesStruct>");
		pvBuf.append("<Name>");
		pvBuf.append(spav.getName());
		pvBuf.append("</Name>");
		pvBuf.append("<Notification>" + spav.getNotification() + "</Notification>");
		pvBuf.append("<NotificationChange>" + spav.isNotificationChange() + "</NotificationChange>");
		pvBuf.append("<AccessListChange>" + spav.isAccessListChange() +"</AccessListChange>");
		if(isIterable(spav.getAccessList())){
			pvBuf.append("<AccessList " + NameSpace.SOAP_ENC_NAME_SPACE.getResponseNs() + ":arrayType=\"cwmp:string[" + spav.getAccessList().size() + "]\">");
				for(String acc : spav.getAccessList()){
					pvBuf.append("<string>" + acc + "</string>");
				}
			pvBuf.append("</AccessList>");
		}else{
			pvBuf.append("<AccessList " + NameSpace.SOAP_ENC_NAME_SPACE.getResponseNs() + ":arrayType=\"cwmp:string[0]\"/>");
		}
		pvBuf.append("</SetParameterAttributesStruct>");
		return pvBuf.toString();
	}
	
	/**
	 * 构建参数名列表，返回soap包文中的ParameterNames结构的字符串:
	 * <pre>
	 *  &lt;ParameterNames SOAP-ENC:arrayType="SOAP-ENC:xsd:string[2]">
	 *		&lt;string>Device.LAN.MACAddress&lt;/string>
	 *		&lt;string>Device.X_CU_STB.AuthServiceInfo.UserID&lt;/string>
	 *  &lt;/ParameterNames>
	 * </pre>
	 * 
	 * @return
	 */
	public static String buildParameterNameList(List<String> parameterName){
		//SOAP-ENCODE命名空间
		String encNs = NameSpace.SOAP_ENC_NAME_SPACE.getResponseNs();
		//返回的数据
		StringBuffer paramNames = new StringBuffer();
		int len = parameterName.size();
		paramNames.append("<ParameterNames " + encNs + ":arrayType=\"xsd:string[" +len+ "]\">");
		if(isIterable(parameterName)){
			for(String name : parameterName){
				paramNames.append("<string>" + name + "</string>");
			}
		}
		paramNames.append("</ParameterNames>");
		return paramNames.toString();
	}
	
	/**
	 * 判断List数据是否可以遍历（非空并且至少包含一个元素）
	 * @param list
	 * @return
	 */
	public static boolean isIterable(List<? extends Object> list){
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取一个字符串如果为空，则返回""
	 */
	public static String getString(String str){
		if(StringUtils.isBlank(str)){
			return "";
		}
		return str;
	}
}
