package com.zhixin.zhfz.common.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TimeSplitUtil {

	/*
	 * 按分钟来拆分时间片段，包括开始时间和结束时间
	 */
	public static Map<Date,Date> timeSplit(Date startTime,Date endTime,int minutes){

		//SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<Date,Date> map=new LinkedHashMap();
		Date start=startTime;
		Date end;
		while(true){
			long time=start.getTime();
			end=new Date(time+minutes*60*1000);
			if(end.compareTo(endTime)<0){
				map.put(start, end);
				start=end;
			}else{
				map.put(start, endTime);
				break;
			}
			
		}
		return map;
	}

}
