package com.cdeledu.storm.service;

import com.cdeledu.util.RedisKey;

public interface DataOfDayService extends RedisKey{

	public void MyStudyEffectOfDay(String courseId);
	
	public void MaxStudyEffectOfDay(String courseId);
	
	public void AVGStudyEffectOfDay(String courseId);
}
