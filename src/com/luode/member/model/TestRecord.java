package com.luode.member.model;


import java.util.List;

import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.luode.letter.model.LetterDetail;

/**
 * 
 * 类描述：TestRecord model
 * @author David
 * @time 2013-5-26 下午6:33:45
 *
 */
@SuppressWarnings("serial")
public class TestRecord extends Model<TestRecord> {
	public static final TestRecord dao = new TestRecord();
	public Page<TestRecord> getTestRecordList(){
		return dao.paginate  (1, 10, "select *", "from letter_test_record order by id asc");
	}
	public List<LetterDetail> getNoTestWords(String testId) {
		if(StringKit.notBlank(testId)){
			List<LetterDetail> ldList = LetterDetail.dao.find("SELECT ltr.word_id,(SELECT ld.word FROM letter_detail ld WHERE ld.id=ltr.word_id) word  FROM letter_test_record ltr WHERE ltr.is_right IS NULL AND ltr.test_id=?",Integer.parseInt(testId));
			return ldList;
		}else{
			return null;
		}
	}
}