package com.tr069.common.handler;

/**
 * 处理来自CPE的方法调用，主要包括两个方法：
 * <dl>
 * <dd>1、inform 方法</dd>
 * <dd>2、TransferComplete 方法</dd>
 * </dl>
 * @author Administrator
 *
 */
public interface RequestParser<T> {

	/**
	 * 对soap请求消息的具体解析实现，由SoapMessage对象调用，返回header或具体的rpc方法对应的message
	 * @return
	 */
	public T parseXml(String xml);
}
