package com.luode.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.luode.util.MD5;

/**
 * @ClassName  CustomerApplyAction
 * @package com.fairyhawk.action.customer
 * @description
 * @author  
 * @Create Date: 2013-3-5 下午04:40:50
 * 
 */
public class SendController extends Controller{

	private static final Logger logger = Logger.getLogger(SendController.class);

    private EmailService emailService;
  

   
	/**
	 * @return the emailService
	 */
	public EmailService getEmailService() {
		return emailService;
	}
	/**
	 * @param emailService the emailService to set
	 */
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	/**
	 * @return the threadExecutor
	 */

	/**
	 * 发送短信
	 */
	public  void send(String msg,String mobile) {
			try {
				//查询 http://www.k8686.com/Proxy/Login.aspx
				SendSMS ss = new SendSMS();
				ss.setUsername("8004480314");
				ss.setPassword(MD5.encoderByMd5("liuguoqiang1984A"));
				ss.setMessage(msg);
				ss.setMobiles(mobile);
				ss.setServicesHost(" http://www.k8686.com");
				ss.setServicesRequestAddRess("/Api/BayouSmsApiEx.aspx");
				ss.setSmstype(0);
				ss.setTimerid("0");
				ss.setTimertype(0);
				SmsThread smsThread = new SmsThread(ss);
				smsThread.run();
			} catch (Exception e) {
				logger.error(e);
			}
	}
	
	public void sendCourseInfo()
	{
		try{
         String sql="select teacher.id,student.real_name as student_name,student.tel as student_tel,student.parents_tel parenttel,student.PARENTS_TEL_ACCEPT parentTelAccept,teacher.real_name teacher_name ,teacher.tel teacher_tel,classroom.name classname,time_rank.rank_name,time_rank.rank_type,course.course_name from courseplan"+
					" left join account student on courseplan.student_id=student.id"+
					" left join account teacher on courseplan.teacher_id=teacher.id "+
					" left join classroom on courseplan.room_id=classroom.id"+
					" left join time_rank on courseplan.TIMERANK_ID=time_rank.id"+
					" left join course on courseplan.course_id=course.id"+
					" where DATE_FORMAT(course_time,'%Y-%m-%d')=DATE_FORMAT(date_add(CURDATE(), interval 1 day),'%Y-%m-%d') order by  teacher.id,TIMERANK_ID";
         List<Record> list=Db.find(sql);
         String smsInfo="";
         String smsTel="";
         int index=0;
         String tomorrowDate="";
         Map<String,String> maps=new HashMap<String,String>();
         for (Record record : list) {//组合数据
        	 index++;
			smsInfo="【上课通知】"+record.getStr("student_name")+"同学,"+tomorrowDate+"日"+record.getStr("rank_name")+","+record.getStr("classname")+","+record.getStr("teacher_name")+"老师"+record.getStr("course_name")+"课程，按时完成作业，提前预习。 by超凡学习体验-罗德国际教育。";
			smsTel=record.getStr("STUDENT_TEL");
			if(smsTel!=null&&!smsTel.equals("")&&smsTel.length()==11)//判断学生手机号为空
			{
			smsTel=smsTel+"_s_"+index;//学生
			maps.put(smsTel, smsInfo);
			}
			//家长
			smsTel=record.getStr("parenttel");
			if(smsTel!=null&&!smsTel.equals("")&&record.getInt("parentTelAccept")==1&&smsTel.length()==11)//判断家长手机号为空或者是否接受
			{
				smsTel=smsTel+"_p_"+index;//家长
				maps.put(smsTel, smsInfo);
			}
			//老师
			smsTel=record.getStr("teacher_tel");
			if(smsTel!=null&&!smsTel.equals("")&&smsTel.length()==11)//判断老师手机号为空或者是否接受
			{
				smsTel=smsTel+"_t";//老师
				smsInfo=record.getStr("rank_type")+"时段："+record.getStr("student_name")+record.getStr("course_name")+" ";
				if(maps.get(smsTel)!=null){
					smsInfo=maps.get(smsTel)+smsInfo;
				}
				maps.put(smsTel, smsInfo);
			}
		}
         for(Map.Entry<String,String> entry:maps.entrySet()){
        	 smsTel=entry.getKey();
        	 smsInfo=entry.getValue();
        	 if(smsTel.contains("_t"))
        	 {
        		 smsInfo="【"+tomorrowDate+"课表】"+smsInfo+"by罗德国际教育";
        	 }
        	 smsTel=smsTel.substring(0,11);
        	 this.send(smsInfo, smsTel);//发送短信
         }
         this.send(tomorrowDate+"的课表已发送完毕，总计"+list.size()+"课时。", "13260008070");//发送确认短信给刘国强
         this.send(tomorrowDate+"的课表已发送完毕，总计"+list.size()+"课时。", "18610056596");//发送确认短信给刘国强
		}catch(Exception e){
			logger.error(e);
			this.send("课表发送出现问题!!!", "15652961182");//发送确认短信给刘国强
		}
	}
    
}
