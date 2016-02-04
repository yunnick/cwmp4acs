package com.tr069.common.util;


public class MatchUtils {
	
	/**
	 * 获取字符串中第一个有效数字,包括符号,返回其字符串
	 * @param input
	 * @return
	 */
	public static String getFirstNum(String input){
		String rtNum = "";
		//上一个字节
		char lastChar = '.';
		char[] charArray = input.toCharArray();
		//遍历到数字的标识
		boolean getNum = false;
		//数字起始和结束位置
		int[] numIndex = {-1, -1};
		//位置计数
		int posiCount = 0;
		for(char c : charArray){
			//获取到第一个数字 '0'=48 '9'=57
			if((int)c > 47 && (int)c < 58 && !getNum){
				getNum = true;
				if((int)lastChar == 45){
					//如果是负号
					numIndex[0] = posiCount-1;
				}else{
					numIndex[0] = posiCount;
				}
			}
			//获取到第一个字符串
			if(!((int)c > 47 && (int)c < 58) && getNum){
				numIndex[1] = posiCount;
				break;
			}
			lastChar = c;
			posiCount++;
		}
		if(numIndex[0] != -1 && numIndex[1] != -1 && numIndex[0] < numIndex[1]){
			rtNum = input.substring(numIndex[0], numIndex[1]);
		}
		return rtNum;
	}
	
	public static void main(String[] args){
		String userInfo = "{\"message\":\"成功\",\"status\":1,\"userInfo\":{\"accountId\":\"zhangxh@channelsoft.com\",\"appId\":\"voip\",\"cn\":\" \",\"country\":\"\",\"favTags\":[],\"introduction\":\"\",\"mail\":\"zhangxh@channelsoft.com\",\"modifyDate\":\"1356417702811\",\"province\":\"\",\"registerDate\":\"1356417702811\",\"sn\":\" \",\"type\":\"1\",\"uid\":\"\"}}";
		/*System.out.println(matchCalllogTime("11117788990022"));
		System.out.println(SortType.valueOf("ASC"));
		System.out.println(":"+getUidfromUserInfo(userInfo)+":");*/
		System.out.println(":"+getFirstNum(userInfo));
		char a = '-';
		System.out.println((int)a);
	}
}
