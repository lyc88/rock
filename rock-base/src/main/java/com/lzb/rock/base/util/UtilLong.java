package com.lzb.rock.base.util;

public class UtilLong {

	
      //判断字典里面的值非空
	  public static boolean flag(Long id){
		  if(id != null && !id.equals("0") && !id.equals("") && id != 0){
			  return true ;
		  }
		  return false;
	  }
}
