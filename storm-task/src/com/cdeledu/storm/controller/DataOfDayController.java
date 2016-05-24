package com.cdeledu.storm.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cdeledu.storm.service.CourseService;
import com.cdeledu.storm.service.DataOfDayService;
import com.cdeledu.storm.service.impl.CourseServiceImpl;
import com.cdeledu.storm.service.impl.DataOfDayServiceImpl;

public class DataOfDayController {

	Logger logger = Logger.getLogger(DataOfDayController.class);
	private CourseService courseService;
	private DataOfDayService dataOfDayService;
	
	public void studyEffectOfDay(){
		logger.info("课程下每日学习效果统计开始！");
		dataOfDayService = new DataOfDayServiceImpl();
		courseService = new CourseServiceImpl();
		List<Map<String, Object>> courseList = courseService.getAllCourseID();
		Iterator<Map<String, Object>> it = courseList.iterator();
		while(it.hasNext()){
			Map<String, Object> course = it.next();
			dataOfDayService.MyStudyEffectOfDay("" + course.get("courseID"));
			dataOfDayService.MaxStudyEffectOfDay("" + course.get("courseID"));
			dataOfDayService.AVGStudyEffectOfDay("" + course.get("courseID"));
		}
		logger.info("课程下每日学习效果统计结束！");
	}
}
