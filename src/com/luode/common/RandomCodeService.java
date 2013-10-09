package com.luode.common;

import com.jfinal.kit.StringKit;
import com.luode.util.MD5;

public abstract class RandomCodeService {
	
	public static String encode(String randomCode) {	
		return MD5.encryption32Uppercase(MD5.encryption32Uppercase(randomCode));
	}
	
	public static boolean validate(String inputRandomCode,String rightRandomCode){
		if (StringKit.isBlank(inputRandomCode))
			return false;
		try{
			inputRandomCode = encode(inputRandomCode);
			return inputRandomCode.equals(rightRandomCode);
		}catch(Exception e){
			return false;
		}
	}
}
