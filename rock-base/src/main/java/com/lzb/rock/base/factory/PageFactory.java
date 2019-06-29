package com.lzb.rock.base.factory;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.plugins.Page;
import com.lzb.rock.base.util.HttpKit;

/**
 * BootStrap Table
 * 
 * @author lzb
 *
 * @param <T> 2019年4月1日 下午8:00:51
 */
public class PageFactory<T> {

	public Page<T> defaultPage() {
		HttpServletRequest request = HttpKit.getRequest();
		int limit = Integer.valueOf(request.getParameter("limit")); // 每页多少条数据
		int offset = Integer.valueOf(request.getParameter("offset")); // 每页的偏移量(本页当前有多少条)
		Page<T> page = new Page<>((offset / limit + 1), limit);
		page.setOpenSort(false);
		return page;
	}

	/**
	 * 
	 * @param limit  每页多少条数据
	 * @param offset 每页的偏移量(本页当前有多少条)
	 * @param sort   排序字段名称
	 * @param order  asc或desc(升序或降序)
	 * @return
	 */
	public Page<T> defaultPage(Integer limit, Integer offset) {
		Page<T> page = new Page<>((offset / limit + 1), limit);
		page.setOpenSort(false);
		return page;
	}
}
