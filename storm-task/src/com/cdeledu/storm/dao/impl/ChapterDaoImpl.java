package com.cdeledu.storm.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cdeledu.conf.SqlMapApp;
import com.cdeledu.storm.dao.ChapterDao;
import com.ibatis.sqlmap.client.SqlMapClient;

public class ChapterDaoImpl implements ChapterDao {
	
	Logger logger = Logger.getLogger(ChapterDaoImpl.class);
	
	private SqlMapClient jiaowuClient;
	
	public ChapterDaoImpl(){
		jiaowuClient = SqlMapApp.getAccJiaoWuClient();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAllChapterID() {
		try {
			return jiaowuClient.queryForList("com.cdeledu.storm.chapter.getAllChapter");
		} catch (SQLException e) {
			logger.error("获取所有章节ID失败", e);
		}
		return null;
	}

}
