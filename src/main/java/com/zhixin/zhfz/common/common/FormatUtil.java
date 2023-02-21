package com.zhixin.zhfz.common.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {
	
	private static final Logger logger =LoggerFactory.getLogger(FormatUtil.class);
	// 小数点2位
	public static final DecimalFormat DECIMAL_2_FORMAT = new DecimalFormat("#.00");  
	// 十位数
	public static final DecimalFormat DECADE_FORMAT = new DecimalFormat("00"); 
	
	
	public static void main(String arg[]){
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(2);
		String format = numberFormat.format((float) 1 / (float) 6);
		System.err.println(format);
	}
	
	
}
