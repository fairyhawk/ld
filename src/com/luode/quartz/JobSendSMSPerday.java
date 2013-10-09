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
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.luode.member.model.Member;
import com.luode.sms.SendSMS;
import com.luode.util.DateHelper;
import com.luode.util.PaikeSysSend;

/**
 * 类描述：
 * 
 * @author David
 * @time 2013-9-22 上午10:56:30
 * 
 */
public class JobSendSMSPerday  extends Controller implements Job{
	private static final Logger logger = Logger.getLogger(JobSendSMSPerday.class);
	static int callTime = 0;
	static int l = 0;
	int[] ii = new int[] {};

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
			String date = DateHelper.getFormatDate(new Date());
//			String date = "2013-06-07";
			int week = DateHelper.dayForWeek(date);
			Map paikeUsers = PaikeSysSend.queryStudent();
			String testSql = "SELECT lm.member_name,s.dcs,s.zql " + "FROM letter_member lm," + "(SELECT lmt.member_id , (SUM(IFNULL(lmt.right_num,0))+SUM(IFNULL(lmt.wrong_num,0))) dcs," + "IF((SUM(IFNULL(lmt.right_num,0)))=0,0,ROUND(SUM(IFNULL(lmt.right_num,0))/(SUM(IFNULL(lmt.right_num,0))+SUM(IFNULL(lmt.wrong_num,0))),2)*100) zql "
					+ "FROM letter_member_test lmt WHERE DATE_FORMAT(lmt.start_time,'%Y-%m-%d')='" + date + "' AND lmt.test_finish=1 GROUP BY lmt.member_id) s WHERE lm.id=s.member_id";
			List<Record> testList = Db.find(testSql);
			String noTestSql = "SELECT * FROM letter_member lm WHERE lm.sms_remind = 1 AND DATE_FORMAT(lm.last_test_date,'%Y-%m-%d') < '"+date+"'";
			List<Member> noTestlist = Member.dao.find(noTestSql);
			String smsInfo = "";
			String sTel = "";
			String pTel = "";
			String testTel = "13260008070";//测试使用的短信下发号码
			int wCount = 0;
			double wAccuracy = 0.0;
			String studentName = "";
			int tindex = 0;//测试
			int nindex = 0;//未测
			int remindWeek = 7;//周日提示请设置为7
			for (Record record : testList) {// 当天有测试用户
				studentName = record.getStr("member_name");
				Map stuMap = (Map) paikeUsers.get(studentName);
				if(stuMap == null){
					logger.info("测试用户："+studentName+"不在排课系统中");
					continue;
				}else{
					sTel = (String) stuMap.get("sMobile");
					pTel = (String) stuMap.get("pMobile");
					if(StringKit.notBlank(sTel)){
						tindex++;
						wCount = record.getNumber("dcs").intValue();
						wAccuracy = record.getNumber("zql").doubleValue();
						smsInfo = "亲爱的小伙伴「"+studentName+"」同学。您今天单词测试"+wCount+"个，正确率"+wAccuracy+"%，请继续加油！【罗德国际教育】";
						SendSMS.send(smsInfo, sTel);//给学生下发sTel
						logger.info("测试用户：发送第"+tindex+"条短信："+smsInfo);
						if(StringKit.notBlank(pTel)&&!sTel.equals(pTel)){
							tindex++;
							smsInfo = "亲爱的「"+studentName+"」同学家长您好。今天您的孩子测试了"+wCount+"个单词，正确率"+wAccuracy+"%，请继续为您的孩子加油！【罗德国际教育】";
							SendSMS.send(smsInfo, pTel);//给家长发pTel
							logger.info("测试用户：发送第"+tindex+"条短信："+smsInfo);
						}
					}else{
						continue;
					}
				}
			}
			
			if(week==remindWeek){
				String testWeekSql = "SELECT lm.member_name,s.dcs,s.zql " + "FROM letter_member lm," + "(SELECT lmt.member_id , (SUM(IFNULL(lmt.right_num,0))+SUM(IFNULL(lmt.wrong_num,0))) dcs," + "IF((SUM(IFNULL(lmt.right_num,0)))=0,0,ROUND(SUM(IFNULL(lmt.right_num,0))/(SUM(IFNULL(lmt.right_num,0))+SUM(IFNULL(lmt.wrong_num,0))),2)*100) zql "
						+ "FROM letter_member_test lmt WHERE DATE_FORMAT(lmt.start_time,'%Y-%m-%d')>=DATE_ADD(DATE_SUB('"+date+"',INTERVAL 1 WEEK),INTERVAL 1 DAY) AND lmt.test_finish=1 GROUP BY lmt.member_id) s WHERE lm.id=s.member_id";
				List<Record> testWeekList = Db.find(testWeekSql);
				for (Record record : testWeekList) {// 本周测试用户
					studentName = record.getStr("member_name");
					Map stuMap = (Map) paikeUsers.get(studentName);
					if(stuMap == null){
						logger.info("测试用户："+studentName+"不在排课系统中");
						continue;
					}else{
						sTel = (String) stuMap.get("sMobile");
						pTel = (String) stuMap.get("pMobile");
						if(StringKit.notBlank(sTel)){
							tindex++;
							wCount = record.getNumber("dcs").intValue();
							wAccuracy = record.getNumber("zql").doubleValue();
							smsInfo = "亲爱的小伙伴「"+studentName+"」同学。本周你共测试单词"+wCount+"个，正确率"+wAccuracy+"%，请继续加油！【罗德国际教育】";
							SendSMS.send(smsInfo, sTel);//给学生下发
							logger.info("测试用户：发送第"+tindex+"条短信："+smsInfo);
							if(StringKit.notBlank(pTel)&&!sTel.equals(pTel)){
								tindex++;
								smsInfo = "亲爱的「"+studentName+"」同学家长您好。本周您的孩子测试了"+wCount+"个单词，正确率"+wAccuracy+"%，请继续为您的孩子加油！【罗德国际教育】";
								SendSMS.send(smsInfo, pTel);//给家长发
								logger.info("测试用户：发送第"+tindex+"条短信："+smsInfo);
							}
						}else{
							continue;
						}
					}
				}
			}
			
			for (Member member : noTestlist) {// 组合数据
				studentName = member.getStr("member_name");
				Date lastTestDate = member.getTimestamp("last_test_date");
				Map stuMap = (Map) paikeUsers.get(studentName);
				if(stuMap == null){
					logger.info("未测试用户："+studentName+"不在排课系统中");
					continue;
				}else{
					sTel = (String) stuMap.get("sMobile");
					pTel = (String) stuMap.get("pMobile");
					if(StringKit.notBlank(sTel)){
						Date ldate = DateHelper.getDateFromStr((String) stuMap.get("lastClassDate"));//最后的上课日期
						int days = DateHelper.getDayInterval(DateHelper.getDateFromStr(date), ldate);
						if(days>0){//还在上课期内
							nindex++;
							smsInfo = "亲爱的小伙伴「"+studentName+"」同学。今天你没有测试单词，单词依旧很重要，事情再多也抽个时间补测下吧！【罗德国际教育】";
							SendSMS.send(smsInfo, sTel);//给学生下发
							logger.info("未测试用户：发送第"+nindex+"条短信："+smsInfo);
							if(StringKit.notBlank(pTel)&&!sTel.equals(pTel)){
								nindex++;
								smsInfo = "亲爱的「"+studentName+"」同学家长您好。今天您的孩子没有测试单词，请继续为您的孩子加油吧！【罗德国际教育】";
								SendSMS.send(smsInfo, pTel);//给家长发
								logger.info("未测试用户：发送第"+nindex+"条短信："+smsInfo);
							}
						}else{
							if(week == remindWeek){//如果是周日则进行为测试用户提醒
								int noTestDays = DateHelper.getDayInterval(lastTestDate,DateHelper.getDateFromStr(date));
								if(noTestDays>=28){//一个月没有进行测试用户
									nindex++;
									smsInfo = "亲爱的小伙伴「"+studentName+"」同学。如果前方依旧有考试在等你，努力从今天开始，只要用功今天开始也不晚，加油！【罗德国际教育】";
									SendSMS.send(smsInfo, sTel);//给学生下发
									logger.info("未测试用户：发送第"+nindex+"条短信："+smsInfo);
									member.set("sms_remind", 0).update();
									continue;
								}else if(noTestDays>=21){
									nindex++;
									smsInfo = "亲爱的小伙伴「"+studentName+"」同学。三周没有看到你的身影了，如果需要每天提醒下你背单词，请告诉我们，我们会每天提醒你，直到你成为单词小狂人！【罗德国际教育】";
									SendSMS.send(smsInfo, sTel);//给学生下发
									logger.info("未测试用户：发送第"+nindex+"条短信："+smsInfo);
									continue;
								}else if(noTestDays>=14){
									nindex++;
									smsInfo = "亲爱的小伙伴「"+studentName+"」同学。两周不见你测单词了，拿起单词书，背起来吧！【罗德国际教育】";
									SendSMS.send(smsInfo, sTel);//给学生下发
									logger.info("未测试用户：发送第"+nindex+"条短信："+smsInfo);
									continue;
								}else if(noTestDays>=7){
									nindex++;
									smsInfo = "亲爱的小伙伴「"+studentName+"」同学。你已经一周没有测单词了，单词是高分的基础，背背单词，好处多多，请开始背单词吧！【罗德国际教育】";
									SendSMS.send(smsInfo, sTel);//给学生下发
									logger.info("未测试用户：发送第"+nindex+"条短信："+smsInfo);
									continue;
								}else{
									logger.info("「"+studentName+"」同学没有签约，一周后发短信提醒。");
									continue;
								}
							}
						}
					}else{
						continue;
					}
				}
			}
			if(week == remindWeek){//如果是周日则进行为测试用户提醒
				smsInfo = "单词系统：今日发送测试用户短信提醒"+tindex+"条,本周未测试提醒"+nindex+"条。【罗德国际教育】";
			}else{
				smsInfo = "单词系统：今日发送测试用户短信提醒"+tindex+"条,未测试提醒"+nindex+"条【罗德国际教育】";
			}
			SendSMS.send(smsInfo, testTel);//发送确认短信给刘国强
		} catch (Exception e) {
			logger.error(e);
			SendSMS.send("单词系统：排课系统连接超时，短信提醒发送失败。", "13260008070");//发送确认短信给刘国强
		}
	}
}
