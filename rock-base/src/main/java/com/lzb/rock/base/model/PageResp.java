package com.lzb.rock.base.model;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.lzb.rock.base.util.UtilObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页返回参数
 * 
 * @author lzb
 *
 *         2018年11月9日 下午4:48:55
 */
public class PageResp<T> {
	@ApiModelProperty(value = "结果集")
	private List<T> rows;

	@ApiModelProperty(value = "总条数")
	private long total;

	public PageResp() {
	}

	public PageResp(List<T> rows, long total) {
		this.rows = rows;
		this.total = total;
	}

	public static <T> PageResp<T> getPage(Page page, Class<T> targetClass) {
		PageResp<T> resp = new PageResp<>();
		if (page != null && page.getRecords() != null) {
			resp.rows = UtilObject.javaList(page.getRecords(), targetClass);
			resp.total = page.getTotal();
		}else {
			resp.rows = null;
			resp.total = 0;
		}


		return resp;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "PageResp [rows=" + rows + ", total=" + total + "]";
	}

}
