package com.tr069.acs.constants;

public class AcsFaultCode {

	/**
	 * 服务器端错误代码
	 * @author leipeng
	 *
	 */
	public enum AcsFault {
		NOT_SUPPORT_METHOD_8000(8000, "方法不支持"),
		REQUEST_DENI_8001(8001, "拒绝请求（未指明原因）"),
		INTERNAL_ERROR_8002(8002, "内部错误"),
		INVALID_PARAM_8003(8003, "无效参数"),
		RESOURCES_TRANSFINITE_8004(8004, "资源超限"),
		REPEAT_QEQUEST_8005(8005, "重试请求");

		private int code;
		private String desc;

		private AcsFault(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}
		public static AcsFault getEnum(int code){
			for(AcsFault a : values()){
				if(a.code == code){
					return a;
				}
			}
			return null;
		}

		public int getCode() {
			return code;
		}
		public void setCode(int code) {
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
