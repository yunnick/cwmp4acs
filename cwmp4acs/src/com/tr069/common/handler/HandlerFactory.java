package com.tr069.common.handler;

import com.tr069.acs.constants.RpcMessageType.MessageType;
import com.tr069.acs.handler.InformHandler;
import com.tr069.acs.handler.TransferCompleteHandler;
import com.tr069.acs.message.request.InformMessage;
import com.tr069.acs.message.request.TransferComplete;
import com.tr069.acs.message.response.InformResponse;
import com.tr069.acs.message.response.TransferCompleteResponse;
import com.tr069.common.message.SoapFault;
import com.tr069.common.message.SoapHeader;
import com.tr069.cpe.handler.DownloadHandler;
import com.tr069.cpe.handler.FactoryResetHandler;
import com.tr069.cpe.handler.GetParameterAttributesHandler;
import com.tr069.cpe.handler.GetParameterNamesHandler;
import com.tr069.cpe.handler.GetParameterValuesHandler;
import com.tr069.cpe.handler.RebootHandler;
import com.tr069.cpe.handler.SetParameterAttributesHandler;
import com.tr069.cpe.handler.SetParameterValuesHandler;
import com.tr069.cpe.handler.UploadHandler;
import com.tr069.cpe.message.request.Download;
import com.tr069.cpe.message.request.FactoryReset;
import com.tr069.cpe.message.request.GetParameterAttributes;
import com.tr069.cpe.message.request.GetParameterNames;
import com.tr069.cpe.message.request.GetParameterValues;
import com.tr069.cpe.message.request.Reboot;
import com.tr069.cpe.message.request.SetParameterAttributes;
import com.tr069.cpe.message.request.SetParameterValues;
import com.tr069.cpe.message.request.Upload;
import com.tr069.cpe.message.response.DownloadResponse;
import com.tr069.cpe.message.response.FactoryResetResponse;
import com.tr069.cpe.message.response.GetParameterAttributesResponse;
import com.tr069.cpe.message.response.GetParameterNamesResponse;
import com.tr069.cpe.message.response.GetParameterValuesResponse;
import com.tr069.cpe.message.response.RebootResponse;
import com.tr069.cpe.message.response.SetParameterAttributesResponse;
import com.tr069.cpe.message.response.SetParameterValuesResponse;
import com.tr069.cpe.message.response.UploadResponse;


/**
 * 解析器工厂类
 *
 */
public class HandlerFactory {

	/**
	 * 头部解析器与构造器
	 */
	private static RequestParser<SoapHeader> headerParser = new HeadHandler();
	private static ResponseBuilder<SoapHeader> headerBuilder = new HeadHandler();
	/**
	 * inform报文解析器与构造器
	 */
	private static RequestParser<InformMessage> informParser = new InformHandler();
	private static ResponseBuilder<InformResponse> informBuilder = new InformHandler();
	/**
	 * TransferComplete报文解析器与构造器
	 */
	private static RequestParser<TransferComplete> transferCompleteParser = new TransferCompleteHandler();
	private static ResponseBuilder<TransferCompleteResponse> transferCompleteBuilder = new TransferCompleteHandler();
	/**
	 * Download报文解析器与构造器
	 */
	private static RequestParser<DownloadResponse> downloadParser = new DownloadHandler();
	private static ResponseBuilder<Download> downloadBuilder = new DownloadHandler();
	/**
	 * FactoryReset报文解析器与构造器
	 */
	private static RequestParser<FactoryResetResponse> factoryResetParser = new FactoryResetHandler();
	private static ResponseBuilder<FactoryReset> factoryResetBuilder = new FactoryResetHandler();
	/**
	 * GetParameterAttributes
	 */
	private static RequestParser<GetParameterAttributesResponse> getParameterParser = new GetParameterAttributesHandler();
	private static ResponseBuilder<GetParameterAttributes> getParameterBuilder = new GetParameterAttributesHandler();
	/**
	 * GetParameterNames
	 */
	private static RequestParser<GetParameterNamesResponse> getParameterNamesParser = new GetParameterNamesHandler();
	private static ResponseBuilder<GetParameterNames> getParameterNamesBuilder = new GetParameterNamesHandler();
	/**
	 * GetParameterNames
	 */
	private static RequestParser<GetParameterValuesResponse> getParameterValuesParser = new GetParameterValuesHandler();
	private static ResponseBuilder<GetParameterValues> getParameterValuesBuilder = new GetParameterValuesHandler();
	/**
	 * Reboot
	 */
	private static RequestParser<RebootResponse> rebootParser = new RebootHandler();
	private static ResponseBuilder<Reboot> rebootBuilder = new RebootHandler();
	/**
	 * SetParameterAttributes
	 */
	private static RequestParser<SetParameterAttributesResponse> setParameterAttributesParser = new SetParameterAttributesHandler();
	private static ResponseBuilder<SetParameterAttributes> setParameterAttributesBuilder = new SetParameterAttributesHandler();
	/**
	 * SetParameterValues
	 */
	private static RequestParser<SetParameterValuesResponse> setParameterValuesParser = new SetParameterValuesHandler();
	private static ResponseBuilder<SetParameterValues> setParameterValuesBuilder = new SetParameterValuesHandler();
	/**
	 * Upload
	 */
	private static RequestParser<UploadResponse> uploadParser = new UploadHandler();
	private static ResponseBuilder<Upload> uploadBuilder = new UploadHandler();

	/**
	 * Fault
	 */
	private static RequestParser<SoapFault> soapFaultParser = new SoapFaultHandler();
	private static ResponseBuilder<SoapFault> soapFaultBuilder = new SoapFaultHandler();

	/**
	 * 用于制造各种具体的解析器
	 * @return
	 */
	@SuppressWarnings({"rawtypes" })
	public static RequestParser generateParser(MessageType type){
		switch (type) {
		case HEAD:
			return headerParser;
		case INFORM:
			return informParser;
		case TRANSFER_COMPLETE:
			return transferCompleteParser;
		case ADD_OBJECT:
			return null;
		case DELETE_OBJECT:
			return null;
		case DOWNLOAD:
			return downloadParser;
		case FACTORY_RESET:
			return factoryResetParser;
		case GET_PARAMETER_ATTRIBUTES:
			return getParameterParser;
		case GET_PARAMETER_NAMES:
			return getParameterNamesParser;
		case GET_PARAMETER_VALUES:
			return getParameterValuesParser;
		case REBOOT:
			return rebootParser;
		case SET_PARAMETER_ATTRIBUTES:
			return setParameterAttributesParser;
		case SET_PARAMETER_VALUES:
			return setParameterValuesParser;
		case UPLOAD:
			return uploadParser;
		case FAULT:
			return soapFaultParser;
		}
		return null;
	}

	/**
	 * 用于制造各种具体的构造器
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static ResponseBuilder generateBuilder(MessageType type){
		switch (type) {
		case HEAD:
			return headerBuilder;
		case INFORM:
			return informBuilder;
		case TRANSFER_COMPLETE:
			return transferCompleteBuilder;
		case ADD_OBJECT:
			return null;
		case DELETE_OBJECT:
			return null;
		case DOWNLOAD:
			return downloadBuilder;
		case FACTORY_RESET:
			return factoryResetBuilder;
		case GET_PARAMETER_ATTRIBUTES:
			return getParameterBuilder;
		case GET_PARAMETER_NAMES:
			return getParameterNamesBuilder;
		case GET_PARAMETER_VALUES:
			return getParameterValuesBuilder;
		case REBOOT:
			return rebootBuilder;
		case SET_PARAMETER_ATTRIBUTES:
			return setParameterAttributesBuilder;
		case SET_PARAMETER_VALUES:
			return setParameterValuesBuilder;
		case UPLOAD:
			return uploadBuilder;
		case FAULT:
			return soapFaultBuilder;
		}
		return null;
	}
}
