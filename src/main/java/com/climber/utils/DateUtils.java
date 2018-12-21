package com.climber.utils;

import java.util.Date;

public class DateUtils {
	
	public static String getStamp(){
		Date date = new Date();
		long lt = date.getTime();
		return String.valueOf(lt);
	}

}
