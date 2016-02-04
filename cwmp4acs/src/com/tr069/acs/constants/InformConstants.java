package com.tr069.acs.constants;

import org.apache.commons.lang.StringUtils;

public class InformConstants {

	/**
	 * 服务器Inform方法事件类型(EventCode)定义
	 * 
	 */
	public enum EventCode {
		BOOT_STRAP("0 BOOTSTRAP"), 
		BOOT("1 BOOT"), 
		PERIODIC("2 PERIODIC"),
		SCHEDULED("3 SCHEDULED"), 
		VALUE_CHANGE("4 VALUE CHANGE"), 
		KICKED("5 KICKED"), 
		CONNECTION_REQUEST("6 CONNECTION REQUEST"),
		TRANSFER_COMPLETE("7 TRANSFER COMPLETE"), 
		DIAGNOSTICS_COMPLETE("8 DIAGNOSTICS COMPLETE"),
		REQUEST_DOWNLOAD("9 REQUEST DOWNLOAD"),
		AUTONOMOUS_TRANSFER_COMPLETE("10 AUTONOMOUS TRANSFER COMPLETE"),
		REBOOT("M Reboot"),
		DOWNLOAD("M Download"),
		ScheduleInform("M ScheduleInform"),
		UPLOAD("M UPLOAD"),
		VALUE_CONFIG("X CU VALUE CONFIG"),
		ALARM("X CU Alarm");

		private String value;

		private EventCode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		public static EventCode getEnum(String event){
			for(EventCode a : values()){
				if(StringUtils.equalsIgnoreCase(event, a.getValue())){
					return a;
				}
			}
			return null;
		}
	}
	
	public enum CommandKey {
		ScheduledInform("ScheduledInform"), 
		Reboot("Reboot"), 
		Download("Download"),
		Upload("Upload");
		
		private String value;
		private CommandKey(String value){
			this.value = value;
		}
		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		public static CommandKey getEnum(String key){
			for(CommandKey a : values()){
				if(StringUtils.equalsIgnoreCase(key, a.getValue())){
					return a;
				}
			}
			return null;
		}
	}
}
