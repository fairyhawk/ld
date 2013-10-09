/**
 ************************************************************
 *  Copyright (c) 2011-2014 罗德国际教育 
 ************************************************************
 * @Package com.luode.quartz
 * @Title：JobReciteWord.java
 * @author: David
 * @Date:2013-9-22 上午10:56:30
 */
package com.luode.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.luode.sms.SendSMS;
import com.luode.sms.SmsThread;
import com.luode.util.MD5;
import com.luode.util.PaikeSysSend;

/**
 * 类描述：
 * @author David
 * @time 2013-9-22 上午10:56:30
 *
 */
public class JobSendSMSPerweek implements Job{
	private static final Logger logger = Logger.getLogger(JobSendSMSPerweek.class);
    static int callTime = 0;
    static int l = 0;
    int [] ii = new int[]{};

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
		int i = 0, j = 0;
		callTime++;
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " JobB works,callTime is: " + callTime);
		String[] arrs;
		String arrs2[];
		String msg = "";
		String mobile = "";
		try {
//			String date = DateHelper.getFormatDate(new Date());
			String date = "2013-09-29";
			Map paikeUsers = PaikeSysSend.queryStudent();
			String oneWeekSql = "SELECT * FROM letter_member lm WHERE lm.sms_remind = 1 AND DATE_FORMAT(lm.last_test_date,'%Y-%m-%d') <= DATE_ADD(DATE_SUB('"+date+"',INTERVAL 1 WEEK),INTERVAL 1 DAY)";
			String twoWeekSql = "SELECT * FROM letter_member lm WHERE lm.sms_remind = 1 AND DATE_FORMAT(lm.last_test_date,'%Y-%m-%d') <= DATE_ADD(DATE_SUB('"+date+"',INTERVAL 2 WEEK),INTERVAL 1 DAY)";
			String thrWeekSql = "SELECT * FROM letter_member lm WHERE lm.sms_remind = 1 AND DATE_FORMAT(lm.last_test_date,'%Y-%m-%d') <= DATE_ADD(DATE_SUB('"+date+"',INTERVAL 3 WEEK),INTERVAL 1 DAY)";
			String fouWeekSql = "SELECT * FROM letter_member lm WHERE lm.sms_remind = 1 AND DATE_FORMAT(lm.last_test_date,'%Y-%m-%d') <= DATE_ADD(DATE_SUB('"+date+"',INTERVAL 4 WEEK),INTERVAL 1 DAY)";
			String smsInfo = "";
			String sTel = "";
			String pTel = "";
			String studentName = "";
			int index = 0;
			List<Record> fouWeekList = Db.find(fouWeekSql);
			for (Record record : fouWeekList) {// 4个星期没有背过单词用户
				index++;
				studentName = record.getStr("member_name");
				Map stuMap = (Map) paikeUsers.get(studentName);
				if(stuMap == null){
					continue;
				}else{
					sTel = (String) stuMap.get("sMobile");
					pTel = (String) stuMap.get("pMobile");
					if(StringKit.notBlank(sTel)){
						smsInfo = "亲爱的罗德小伙伴"+studentName+"同学，如果前方依旧有考试在等你，努力从今天开始，只要用功今天开始也不晚，加油！【罗德国际教育】";
						SendSMS.send(smsInfo, "13488751040");//给学生下发sTel
						logger.info("测试用户：发送第"+index+"条短信："+smsInfo);
						if(StringKit.notBlank(pTel)&&!sTel.equals(pTel)){
							index++;
							smsInfo = "亲爱的"+studentName+"同学家长您好！您的孩子已有四星期没有使用罗德单词系统背单词了，请继续为您的孩子加油！【罗德国际教育】";
							SendSMS.send(smsInfo, "13488751040");//给家长发pTel
							logger.info("测试用户：发送第"+index+"条短信："+smsInfo);
						}
					}else{
						continue;
					}
				}
			}
			String updateSql = "UPDATE letter_member lm SET lm.sms_remind=0 WHERE lm.sms_remind = 1 AND DATE_FORMAT(lm.last_test_date,'%Y-%m-%d') <= DATE_ADD(DATE_SUB('"+date+"',INTERVAL 4 WEEK),INTERVAL 1 DAY)";
			Db.update(updateSql);
			List<Record> thrWeekList = Db.find(thrWeekSql);
			for (Record record : thrWeekList) {// 3个星期没有背过单词用户
				index++;
				studentName = record.getStr("member_name");
				Map stuMap = (Map) paikeUsers.get(studentName);
				if(stuMap == null){
					continue;
				}else{
					sTel = (String) stuMap.get("sMobile");
					pTel = (String) stuMap.get("pMobile");
					if(StringKit.notBlank(sTel)){
						smsInfo = "亲爱的罗德小伙伴"+studentName+"同学，如果前方依旧有考试在等你，努力从今天开始，只要用功今天开始也不晚，加油！【罗德国际教育】";
						SendSMS.send(smsInfo, "13488751040");//给学生下发
						logger.info("测试用户：发送第"+index+"条短信："+smsInfo);
						if(StringKit.notBlank(pTel)&&!sTel.equals(pTel)){
							index++;
							smsInfo = "亲爱的"+studentName+"同学家长您好！您的孩子已有三星期没有使用罗德单词系统背单词了，请继续为您的孩子加油！【罗德国际教育】";
							SendSMS.send(smsInfo, "13488751040");//给家长发
							logger.info("测试用户：发送第"+index+"条短信："+smsInfo);
						}
					}else{
						continue;
					}
				}
			}
			List<Record> twoWeekList = Db.find(twoWeekSql);
			for (Record record : twoWeekList) {// 2个星期没有背过单词用户
				index++;
				studentName = record.getStr("member_name");
				Map stuMap = (Map) paikeUsers.get(studentName);
				if(stuMap == null){
					continue;
				}else{
					sTel = (String) stuMap.get("sMobile");
					pTel = (String) stuMap.get("pMobile");
					if(StringKit.notBlank(sTel)){
						smsInfo = "亲爱的罗德小伙伴"+studentName+"同学，如果前方依旧有考试在等你，努力从今天开始，只要用功今天开始也不晚，加油！【罗德国际教育】";
						SendSMS.send(smsInfo, "13488751040");//给学生下发
						logger.info("测试用户：发送第"+index+"条短信："+smsInfo);
						if(StringKit.notBlank(pTel)&&!sTel.equals(pTel)){
							index++;
							smsInfo = "亲爱的"+studentName+"同学家长您好！您的孩子已有两星期没有使用罗德单词系统背单词了，请继续为您的孩子加油！【罗德国际教育】";
							SendSMS.send(smsInfo, "13488751040");//给家长发
							logger.info("测试用户：发送第"+index+"条短信："+smsInfo);
						}
					}else{
						continue;
					}
				}
			}
			List<Record> oneWeekList = Db.find(oneWeekSql);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
