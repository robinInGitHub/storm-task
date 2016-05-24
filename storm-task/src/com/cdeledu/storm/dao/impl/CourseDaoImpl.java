package com.cdeledu.storm.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cdeledu.conf.SqlMapApp;
import com.cdeledu.storm.dao.CourseDao;
import com.ibatis.sqlmap.client.SqlMapClient;

public class CourseDaoImpl implements CourseDao{
	
	Logger logger = Logger.getLogger(CourseDaoImpl.class);
	
	private SqlMapClient jiaowuClient;

	public CourseDaoImpl() {
		jiaowuClient = SqlMapApp.getAccJiaoWuClient();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAllCourseID() {
		try {
			return jiaowuClient.queryForList("com.cdeledu.storm.course.getAllCourse");
		} catch (SQLException e) {
			logger.error("获取课程下所有章节失败", e);
		}
		return null;
	}

}
