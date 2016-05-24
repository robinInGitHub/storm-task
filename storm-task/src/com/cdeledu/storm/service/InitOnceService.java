package com.cdeledu.storm.service;

import com.cdeledu.util.RedisKey;

public interface InitOnceService extends RedisKey {
	
	public void initChapter(String chapterId);
	
	public void initCourse(String course);

}
