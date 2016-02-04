package com.tr069.acs.constants;

import org.apache.commons.lang.StringUtils;


public class RpcMessageType {
	/**
	 * rpc报文的类型
	 * @author Administrator
	 *
	 */
	public enum MessageType{
		/**
		 * 暂不使用
		 */
		HEAD(""),
		INFORM("Inform"),
		TRANSFER_COMPLETE("TransferComplete"),
		/**
		 * 暂不使用
		 */
		ADD_OBJECT(""),
		/**
		 * 暂不使用
		 */
		DELETE_OBJECT(""),
		DOWNLOAD("DownloadResponse"),
		FACTORY_RESET("FactoryResetResponse"),
		GET_PARAMETER_ATTRIBUTES("GetParameterAttributesResponse"),
		GET_PARAMETER_NAMES("GetParameterNamesResponse"),
		GET_PARAMETER_VALUES("GetParameterValuesResponse"),
		REBOOT("RebootResponse"),
		SET_PARAMETER_ATTRIBUTES("SetParameterAttributesResponse"),
		SET_PARAMETER_VALUES("SetParameterValuesResponse"),
		UPLOAD("UploadResponse"),
		FAULT("Fault");
		
		private String rpc;

		public String getRpc() {
			return rpc;
		}

		public void setRpc(String rpc) {
			this.rpc = rpc;
		}

		private MessageType(String rpc) {
			this.rpc = rpc;
		}
		public static MessageType getEnum(String type){
			for(MessageType a : values()){
				if(StringUtils.equalsIgnoreCase(a.rpc, type)){
					return a;
				}
			}
			return null;
		}
	} 
}
