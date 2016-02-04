package com.tr069.acs.po;

import com.tr069.acs.constants.InformConstants.CommandKey;
import com.tr069.acs.constants.InformConstants.EventCode;

public class EventStruct {

	private EventCode eventCode;
	private CommandKey CommandKey;
	public EventCode getEventCode() {
		return eventCode;
	}
	public void setEventCode(EventCode eventCode) {
		this.eventCode = eventCode;
	}
	public CommandKey getCommandKey() {
		return CommandKey;
	}
	public void setCommandKey(CommandKey commandKey) {
		CommandKey = commandKey;
	}
	
}
