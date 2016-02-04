package com.tr069.common.handler;

/**
 * 组装对CPE的方法调用，构建soap报文，主要有以下方法：
 * <dl>
 * <dd>1、SetParameterValues</dd>
 * <dd>2、GetParameterValues</dd>
 * <dd>3、GetParameterNames </dd>
 * <dd>4、SetParameterAttributes</dd>
 * <dd>5、GetParameterAttributes </dd>
 * <dd>6、AddObject </dd>
 * <dd>7、DeleteObject</dd>
 * <dd>8、Download </dd>
 * <dd>9、Reboot</dd>
 * <dd>10、Upload</dd>
 * </dl>
 *
 */
public interface ResponseBuilder<T> {

	/**
	 * 构建xml字符串
	 * @param message
	 * @return
	 */
	public String build(T message);
}
