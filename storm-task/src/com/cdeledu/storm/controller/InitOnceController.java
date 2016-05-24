package com.cdeledu.storm.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cdeledu.storm.service.ChapterService;
import com.cdeledu.storm.service.CourseService;
import com.cdeledu.storm.service.InitOnceService;
import com.cdeledu.storm.service.impl.ChapterServiceImpl;
import com.cdeledu.storm.service.impl.CourseServiceImpl;
import com.cdeledu.storm.service.impl.InitOnceServiceImpl;

public class InitOnceController {
	Logger logger = Logger.getLogger(InitOnceController.class);
	private InitOnceService initOnceservice;
	private ChapterService chapterService;
	private CourseService courseService;
	
	public void init(){
		logger.info("初始化掌握程度、学习效果开始！");
		initOnceservice = new InitOnceServiceImpl();
		
		courseService = new CourseServiceImpl();
		List<Map<String, Object>> courseList = courseService.getAllCourseID();
		Iterator<Map<String, Object>> it2 = courseList.iterator();
		while(it2.hasNext()){
			Map<String, Object> course = it2.next();
			initOnceservice.initCourse("" + course.get("courseID"));
		}
		
		chapterService = new ChapterServiceImpl();
		List<Map<String, Object>> chapterList = chapterService.getAllChapterID();
		Iterator<Map<String, Object>> it1 = chapterList.iterator();
		while(it1.hasNext()){
			Map<String, Object> chapter = it1.next();
			initOnceservice.initChapter("" + chapter.get("chapterID"));
		}
		
		logger.info("初始化掌握程度、学习效果结束！");
	}

}
