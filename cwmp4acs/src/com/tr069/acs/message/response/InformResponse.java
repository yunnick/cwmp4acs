package com.tr069.acs.message.response;

import com.tr069.acs.message.request.InformMessage;
import com.tr069.common.message.SoapMessage;

public class InformResponse {

	private int maxEnvelopes;

	public int getMaxEnvelopes() {
		return maxEnvelopes;
	}

	public void setMaxEnvelopes(int maxEnvelopes) {
		this.maxEnvelopes = maxEnvelopes;
	}
	public static void main(String[] args){
		SoapMessage<InformMessage, InformResponse> s = new InformMessage();
		s.parse("");
	}
}
