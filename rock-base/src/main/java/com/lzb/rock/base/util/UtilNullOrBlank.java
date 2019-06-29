package com.lzb.rock.base.util;

public class UtilNullOrBlank {

	public static boolean character(Object str){
		if(str != null && !str.toString().equals("")){
			return true ;
		}
		
		return false;
	}
}
