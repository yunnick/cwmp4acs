package com.tr069.cpe.handler;

import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.cpe.message.request.FactoryReset;
import com.tr069.cpe.message.response.FactoryResetResponse;

public class FactoryResetHandler implements RequestParser<FactoryResetResponse>,
		ResponseBuilder<FactoryReset> {

	@Override
	public String build(FactoryReset message) {
		//ENVELOPE命名空间
		String envNs = NameSpace.ENVELOPE_NAME_SPACE.getResponseNs();
		/**
		 * 拼接body
		 */
		StringBuffer body = new StringBuffer();
		body.append("<" + envNs + ":Body>");
		body.append("<cwmp:FactoryReset>");
		body.append("</cwmp:FactoryReset>");
		body.append("</" + envNs + ":Body>");
		return body.toString();
	}

	@Override
	public FactoryResetResponse parseXml(String xml) {
		return new FactoryResetResponse();
	}


}
