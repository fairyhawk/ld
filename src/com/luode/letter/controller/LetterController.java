package com.luode.letter.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Before;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.luode.common.CommonController;
import com.luode.letter.model.LetterBook;
import com.luode.letter.model.LetterBookChapter;
import com.luode.letter.model.LetterBookType;
import com.luode.letter.model.LetterDetail;
import com.luode.member.model.LearnState;
import com.luode.member.model.Member;
import com.luode.member.model.MemberBook;
import com.luode.member.model.MemberTest;
import com.luode.member.model.TestRecord;
import com.luode.util.Arith;
import com.luode.util.DateHelper;

public class LetterController extends CommonController {
	// 显示所有书分类
	public void index() {
		Integer memberId = getParaToInt(0);
		 setAttr("myBooks",Db.find("SELECT lb.*,lmb.is_finish,finish_time FROM letter_book lb,letter_member_book lmb WHERE lb.id=lmb.book_id AND lmb.member_id=?",memberId));
//		setAttr("myBooks", LetterBook.dao.paginate(getParaToInt(0, 1), 20, "SELECT lb.*,lmb.is_finish,finish_time", " FROM letter_book lb,letter_member_book lmb WHERE lb.id=lmb.book_id AND lmb.member_id=?", memberId));
		setAttr("member", Member.dao.findById(memberId));
		setAttr("bookType", Db.find("select * from letter_booktype order by id asc"));
		renderJsp("/WEB-INF/member/wordbook.jsp");
	}

	/**
	 * 显示书下的章节
	 */
	public void bookChapter() {
		Integer bookId = getParaToInt(0);
		HttpSession session = getSession();
		Member member = (Member) session.getAttribute("member");
		Integer memberId = member.getInt("id");
		List<MemberTest> mtList = MemberTest.dao.getMemberTestList(bookId,memberId,5);
		setAttr("mtList", mtList);
		Long noLearning = Db.queryLong("SELECT COUNT(*) FROM letter_learn_state lls WHERE lls.member_id=? AND lls.book_id=? AND lls.right_num=0 AND lls.wrong_num=0",memberId,bookId);
		Long rigthCount = Db.queryLong("SELECT COUNT(*) FROM letter_learn_state lls WHERE lls.member_id=? AND lls.book_id=? AND lls.right_num!=0 ",memberId,bookId);
		Long wrongCount = Db.queryLong("SELECT COUNT(*) FROM letter_learn_state lls WHERE lls.member_id=? AND lls.book_id=? AND lls.right_num=0 AND lls.wrong_num!=0 ",memberId,bookId);
		setAttr("noLearning", noLearning);
		setAttr("rigthCount", rigthCount);
		setAttr("wrongCount", wrongCount);
		LetterBook book = LetterBook.dao.findById(bookId);
		setAttr("book", book);
		setAttr("member", member);
		Long learnBookCount = Db.queryLong("SELECT COUNT(*) FROM letter_member_book lmb WHERE lmb.book_id=? AND lmb.member_id=?", getPara(0), getPara(1));
		setAttr("isLearn", learnBookCount);
		setAttr("thisBookType", LetterBookType.dao.findById(book.get("bookType_id")));
		List<LetterBookChapter> letter_book_chapter =  LetterBookChapter.dao.find("SELECT lbc.*,IF(ls.remain IS NULL,0,ls.remain ) remain FROM letter_book_chapter lbc " +
				"LEFT JOIN (SELECT lls.chapter_id,COUNT(*) remain FROM letter_learn_state lls WHERE lls.member_id=? AND lls.book_id=? AND lls.right_num=0 GROUP BY lls.chapter_id) ls " +
				"ON lbc.id = ls.chapter_id WHERE lbc.book_id=?" ,
		getPara(1),getPara(0),getPara(0));
		setAttr("bookChapter",letter_book_chapter);
		String random = getPara(2)==null?"0":"1";
		setAttr("random",random);
		render("bookChapter.html");
	}

	/**
	 * 书列表
	 */
	public void book() {
		setAttr("member", Member.dao.findById(getParaToInt(1)));
		setAttr("bookType", LetterBookType.dao.paginate(1, 100, "select *", "from letter_booktype order by id asc"));
		setAttr("book", LetterBook.dao.paginate(1, 100, "select *", "from letter_book where bookType_id=? order by id asc", getParaToInt(0)));
		render("book.html");
	}

	/**
	 * 
	 * 方法描述：用户要学习的单词书
	 * 
	 * @author David
	 * @time 2013-5-19 下午8:33:05
	 * 
	 */
	@Before(Tx.class)
	public void saveWordbook() {
		String bookId = getRequest().getParameter("bookId");
		String memberId = getRequest().getParameter("memberId");
		MemberBook mb = MemberBook.dao.findFirst("SELECT * FROM letter_member_book lmb WHERE lmb.member_id=? AND lmb.book_id=?", memberId, bookId);
		if (mb == null) {
			new MemberBook().set("member_id", memberId).set("book_id", bookId).set("start_time", new Date()).save();
			String sql = "INSERT INTO letter_learn_state(member_id,word_id,book_id,chapter_id)SELECT " + memberId + ", ld.id,ld.book_id,ld.book_chapter_id FROM letter_detail ld WHERE ld.book_id=" + bookId;
			Db.update(sql);
			setAttr("type", 1);
			setAttr("msg", "单词书激活成功，开始学习吧");
		} else {
			setAttr("type", 0);
			setAttr("msg", "您已激活本单词书");
		}
	
		renderJson();
	}

	/**
	 * 
	 * 方法描述：取消收藏单词书
	 * 
	 * @author David
	 * @time 2013-5-22 下午3:53:38
	 * 
	 */
	@Before(Tx.class)
	public void unSaveWordbook() {
		String bookId = getRequest().getParameter("bookId");
		String memberId = getRequest().getParameter("memberId");
		if (StringKit.notBlank(memberId) && StringKit.notBlank(bookId)) {
			String deletLlsSql = "DELETE FROM letter_learn_state WHERE member_id=" + memberId + " AND book_id=" + bookId;
			String deletLmbSql = "DELETE FROM letter_member_book WHERE member_id=" + memberId + " AND book_id=" + bookId;
			Db.update(deletLlsSql);
			Db.update(deletLmbSql);
			setAttr("type", 1);
			setAttr("msg", "取消学习成功");
		} else {
			setAttr("type", 0);
			setAttr("msg", "取消学习失败");
		}
		renderJson();
	}

	/**
	 * 单词列表
	 */
	public void detail() {
		// 第2个参数是bookId
		// 第3个参数是bookChapterId
		// 第4个参数是分页号码
		// 第1个参数是用户ID
		//setAttr("bookType", LetterBookType.dao.paginate(1, 100, "select *", "from letter_booktype order by id asc"));
		setAttr("book", LetterBook.dao.findById(getParaToInt(1)));
		setAttr("bookChapter", LetterBookChapter.dao.findById(getParaToInt(2)));
		setAttr("letterDetail", LetterDetail.dao.paginate(getParaToInt(3, 1), 20, "SELECT letter_detail.*, letter_learn_state.right_num ", 
				" FROM  letter_detail LEFT JOIN letter_learn_state ON " +
				" letter_detail.book_id=letter_learn_state.book_id AND " +
				" letter_detail.book_chapter_id=letter_learn_state.chapter_id AND" +
				" letter_detail.id=letter_learn_state.word_id" +
				" AND letter_learn_state.member_id=?" +
				" WHERE letter_detail.book_id =? AND letter_detail.book_chapter_id=?  ORDER BY letter_detail.id ", getParaToInt(0),
				getParaToInt(1), getParaToInt(2)));
		setAttr("member", Member.dao.findById(getParaToInt(0)));
		render("letterDetail.html");
	}

	/**
	 * 测试页面,显示单词 第一个参数为 测试的专业类型 托福1 sat 2
	 */
	@SuppressWarnings("unchecked")
	public void test() {
		/**
		 * 筛选测试单词的规则 待实现 TODO: （随机筛选）
		 * 
		 * 随机查询
		 */
		/*
		 * setAttr("testWordList", LetterDetail.dao.find(
		 * "SELECT * FROM letter_detail WHERE id >= (SELECT floor(RAND() * ((SELECT\n"
		 * +
		 * "MAX(id) FROM letter_detail)-(SELECT MIN(id) FROM letter_detail)) + (SELECT MIN(id)\n"
		 * +
		 * "FROM letter_detail))) and booktype_id = ? and book_id=? and book_chapter_id=?  ORDER BY id LIMIT 50;"
		 * , getPara(0),getPara(1),getPara(2)));
		 */
		// String desc = "";
		// String rad = "<";
		String bookId = getPara(1);
		String chapterId = getPara(2);
		Integer memberId = getParaToInt(3);

		// Random random = new Random();
		// double dudom = random.nextDouble();
		// if (dudom > 0.5) {
		// desc = "asc";
		// rad = "<";
		// } else {
		// desc = "desc";
		// rad = ">";
		// }
		// 优先从错误的单词中挑选出20个，按照错误次数排序

		// List<LetterDetail> list =
		// LetterDetail.dao.find("SELECT  RAND() rad,  letter_detail.* FROM  letter_detail WHERE  "
		// +
		// "booktype_id = ?  AND book_id = ? AND book_chapter_id = ? HAVING  rad "
		// + rad + dudom + " ORDER BY id  " + desc + "  ", getPara(0),
		// getPara(1), getPara(2));
		// 测试使用
		// List<LetterDetail> list =
		// LetterDetail.dao.find("SELECT letter_detail.* FROM  letter_detail WHERE booktype_id = ?  AND book_id = ? AND book_chapter_id = ? limit 20 ",
		// getPara(0), getPara(1), getPara(2));
		// List<LetterDetail> list1 =
		// LetterDetail.dao.find("SELECT ld.* FROM letter_detail ld ,letter_learn_state lls WHERE ld.id = lls.word_id AND lls.member_id=? AND lls.book_id=? AND lls.chapter_id=? AND lls.right_num <=3 ORDER BY lls.wrong_num DESC LIMIT 20 ",
		// memberId, bookId, chapterId);
		// int newWordCount = list1 == null ? 0 : list1.size();
		// List<LetterDetail> list2 =
		// LetterDetail.dao.find("SELECT ld.* FROM letter_detail ld ,letter_learn_state lls WHERE ld.id = lls.word_id AND lls.member_id=? AND lls.book_id=? AND lls.chapter_id=? AND lls.right_num =0 AND lls.wrong_num = 0 LIMIT ? ",
		// memberId, bookId, chapterId, 50 - newWordCount);
		// list1.addAll(list2);
		List<LetterDetail> list = LetterDetail.dao.find("SELECT ld.* FROM letter_detail ld  JOIN letter_learn_state lls " +
				" ON ld.book_id=lls.book_id AND ld.book_chapter_id=lls.chapter_id" +
				" AND ld.id=lls.word_id AND lls.member_id=?" +
				" WHERE ld.book_id=? AND ld.book_chapter_id=? ",memberId, bookId, chapterId);
		int wordtestCount = list == null ? 0 : list.size();
		if (wordtestCount >= 1) {
			Collections.shuffle(list);
			System.out.println("+++list.size():" + list.size());
			// if (list1 != null && list1.size() > 0) {
			// if (list1.size() > 50) {
			// int beginIndex = 0;
			// if (random.nextDouble() > 0.5) {
			// beginIndex = list1.size() - 50;
			// beginIndex = beginIndex > 30 ? 15 : beginIndex;
			// beginIndex = beginIndex > 40 ? 25 : beginIndex;
			// beginIndex = beginIndex > 60 ? 35 : beginIndex;
			// }
			// List<LetterDetail> details = new ArrayList<LetterDetail>();
			// int j = 0;
			// for (int i = beginIndex; i < list1.size(); i++) {
			// j++;
			// details.add(list1.get(i));
			// if (j == 50)
			// break;
			// }
			// setAttr("testWordList", details);
			// System.out.println("+++details.size():" + details.size());
			// } else {
			// setAttr("testWordList", list1);
			// }
			// }
			setAttr("testWordList", list);
			List<String> wordRanks = new ArrayList<String>();
			for (LetterDetail letterDetail : (List<LetterDetail>) getAttr("testWordList")) {
				wordRanks.add(letterDetail.get("id").toString());
			}
			setAttr("word_ranks", wordRanks);
		} else {
			// MemberBook mb =
			// MemberBook.dao.findFirst("SELECT * FROM letter_member_book lmb WHERE lmb.member_id=? AND lmb.book_id=?",
			// memberId, bookId);
			// mb.set("is_finish", 1).set("finish_time", new Date()).update();
			setAttr("testWordList", null);
		}
		setAttr("member", Member.dao.findById(memberId));
		setAttr("bookId", bookId);
		setAttr("chapterId", chapterId);
		setAttr("testType", "1");
		render("test-2-1.html");

	}

	/**
	 * 
	 * 方法描述：错词重测
	 * 
	 * @author David
	 * @time 2013-5-26 下午6:29:55
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void testWrong() {
		String bookId = getPara(1);
		String chapterId = getPara(2);
		Integer memberId = getParaToInt(3);
		//错词测试，取本单元上次测试时错误的。last_status=0
		List<LetterDetail> list = LetterDetail.dao.find("SELECT ld.* FROM letter_detail ld ,letter_learn_state lls WHERE ld.id = lls.word_id AND lls.member_id=? AND lls.book_id=? AND lls.chapter_id=? AND lls.last_status =0 ", memberId, bookId, chapterId);
		int newWordCount = list == null ? 0 : list.size();
		if (newWordCount >= 1) {
			Collections.shuffle(list);
			System.out.println("+++list.size():" + list.size());
			setAttr("testWordList", list);
			List<String> wordRanks = new ArrayList<String>();
			for (LetterDetail letterDetail : (List<LetterDetail>) getAttr("testWordList")) {
				wordRanks.add(letterDetail.get("id").toString());
			}
			setAttr("word_ranks", wordRanks);
		} else {
//			MemberBook mb = MemberBook.dao.findFirst("SELECT * FROM letter_member_book lmb WHERE lmb.member_id=? AND lmb.book_id=?", memberId, bookId);
//			mb.set("is_finish", 1).set("finish_time", new Date()).update();
			setAttr("testWordList", null);
		}
		setAttr("member", Member.dao.findById(memberId));
		setAttr("bookId", bookId);
		setAttr("chapterId", chapterId);
		setAttr("testType", "2");
		render("test-2-1.html");

	}

	// 测试第二步。获取选择的单词意思。并且拼装显示所需数据
	@SuppressWarnings("rawtypes")
	public void testStep2() {
		String name;
		Map<String, String> wordMap = new HashMap<String, String>();
		String memberId = getRequest().getParameter("memberId");
		String bookId = getRequest().getParameter("bookId");
		String chapterId = getRequest().getParameter("chapterId");
		String testType = getRequest().getParameter("testType");
		Enumeration test = getRequest().getParameterNames();
		List<String> testRecordList = new ArrayList<String>();
		while (test.hasMoreElements()) {
			name = (String) test.nextElement();
			if (name.contains("testwords")) {
				wordMap.put("word_" + getRequest().getParameter(name), name.split("_")[1]);
			}
		}
		if (wordMap.size() != 0) {
			setAttr("word_map", wordMap);
			MemberTest mt = new MemberTest();
			mt.set("member_id", memberId).set("book_id", bookId).set("start_time", new Date()).set("test_type", Integer.parseInt(testType));
			if(StringKit.notBlank(chapterId)&&!"3".equals(testType)){
				mt.set("chapter_id", chapterId);
			}
			boolean isOk = mt.save();
			if (isOk) {
				Integer mtid = mt.get("id");
				Map<String, List> answerMap = new HashMap<String, List>();
				for (Map.Entry<String, String> entry : wordMap.entrySet()) {
					String id = entry.getKey().split("_")[1];
					String wordValue=entry.getValue();
					testRecordList.add("INSERT INTO letter_test_record (test_id, word_id) VALUES ("+mtid+","+id+")");
//					String pos = Db.queryStr("SELECT SUBSTRING_INDEX(ld.explanation, '.', 1) FROM letter_detail ld WHERE ld.id=?", id);
					String pos = Db.queryStr("SELECT SUBSTRING(ltrim(ld.explanation), 1, 1) FROM letter_detail ld WHERE ld.id=?", id);
					List<LetterDetail> wordList = new ArrayList<LetterDetail>(); 
					
					wordList = LetterDetail.dao.find("select ld1.id,ld1.word,ld1.explanation from letter_detail ld1 where ld1.id = ? " + 
								"union all (SELECT ld2.id,ld2.word,ld2.explanation FROM letter_detail ld2 " +
								"WHERE ld2.book_id=? AND ld2.id!=? AND SUBSTRING(ld2.explanation, 1, 1) = ?  AND ld2.word !=? ORDER BY RAND() limit 4)", id,bookId,id,pos,wordValue);
					
					if(wordList.size()<5){
	                    wordList = LetterDetail.dao.find("SELECT ld1.id,ld1.word,ld1.explanation FROM letter_detail ld1 WHERE ld1.id = ?" +
	                            " UNION ALL (" +
	                            " SELECT   ld3.id,  ld3.word,  ld3.explanation FROM" +
	                            " (SELECT  ld2.word,MAX(ld2.id) id FROM letter_detail ld2  WHERE ld2.book_id = ?   AND ld2.id != ? AND ld2.word !=? " +
	                            " GROUP BY   ld2.word ORDER BY RAND()   LIMIT 4  ) AS ma" +
	                            " LEFT JOIN letter_detail ld3   ON ma.id= ld3.id)", id,bookId,id,wordValue);
	                }
					Collections.shuffle(wordList);
					answerMap.put(wordMap.get("word_" + id.trim()) + "_" + id.trim(), wordList);
				}
				Db.batch(testRecordList, testRecordList.size());
				setAttr("mtId", mtid);
				setAttr("wordtestCount", wordMap.size());
				setAttr("bookId", bookId);
				setAttr("chapterId", chapterId);
				setAttr("answer_map", answerMap);
				setAttr("member", Member.dao.findById(Integer.parseInt(memberId)));
				render("test-2-2.html");
			}
		} else {
			setAttr("memberId", memberId);
			redirect("/letter/testStep3/"+memberId+"-"+bookId);
		}

	}

	// 测试第三根据测试数据。显示测试结果页面
	public void testStep3() {
		String memberId = getRequest().getParameter("memberId");
		String bookId = getRequest().getParameter("bookId");
		String chapterId = getRequest().getParameter("chapterId");
		String wrongWords = getRequest().getParameter("wrongWords");
		String rightWords = getRequest().getParameter("rightWords");
		String memberTestId = getRequest().getParameter("mtId");
		int wordtestCount_right = 0;
		int wordtestCount_wrong = 0;
		Member member=Member.dao.findById(Integer.parseInt(memberId));
		if (StringKit.notBlank(memberId) && StringKit.notBlank(memberTestId)) {
			MemberTest mt = MemberTest.dao.findById(Integer.parseInt(memberTestId));
			Map<String, String> wordMap = new HashMap<String,String>();
			if (mt != null) {
				int mtid = mt.getInt("id");
				if (StringKit.notBlank(wrongWords)) {
					String worngWord[] = wrongWords.split("_");
					for (String wordId : worngWord) {
						wordMap.put(wordId, "0");
					}
					wordtestCount_wrong = wordMap.size();
				}
				if (StringKit.notBlank(rightWords)) {
					String rightWord[] = rightWords.split("_");
					for (String wordId : rightWord) {
						wordMap.put(wordId, "1");
					}
					wordtestCount_right = wordMap.size()-wordtestCount_wrong;
				}
				if(wordMap.size()!=0){
					Set<String> keys = wordMap.keySet();
					Iterator<String> it = keys.iterator();
					while(it.hasNext()){
						String wordId = it.next();
						String isRight = wordMap.get(wordId);
//						new TestRecord().set("test_id", mtid).set("word_id", wordId).set("is_right", isRight).save();
						LearnState learnState = LearnState.dao.findFirst("select * from letter_learn_state where member_id=? and book_id = ? and word_id = ?", memberId, bookId, wordId);
						if("1".equals(isRight)){//正确的情况
							if (learnState == null) {
								new LearnState().set("member_id", memberId).set("book_id", bookId).set("word_id", wordId).set("right_num", 1).set("last_status",1).save();
							} else {
								learnState.set("right_num", learnState.getInt("right_num") + 1).set("last_status",1).update();
							}
						}else{//错误的情况
							if (learnState == null) {
								new LearnState().set("member_id", memberId).set("book_id", bookId).set("word_id", wordId).set("wrong_num", 1).set("last_status",0).save();
							} else {
								learnState.set("wrong_num", learnState.getInt("wrong_num") + 1).set("last_status",0).update();
							}
						}
					}
				}
				Long noTestCount = Db.queryLong("SELECT COUNT(*) FROM letter_test_record t WHERE t.test_id=? AND t.is_right IS NULL",mtid);
				Long testCount = Db.queryLong("SELECT COUNT(*) FROM letter_test_record t WHERE t.test_id=?",mtid);
				Long rightCount = Db.queryLong("SELECT COUNT(*) FROM letter_test_record t WHERE t.test_id=? AND t.is_right=1",mtid);
				Long wrongCount = Db.queryLong("SELECT COUNT(*) FROM letter_test_record t WHERE t.test_id=? AND t.is_right=0",mtid);
				if(noTestCount==0){
					Date endDate = new Date();
					java.sql.Timestamp startDate = mt.getTimestamp("start_time");
					Long startTimeLong = startDate.getTime();
					Date startTime = DateHelper.timestampToDate(startTimeLong);
					Double accuracy = Arith.div(rightCount, testCount)*100;
					Integer expendTime = DateHelper.getMinuteInterval(startTime, endDate);
					System.out.println("+++accuracy:"+new BigDecimal(accuracy).setScale(2, RoundingMode.HALF_UP));
					mt.set("accuracy", new BigDecimal(accuracy)
					.setScale(2, RoundingMode.HALF_UP)+"%")
					.set("expend_time", expendTime)
					.set("end_time", new Date())
					.set("test_finish", 1)
					.set("right_num", rightCount)
					.set("wrong_num", wrongCount).update();
					member.set("last_test_date", endDate).set("sms_remind", 0).update();//更新用户最后测试时间
				}
				setAttr("member", Member.dao.findById(Integer.parseInt(memberId)));
			}

		}else{
			memberId = getPara(0);
			bookId = getPara(1);
			setAttr("member", Member.dao.findById(Integer.parseInt(memberId)));
		}
		int allcount = wordtestCount_right + wordtestCount_wrong;
		setAttr("wordtestCount_right", wordtestCount_right);
		setAttr("wordtestCount_wrong", wordtestCount_wrong);
		setAttr("bookId", bookId);
		setAttr("chapterId", chapterId);
		BigDecimal successPr=null;
		if (allcount != 0){
            successPr=new BigDecimal(wordtestCount_right * 1.00 / allcount * 100).setScale(2, RoundingMode.HALF_UP);
        }else{
            successPr=new BigDecimal(wordtestCount_right * 1.00 / 1 * 100).setScale(2, RoundingMode.HALF_UP);
        }
		setAttr("success", successPr);
		if (successPr.doubleValue() < 40) {
            setAttr("testResult", " 正确率太低了。Come On。。 ");
        } else if (successPr.doubleValue() < 50) {
            setAttr("testResult", " 加油努力到50%！。Go Go 。。");
        } else if (successPr.doubleValue() < 60) {
            setAttr("testResult", " 革命尚未成功，同志仍须努力！加油。。");
        } else if (successPr.doubleValue() < 80) {
            setAttr("testResult", " 正确率不错！加油。。");
        } else {
            setAttr("testResult", " 木的说，非常好！加油。。");
        }
		render("testOK.html");
	}
	/**
	 * 点击已经背过操作。
	 * 将正确的次数改为4，这样在测试过程就不再显示
	 */
	public  void doright(){
		Integer letterId=Integer.valueOf(getRequest().getParameter("id"));
		Integer memberId=getParaToInt(0);
		String sql="select * from  letter_learn_state  WHERE member_id =? AND word_id=?";
		LearnState learnState = LearnState.dao.findFirst(sql,memberId,letterId);
		learnState.set("right_num", 4).update();
		setAttr("msg", "success");
		renderJson();
	}
	/**
	 * 
	 * 方法描述：点击掌握取消标记
	 * @author David
	 * @time 2013-6-12 下午5:47:24
	 *
	 */
	public  void cancelKnow(){
		Integer letterId=Integer.valueOf(getRequest().getParameter("id"));
		Integer memberId=getParaToInt(0);
		String sql="select * from  letter_learn_state  WHERE member_id =? AND word_id=?";
		LearnState learnState = LearnState.dao.findFirst(sql,memberId,letterId);
		learnState.set("right_num", 0).update();
		setAttr("msg", "success");
		renderJson();
	}
	
	/**
	 * 
	 * 方法描述：根据用户所选单元进行随机测试单词
	 * @author David
	 * @time 2013-6-16 下午3:11:45
	 *
	 */
	@SuppressWarnings("unchecked")
	public void randomTest() {
		String bookId = getRequest().getParameter("bookId");
		String bookTypeId = getRequest().getParameter("bookTypeId");
		String memberId = getRequest().getParameter("memberId");
		String priorTest = getRequest().getParameter("priorTest");//1优先测试未测单词，2优先测试错误单词
		String chapterIds[] = getRequest().getParameterValues("bchapters");
		String chapterId = "";
		String testType = getRequest().getParameter("testType");
		setAttr("member", Member.dao.findById(memberId));
		setAttr("bookId", bookId);
		setAttr("testType", testType);
		setAttr("bookTypeId", bookTypeId);
		setAttr("chapterId", chapterId);
		if(chapterIds == null){
			redirect("/letter/bookChapter/"+bookId+"-"+memberId+"-1");
		}else{
			int choseChapters = chapterIds.length;
			List<LetterDetail> testWordList = new ArrayList<LetterDetail>();
			if(choseChapters>=1){
//				int testAmount = (int)Math.floor(200/choseChapters);
				for(int i = 0 ; i<choseChapters;i++){
					String cid = chapterIds[i];
					chapterId +=cid+",";
					List<LetterDetail> list = new ArrayList<LetterDetail>();
					if("1".equals(priorTest)){//测试没有测试过的单词
						list = LetterDetail.dao.find("SELECT ld.* FROM letter_detail ld  JOIN letter_learn_state lls " +
								" ON ld.book_id=lls.book_id AND ld.book_chapter_id=lls.chapter_id" +
								" AND ld.id=lls.word_id AND lls.right_num=0 AND wrong_num=0 AND lls.member_id=?" +
								" WHERE ld.book_id=? AND ld.book_chapter_id=? ",memberId, bookId, Integer.parseInt(cid));					
					}else{
						list = LetterDetail.dao.find("SELECT ld.* FROM letter_detail ld  JOIN letter_learn_state lls " +
								" ON ld.book_id=lls.book_id AND ld.book_chapter_id=lls.chapter_id" +
								" AND ld.id=lls.word_id  AND lls.member_id=?" +
								" WHERE ld.book_id=? AND ld.book_chapter_id=? ",memberId, bookId, Integer.parseInt(cid));
					}
//					if(choseChapters-1 == i+1){
//						testAmount = 200 - testAmount*(i+1);
//					}
//					if(list.size()>testAmount){
//						testWordList.addAll(list.subList(0, testAmount));
//					}else{
						testWordList.addAll(list);
//					}
				}
			}
			int wordtestCount = testWordList == null ? 0 : testWordList.size();
			if (wordtestCount >= 1) {
				Collections.shuffle(testWordList);
				System.out.println("+++list.size():" + testWordList.size());
				if(testWordList.size()>200){
					setAttr("testWordList", testWordList.subList(0, 200));
				}else{
					setAttr("testWordList", testWordList);
				}
				List<String> wordRanks = new ArrayList<String>();
				for (LetterDetail letterDetail : (List<LetterDetail>) getAttr("testWordList")) {
					wordRanks.add(letterDetail.get("id").toString());
				}
				setAttr("word_ranks", wordRanks);
			} else {
				setAttr("testWordList", null);
			}
			render("test-2-1.html");
		}
	}
	
	@Before(Tx.class)
	public void updateTestRecord() {
		String testId = getRequest().getParameter("testId");
		String wordId = getRequest().getParameter("wordId");
		String isRight = getRequest().getParameter("isRight");
		if (StringKit.notBlank(testId) && StringKit.notBlank(wordId)&&StringKit.notBlank(isRight)) {
			String sql="update letter_test_record set is_right="+isRight+" WHERE test_id ="+testId+" AND word_id="+wordId;
			Db.update(sql);
			setAttr("status", 0);
		} else {
			setAttr("status", 1);
			setAttr("msg", "系统异常，提交失败！");
		}
		renderJson();
	}
	
	/**
	 * 
	 * 方法描述：用户查询测试情况
	 * @author David
	 * @time 2013-7-14 下午4:23:59
	 *
	 */
	public void testRecord() {
		List<LetterBook> letterBookList = LetterBook.dao.getLetterBookAll();
		HttpSession session = getSession();
		Member member = (Member) session.getAttribute("member");
		String beginDate = getRequest().getParameter("beginDate");
		String endDate = getRequest().getParameter("endDate");
		String testFinish = getRequest().getParameter("testFinish");
		String letterBookId = getRequest().getParameter("letterBookId");
		String testType = getRequest().getParameter("testType");
		Integer mid = member.getInt("id");
		StringBuffer condition = new StringBuffer("FROM letter_member_test lmt WHERE lmt.member_id=?");
		if (StringKit.notBlank(letterBookId))
			condition.append(" AND lmt.book_id = " + letterBookId + " ");
		if (StringKit.notBlank(testFinish))
			condition.append(" AND lmt.test_finish = " + testFinish + " ");
		if (StringKit.notBlank(beginDate))
			condition.append(" AND lmt.start_time >= '" + beginDate + " 00:00:00' ");
		if (StringKit.notBlank(endDate))
			condition.append(" AND lmt.start_time <= '" + endDate + " 23:59:59' ");
		if (StringKit.notBlank(testType))
			condition.append(" AND lmt.test_type = " + testType);
		List<MemberTest> memberTests = MemberTest.dao.find("SELECT lmt.id,(SELECT lm.member_name FROM letter_member lm WHERE lm.id=lmt.member_id) mname," +
						"(SELECT lb.`name` FROM letter_book lb WHERE lb.id=lmt.book_id) bname ," +
						"(SELECT lbc.`name` FROM letter_book_chapter lbc WHERE lbc.id=lmt.chapter_id) cname," +
						"IF(lmt.test_type=1,'单元测试',IF(lmt.test_type=2,'错词测试','随机测试')) test_type,IF(lmt.test_finish=0,'未完成','已完成') test_finish," +
						"lmt.right_num,lmt.wrong_num,lmt.accuracy,lmt.expend_time,lmt.start_time,lmt.end_time "+condition.append(" ORDER BY id DESC").toString(),mid);
		setAttr("memberTests", memberTests);
		setAttr("letterBookList", letterBookList);
		renderJsp("/WEB-INF/member/testRecord.jsp");
	}
	
	public void testDetail() {
		Integer testId = getParaToInt(0);
		List<Record> testRecordList = Db.find("SELECT (SELECT lb.`name` FROM letter_book lb WHERE lb.id=ld.book_id) book_name," +
				"(SELECT lbc.`name` FROM letter_book_chapter lbc WHERE lbc.id=ld.book_chapter_id) book_chapter," +
				"IF(t.is_right IS NULL,'未测',IF(t.is_right=0,'否','是')) isright,ld.word,ld.soundmark,ld.explanation " +
				"FROM letter_detail ld,letter_test_record t WHERE ld.id = t.word_id AND t.test_id=? ORDER BY t.is_right", testId);
		setAttr("recordList", testRecordList);
		renderJsp("/WEB-INF/member/testRecord.jsp");
	}
	
	/**
	 * 
	 * 方法描述：继续测试
	 * @author David
	 * @time 2013-7-16 下午10:30:51
	 *
	 */
	public void goOnTestStep2() {
		HttpSession session = getSession();
		Member member = (Member) session.getAttribute("member");
		Map<String, String> wordMap = new HashMap<String, String>();
		String mtid = getPara(0);
		MemberTest mt = MemberTest.dao.findById(Long.parseLong(mtid));
		String memberId = member.getInt("id")+"";
		String bookId = mt.getInt("book_id")+"";
		String chapterId = mt.getInt("chapterId")+"";
		String testType = mt.getInt("test_type")+"";
		List<LetterDetail> noTestWordList = TestRecord.dao.getNoTestWords(mtid);
		if(noTestWordList !=null && noTestWordList.size() >=1){
			for(LetterDetail ld : noTestWordList){
				wordMap.put("word_" + ld.getInt("word_id"), ld.getStr("word"));
			}
			if (wordMap.size() != 0) {
				setAttr("word_map", wordMap);
				Map<String, List<LetterDetail>> answerMap = new HashMap<String, List<LetterDetail>>();
				for (Map.Entry<String, String> entry : wordMap.entrySet()) {
					String id = entry.getKey().split("_")[1];
					String pos = Db.queryStr("SELECT SUBSTRING(ltrim(ld.explanation), 1, 1) FROM letter_detail ld WHERE ld.id=?", id);
					List<LetterDetail> wordList = new ArrayList<LetterDetail>(); 
					wordList = LetterDetail.dao.find("select ld1.id,ld1.word,ld1.explanation from letter_detail ld1 where ld1.id = ? " + 
                            "union all (SELECT ld2.id,ld2.word,ld2.explanation FROM letter_detail ld2 " +
                            "WHERE ld2.book_id=? AND ld2.id!=? AND SUBSTRING(ld2.explanation, 1, 1) = ? ORDER BY RAND() limit 4)", id,bookId,id,pos);
					if(wordList==null || wordList.size()<5){
					    wordList = LetterDetail.dao.find("select ld1.id,ld1.word,ld1.explanation from letter_detail ld1 where ld1.id = ? " + 
	                            "union all (SELECT ld2.id,ld2.word,ld2.explanation FROM letter_detail ld2 " +
	                            "WHERE ld2.book_id=? AND ld2.id!=?  ORDER BY RAND() limit 4)", id,bookId,id);
					}
					Collections.shuffle(wordList);
					answerMap.put(wordMap.get("word_" + id.trim()) + "_" + id.trim(), wordList);
				}
				setAttr("mtId", mtid);
				setAttr("wordtestCount", wordMap.size());
				setAttr("bookId", bookId);
				setAttr("chapterId", chapterId);
				setAttr("answer_map", answerMap);
				setAttr("member", member);
				setAttr("testType", testType);
				render("test-2-2.html");
			
			} else {
				setAttr("memberId", memberId);
				redirect("/letter/testStep3/"+memberId+"-"+bookId);
			}
		}else{
			Long noTestCount = Db.queryLong("SELECT COUNT(*) FROM letter_test_record t WHERE t.test_id=? AND t.is_right IS NULL",mtid);
			Long testCount = Db.queryLong("SELECT COUNT(*) FROM letter_test_record t WHERE t.test_id=?",mtid);
			Long rightCount = Db.queryLong("SELECT COUNT(*) FROM letter_test_record t WHERE t.test_id=? AND t.is_right=1",mtid);
			Long wrongCount = Db.queryLong("SELECT COUNT(*) FROM letter_test_record t WHERE t.test_id=? AND t.is_right=0",mtid);
			if(noTestCount==0){
				Date endDate = new Date();
				java.sql.Timestamp startDate = mt.getTimestamp("start_time");
				Long startTimeLong = startDate.getTime();
				Date startTime = DateHelper.timestampToDate(startTimeLong);
				Double accuracy = Arith.div(rightCount, testCount)*100;
				Integer expendTime = DateHelper.getMinuteInterval(startTime, endDate);
				System.out.println("+++accuracy:"+new BigDecimal(accuracy).setScale(2, RoundingMode.HALF_UP));
				mt.set("accuracy", new BigDecimal(accuracy)
				.setScale(2, RoundingMode.HALF_UP)+"%")
				.set("expend_time", expendTime)
				.set("end_time", new Date())
				.set("test_finish", 1)
				.set("right_num", rightCount)
				.set("wrong_num", wrongCount).update();
				member.set("last_test_date", endDate).set("sms_remind", 0).update();
			}
			redirect("/letter/testRecord");
		}
	}
	
	/**
	 * 
	 * 方法描述：单词统计
	 * @author David
	 * @time 2013-7-18 下午1:46:16
	 *
	 */
	public void testStata(){
		HttpSession session = getSession();
		Member member = (Member) session.getAttribute("member");
		
	}
	public static void main(String[] args) {
//		MemberTest mt = MemberTest.dao.findById(3);
//		Date startDate = mt.getDate("start_time");
//		System.out.println(startDate);
		System.out.println(new BigDecimal("123.76688").setScale(2,RoundingMode.HALF_UP));
	}
	//手动对所有用户增加收藏
	public void modifyallmember() {
		List<Member> members = Member.dao.find("select * from letter_member");
		List<LetterBook> bookList = LetterBook.dao.getLetterBookAll();
		for(Member member :members){
			int memberId= member.getInt("id");
			for(LetterBook lb : bookList){
				Long bookId = lb.getLong("id");
				MemberBook mb = MemberBook.dao.findFirst("SELECT * FROM letter_member_book lmb WHERE lmb.member_id=? AND lmb.book_id=?", memberId, bookId);
				if (mb == null) {
					new MemberBook().set("member_id", memberId).set("book_id", bookId).set("start_time", new Date()).save();
					String sql = "INSERT INTO letter_learn_state(member_id,word_id,book_id,chapter_id)SELECT " + memberId + ", ld.id,ld.book_id,ld.book_chapter_id FROM letter_detail ld WHERE ld.book_id=" + bookId;
					Db.update(sql);
				} else{
					System.out.println("单词书已存在:");
				}
			}
		}
		
	}
}
