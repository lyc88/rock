package com.lzb.rock.base.util;

import com.lzb.rock.base.model.QueryPage;

/**
 * 查询数据库 ： 页数和数量
 * @author lzz
 *
 */
public class UtilQueryNumber {
	
	public static QueryPage  getNumber(Integer limit,Integer offset){
		QueryPage qp=  new QueryPage();
		Integer  limitNum  = limit==null  ? 5 : limit;                   
		Integer  offsetNum = offset==null ? 0 :(limit * (offset -1 )) > 0 ? 			                                             
				                                limit * (offset -1 ):0 ; 
	    qp.setLimit(limitNum);
	    qp.setOffset(offsetNum);
	    return qp ;
	}

}
