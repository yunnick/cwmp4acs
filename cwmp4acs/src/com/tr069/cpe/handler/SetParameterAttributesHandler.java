package com.tr069.cpe.handler;

import com.tr069.common.constants.CommonContants.NameSpace;
import com.tr069.common.handler.RequestParser;
import com.tr069.common.handler.ResponseBuilder;
import com.tr069.common.util.CwmpSoapUtils;
import com.tr069.cpe.message.request.SetParameterAttributes;
import com.tr069.cpe.message.response.SetParameterAttributesResponse;

public class SetParameterAttributesHandler implements RequestParser<SetParameterAttributesResponse>,
		ResponseBuilder<SetParameterAttributes> {

	@Override
	public String build(SetParameterAttributes message) {
		//ENVELOPE命名空间
		String envNs = NameSpace.ENVELOPE_NAME_SPACE.getResponseNs();
		/**
		 * 拼接body
		 */
		StringBuffer body = new StringBuffer();
		body.append("<" + envNs + ":Body>");
		body.append("<cwmp:SetParameterAttributes>");
		/**
		 * ParameterAttributes
		 */
		body.append(CwmpSoapUtils.buildSetParameterAttributeList(message.getParameterList()));
		
		body.append("</cwmp:SetParameterAttributes>");
		body.append("</" + envNs + ":Body>");
		return body.toString();
	}

	@Override
	public SetParameterAttributesResponse parseXml(String xml) {
		return new SetParameterAttributesResponse();
	}

}
