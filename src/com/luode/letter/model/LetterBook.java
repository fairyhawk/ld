package com.luode.letter.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 *  书名
 */
public class LetterBook extends Model<LetterBook> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3559362480966048724L;
	public static final LetterBook dao = new LetterBook();
	public Page<LetterBook> getLetterBookList(){
		return dao.paginate  (1, 10, "select *", "from letter_book");
	}
	
	/**
	 * 
	 * 方法描述：获取全部单词书
	 * @author David
	 * @time 2013-7-16 下午9:19:48
	 *
	 * @return
	 */
	public List<LetterBook> getLetterBookAll(){
		return dao.find("select * from letter_book");
	}
}
