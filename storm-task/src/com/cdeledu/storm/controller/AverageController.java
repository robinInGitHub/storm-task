package com.cdeledu.storm.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cdeledu.storm.service.AverageService;
import com.cdeledu.storm.service.ChapterService;
import com.cdeledu.storm.service.CourseService;
import com.cdeledu.storm.service.impl.AverageServiceImpl;
import com.cdeledu.storm.service.impl.ChapterServiceImpl;
import com.cdeledu.storm.service.impl.CourseServiceImpl;

public class AverageController {
	 Logger logger = Logger.getLogger(AverageController.class);
	private AverageService averageService;
	private ChapterService chapterService;
	private CourseService courseService;
	// 章节下统计数据
	public void chapterAVG(){
		logger.info("章节下统计数据开始！");
		averageService = new AverageServiceImpl();
		chapterService = new ChapterServiceImpl();
		List<Map<String, Object>> chapterList = chapterService.getAllChapterID();
		Iterator<Map<String, Object>> it = chapterList.iterator();
		while(it.hasNext()){
			Map<String, Object> chapter = it.next();
			averageService.chapterAverage("" + chapter.get("chapterID"));
		}
		logger.info("章节下统计数据结束！");
	}
	// 课程下统计数据
	public void courseAVG(){
		logger.info("课程下统计数据开始！");
		averageService = new AverageServiceImpl();
		courseService = new CourseServiceImpl();
		List<Map<String, Object>> courseList = courseService.getAllCourseID();
		Iterator<Map<String, Object>> it = courseList.iterator();
		while(it.hasNext()){
			Map<String, Object> course = it.next();
			averageService.courseAverage("" + course.get("courseID"));
		}
		logger.info("章节下统计数据结束！");
	}

}
