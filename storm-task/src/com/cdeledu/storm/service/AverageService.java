package com.cdeledu.storm.service;

import com.cdeledu.util.RedisKey;

/**
 * @ClassName: AverageService
 * @Description: 求平均值
 * @author yangzhenping
 * @date 2016年4月29日 下午2:20:29
 *
 */ 
public interface AverageService extends RedisKey{

	/**
	 * @Title: chapterAverage
	 * @Description: 章节下的平均值
	 */ 
	public void chapterAverage(String chapterId);
	
	/**
	 * @Title: courseAverage
	 * @Description: 课程下的平均值
	 */ 
	public void courseAverage(String courseId);
}
