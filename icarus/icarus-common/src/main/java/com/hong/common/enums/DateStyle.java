package com.hong.common.enums;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;


/**
 * 时间格式化
 *
 * @author HongYi@10004580
 * @createTime 2021年05月26日 14:08:00
 */
public enum DateStyle {
	YYYY_MM("yyyy-MM"),
    YYYY_MM_DD("yyyy-MM-dd"),
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),

    YYYY_MM_EN("yyyy/MM"),
    YYYY_MM_DD_EN("yyyy/MM/dd"),
    YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm"),
    YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss"),

    YYYY_MM_CN("yyyy年MM月"),
    YYYY_MM_DD_CN("yyyy年MM月dd日"),
    YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm"),
    YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss"),

    HH_MM_SS("HH:mm:ss"),
    YYYY_MM_DD_HH_MM_SS_JOIN("yyyyMMddHHmmss"),
    YYYY_MM_DD_HH_MM_SS_SSS_JOIN("yyyyMMddHHmmssSSS"),
    YYYY_MM_DD_JOIN("yyyyMMdd"),
    
    YYYY_MM_DD_DOT("yyyy.MM.dd"),
    MM_DD("MM-dd"),
	;
	public String value;
	
	private DateStyle(String value){
		this.value=value;
	}
	
	/**
	 * 格式化date
	 */
	public String format(Date date){
		
		return DateFormatUtils.format(date,this.value);
	}
	/**
	 * 格式化date
	 */
	public String format(Long date){
		
		return DateFormatUtils.format(date,this.value);
	}
	
	/**
	 * 解析date
	 * @return
	 */
	public Date parse(String dateStr){
		dateStr=StringUtils.trim(dateStr);
		Date date=null;
		try {
			date=DateUtils.parseDate(dateStr,this.value);
		} catch (Exception e) {
			throw new RuntimeException(String.format("解析时间:[%s]出错,errorMsg:[%s]",dateStr,e.getMessage()));
		}
		return date;
	}
}
