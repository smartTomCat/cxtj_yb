package com.yinhai.cxtj.admin.util;

import java.lang.reflect.Method;

/** 
 * base64工具类
 * @author:
 * @Copyright: 2013-2014 银海软件所有
 * @date: 2015-12-13 下午09:30:31
 * @vesion: 1.0
 */
public class Base64Util {
	/***
	 * encode by Base64 加密
	 */
	public static String encodeBase64(String input) throws Exception{
		if(null != input && !"".equals(input)){
			Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
			Method mainMethod= clazz.getMethod("encode", byte[].class);
			mainMethod.setAccessible(true);
			Object retObj=mainMethod.invoke(null, new Object[]{input.getBytes()});
			return (String)retObj;
		}else{
			return input;
		}		
	}
	/***
	 * decode by Base64 解密
	 */
	public static String decodeBase64(String input) throws Exception{
		if(null != input && !"".equals(input)){
			Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
			Method mainMethod= clazz.getMethod("decode", String.class);
			mainMethod.setAccessible(true);
			byte[] retObj= (byte[])mainMethod.invoke(null, input);
			return new String(retObj);
		}else{
			return input;
		}
	}	

	public static void main(String[] args) {
		try {
			String ddd = Base64Util.encodeBase64("!@1%%%23^&&&&%");
			System.out.println(ddd);
			String ttt= Base64Util.decodeBase64(ddd);
			System.out.println(ttt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
