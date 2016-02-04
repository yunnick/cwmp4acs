package com.tr069.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ximpleware.NavException;
import com.ximpleware.VTDNav;

public class VtdUtils {

	static Logger logger = Logger.getLogger(VtdUtils.class);
	
	/**
	 * 获取所有子节点的名称和文本值，适用于只包含文本的节点
	 * @return
	 * @throws NavException 
	 */
	public static Map<String, String> fetchAllChilds(VTDNav vn) throws NavException{
		Map<String, String> childs = new HashMap<String, String>();
		//节点索引
		int index = 0;
		int textIndex = 0;//the text index
		if (vn.toElement(VTDNav.FC)){ 
	 		do{
   		 		/**
   		 		 * 当前节点索引
   		 		 */
   		 		index = vn.getCurrentIndex();
   		 		textIndex = vn.getText();
   		 		/**
   		 		 * 节点名称
   		 		 */
   		 		String tagName = vn.toString(index);
   		 		String value = vn.toString(textIndex);
   		 		if(logger.isDebugEnabled()){
   		 			logger.debug("tagName :"+tagName + "  value :"+value);
   		 		}
   		 		childs.put(tagName, value);
	 		}while (vn.toElement(VTDNav.NS));
	 		//回到本节点
	 		vn.toElement(VTDNav.P);
  		}
		childs = (childs.keySet() == null || childs.keySet().size() < 1) ? null : childs;
		return childs;
	}
	
	/**
	 * 获取节点文本值
	 * @return
	 * @throws NavException 
	 */
	public static String getText(VTDNav vn) throws NavException{
		int textIndex = vn.getText();
	 	String value = vn.toString(textIndex).trim();
	 	return value;
	}
	
}
