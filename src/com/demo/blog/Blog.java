package com.demo.blog;


import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Blog model.
 */
@SuppressWarnings("serial")
public class Blog extends Model<Blog> {
	public static final Blog dao = new Blog();
	public Page<Blog> getBlogList(){
		return dao.paginate  (1, 10, "select *", "from blog order by id asc");
	}
	
	
}