package com.tr069.cpe.constants;

import org.apache.commons.lang.StringUtils;

public class CpeFaultCode {
	/**
	 * CPE端错误代码
	 * @author leipeng
	 *
	 */
	public enum CpeFault {
		NOT_SUPPORT_METHOD_9000("9000", "方法不支持"),
		REQUEST_DENI_9001("9001", "拒绝请求（未指明原因）"),
		INTERNAL_ERROR_9002("9002", "内部错误"),
		INVALID_PARAM_9003("9003", "无效参数"),
		RESOURCES_TRANSFINITE_9004("9004", "资源超限"),
		INVALID_PARAM_NAME_9005("9005", "无效参数名"),
		INVALID_PARAM_TYPE_9006("9006", "无效参数类型"),
		INVALID_PARAM_VALUE_9007("9007", "无效参数值"),
		SET_UNWITABLE_PARAM_9008("9008", "试图设置不可写的参数"),
		NOTIFICATION_DENI_9009("9009", "Notification请求被拒"),
		DOWNLOAD_FAIL_9010("9010", "下载失败"),
		UPLOAD_FAIL_9011("9011", "上载失败"),
		FILE_SERVER_ACCESS_DENI_9012("9012", "文件传输服务器认证失败"),
		FILE_TRANSFER_PROTOCAL_NOT_SUPPORT_9013("9013", "文件传输的协议不支持");

		private String code;
		private String desc;

		private CpeFault(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}
		public static CpeFault getEnum(String code){
			if (code != null){
				for(CpeFault a : values()){
					if(StringUtils.equals(a.code, code)){
						return a;
					}
				}
			}
			return null;
		}

		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
	}
}
