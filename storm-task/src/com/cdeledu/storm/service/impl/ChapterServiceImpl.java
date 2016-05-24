package com.cdeledu.storm.service.impl;

import java.util.List;
import java.util.Map;

import com.cdeledu.storm.dao.ChapterDao;
import com.cdeledu.storm.dao.impl.ChapterDaoImpl;
import com.cdeledu.storm.service.ChapterService;

public class ChapterServiceImpl implements ChapterService{

	private ChapterDao chapterDao;
	
	public ChapterServiceImpl() {
		chapterDao = new ChapterDaoImpl();
	}
	
	public List<Map<String, Object>> getAllChapterID() {
		return chapterDao.getAllChapterID();
	}

}
