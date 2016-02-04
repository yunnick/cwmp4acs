package com.tr069.cpe.handler;

import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.cpe.message.request.Reboot;
import com.tr069.cpe.message.response.RebootResponse;

public class RebootHandler implements RequestParser<RebootResponse>, ResponseBuilder<Reboot> {

	@Override
	public String build(Reboot message) {
		//ENVELOPE命名空间
		String envNs = NameSpace.ENVELOPE_NAME_SPACE.getResponseNs();
		/**
		 * 拼接body
		 */
		StringBuffer body = new StringBuffer();
		body.append("<" + envNs + ":Body>");
		body.append("<cwmp:Reboot>");
		/**
		 * CommandKey
		 */
		body.append("<CommandKey>" + message.getCommandKey() + "</CommandKey>");
		
		body.append("</cwmp:Reboot>");
		body.append("</" + envNs + ":Body>");
		return body.toString();
	}

	@Override
	public RebootResponse parseXml(String xml) {
		return new RebootResponse();
	}

}
