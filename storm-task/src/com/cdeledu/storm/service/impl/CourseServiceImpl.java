package com.cdeledu.storm.service.impl;

import java.util.List;
import java.util.Map;

import com.cdeledu.storm.dao.CourseDao;
import com.cdeledu.storm.dao.impl.CourseDaoImpl;
import com.cdeledu.storm.service.CourseService;

public class CourseServiceImpl implements CourseService {

	private CourseDao courseDao;
	
	public CourseServiceImpl() {
		courseDao = new CourseDaoImpl();
	}
	
	public List<Map<String, Object>> getAllCourseID() {
		return courseDao.getAllCourseID();
	}

}
