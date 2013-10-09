package com.luode.sms;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @ClassName  SmsThread
 * @package com.fairyhawk.service.sms
 * @description
 * @author  liuqinggang
 * @Create Date: 2013-3-20 下午11:57:14
 * 
 */
public class SmsThread implements Runnable {

	private  SendSMS sendSMS;
	/**
	 * 
	 */
	public SmsThread(SendSMS sendSMS) {
		this.sendSMS=sendSMS;
	}
	@Override
	public void run() {
		Map<String, String> map = sendSMS.sendSMS();
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}

}
