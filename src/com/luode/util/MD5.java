/**
 ****************************************************
 *  Copyright (c) 2011-2012 二六三网络通信股份有限公司 
 ****************************************************
 * @Package com.net263.boss.util
 * @Title：MD5.java
 * @author:Administrator
 * @Date:2011-4-10 下午11:57:52
 */
package com.luode.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.UUID;

import sun.misc.BASE64Encoder;

/**
 * 类描述：
 * @author Administrator
 * @time 2011-4-10 下午06:57:52

 */
public class MD5 {

	/**
	 * 利用MD5进行加密
	 * 
	 * @param str待加密的字符串
	 * @return 加密后的字符串
	 * @throws NoSuchAlgorithmException没有这种产生消息摘要的算法
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String encoderByMd5(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5"); // 确定计算方法
			BASE64Encoder base64en = new BASE64Encoder();
			return base64en.encode(md5.digest(str.getBytes("utf-8")));// 返回加密后的字符串
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * 方法描述：32位大写
	 * @author David
	 * @time 2013-4-7 下午4:23:21
	 *
	 * @param plainText
	 * @return
	 */
	public static String encryption32Uppercase(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
 
            int i;
 
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
 
            re_md5 = buf.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5.toUpperCase();
    }
	public final static String getMD5(String s){
		
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd','e', 'f'};
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>>4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		}
		catch (Exception e){
			return null;
		}
	}
	public static void main(String[] args) throws ParseException{
//		String a = UUID.randomUUID().toString().replace("-", "");
//        System.out.println(a);
		
		String s = "/system";
		String b = "/system/query";
		boolean a = s.contains(b);
		System.out.print(a);
	}
}
