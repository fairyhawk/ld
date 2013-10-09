package com.luode.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StringKit;


public class PaikeSysSend {
	private static final Logger LOGGER = Logger.getLogger(PaikeSysSend.class);

	public static Map<String, Map> queryStudent(){
		Map<String, Map> userMap = new HashMap<String, Map>();
		try 
		{	URL url = null;
			url = new URL("http://paike.luode.org/account/getStudentForWordSys");
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.flush(); 
			out.close();
			connection.connect();
			InputStream is = connection.getInputStream();
			StringBuilder buffer = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(is, "utf-8"));
			String tmpStr = null;
			while ((tmpStr = bufferedReader.readLine()) != null) {
				buffer.append(tmpStr);
			}
			bufferedReader.close();
			String str = buffer.toString();
			LOGGER.info("返回消息" + str);
			JSONObject json = JSONObject.parseObject(str);
			JSONArray paikeusers = (JSONArray) json.get("courses");
			for(Object obj : paikeusers){
				Map<String, String> users = new HashMap<String, String>();
				List list = (List) obj;
				if(list.get(1) != null){
					String userName =list.get(1).toString();					
					users.put("sMobile", list.get(2) == null ? "":list.get(2).toString());
					users.put("pMobile", list.get(3) == null ? "":list.get(3).toString());
					users.put("lastClassDate", list.get(4) == null ? "":list.get(4).toString());
					userMap.put(userName, users);
				}
			}
			out.close();
			return userMap;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

	public static void main(String[] args) {
		
		Map o = PaikeSysSend.queryStudent();
		Map u = (Map) o.get("王子耕");
		if(u == null){
		LOGGER.info("dd");
		}else{
			String s = (String) u.get("sMobile");
			String p = (String) u.get("pMobile");
			LOGGER.info("S:"+s+"===p:"+p);
		}
	}
}
