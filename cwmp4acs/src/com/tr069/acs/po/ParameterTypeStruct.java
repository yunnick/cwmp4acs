package com.tr069.acs.po;

import com.tr069.common.constants.CommonContants.DataStruct;
import com.tr069.common.constants.CommonContants.DataType;
import com.tr069.common.constants.CommonContants.NameSpace;

public class ParameterTypeStruct {

	/**
	 * 数据使用的数据结构普通类型（type）或数组（arrayType）
	 */
	private DataStruct dataStruct;
	/**
	 * 数据结构使用的命名空间
	 */
	private NameSpace dataStructNs;
	/**
	 * 数据类型
	 */
	private DataType type;
	/**
	 * 数据类型所在命名空间
	 */
	private NameSpace typeNs;
	/**
	 * 数据长度：</p>
	 * 普通类型（type）表示数据长度，如String长度，Base64编码的二进制数的长度，可缺省</p>
	 * 数组类型（arrayType）表示数组元素个数
	 */
	private String length;
	/**
	 * 对于无符号整数型或整数型数据表示可取的最大值，可缺省
	 */
	private String max;
	/**
	 * 对于无符号整数型或整数型数据表示可取的最小值，可缺省
	 */
	private String min;
	public DataStruct getDataStruct() {
		return dataStruct;
	}
	public void setDataStruct(DataStruct dataStruct) {
		this.dataStruct = dataStruct;
	}
	public NameSpace getDataStructNs() {
		return dataStructNs;
	}
	public void setDataStructNs(NameSpace dataStructNs) {
		this.dataStructNs = dataStructNs;
	}
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		this.type = type;
	}
	public NameSpace getTypeNs() {
		return typeNs;
	}
	public void setTypeNs(NameSpace typeNs) {
		this.typeNs = typeNs;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
}
