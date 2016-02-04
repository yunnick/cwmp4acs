package com.tr069.common.constants;

public class CommonContants {

	/**
	 * rpc报文命名空间
	 * 信包名称空间标识“http://schemas.xmlsoap.org/soap/envelope/”
	 * 序列号名称空间标识“http://schemas.xmlsoap.org/soap/encoding/(查看资料发现请求报文和返回报文的别名不同)”
	 * CPE WAN管理协议定义的元素和属性名称空间：urn:dslforum-org:cwmp-1-0”
	 * xsi名称空间：http://www.w3.org/2001/XMLSchema-instance
	 * xsd名称空间：http://www.w3.org/2001/XMLSchema
	 * @author leipeng
	 *
	 */
	public enum NameSpace {
		ENVELOPE_NAME_SPACE("soap", "xmlns:soap", "http://schemas.xmlsoap.org/soap/envelope/"),
		//请求报文对应的别名
		ENCODING_NAME_SPACE("", "soap:encodingStyle", "http://schemas.xmlsoap.org/soap/encoding/"),
		//返回报文对应的别问，与上面的有区别
		SOAP_ENC_NAME_SPACE("SOAP-ENC", "xmlns:SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/"),
		CWMP_NAME_SPACE("cwmp", "xmlns:cwmp", "urn:dslforum-org:cwmp-1-0"),
		XSI_NAME_SPACE("xsi", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance"),
		XSD_NAME_SPACE("xsd", "xmlns:xsd", "http://www.w3.org/2001/XMLSchema");

		/**
		 * 返回soap报文是使用的soap命名空间别名
		 */
		private String responseNs;
		private String alias;
		private String desc;

		private NameSpace(String responseNs, String alias, String desc) {
			this.responseNs = responseNs;
			this.alias = alias;
			this.desc = desc;
		}
		
		public String getResponseNs() {
			return responseNs;
		}
		public void setResponseNs(String responseNs) {
			this.responseNs = responseNs;
		}

		public String getAlias() {
			return alias;
		}
		public void setAlias(String alias) {
			this.alias = alias;
		}
		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 数据类型描述可使用的数据结构</p>
	 * 具体数据类型见{@DataType}</p>
	 * 可根据需要添加
	 * @author Administrator
	 *
	 */
	public enum DataStruct{
		TYPE("type"),
		ARRAY_TYPE("arrayType");
		
		private String desc;

		private DataStruct(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	public enum DataType{
		STRING("string"),
		INT("int"),
		UNSIGNED_INT("unsignedInt"),
		BOOLEAN("boolean"),
		DATATIME("dataTime"),
		BASE64("base64");
		
		private String desc;

		private DataType(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

	}
}
