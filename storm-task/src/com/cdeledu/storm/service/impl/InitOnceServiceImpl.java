package com.cdeledu.storm.service.impl;

import java.util.Iterator;
import java.util.Set;
import com.cdeledu.storm.service.InitOnceService;
import com.cdeledu.util.RedisClusterClient;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;

public class InitOnceServiceImpl implements InitOnceService {

	private static JedisCluster jedisClient = RedisClusterClient.getJedisClient();
	
	public void initChapter(String chapterId) {
		Set<Tuple> set = jedisClient.zrangeWithScores(mergeChapterScoreKey(chapterId), 0, -1);
		Iterator<Tuple> it = set.iterator();
		while(it.hasNext()){
			Tuple tu = it.next();
			chapterMasterDegree(tu.getElement(), chapterId, tu.getScore());
		}
	}
	
	/**
	 * @Title: chapterMasterDegree
	 * @Description: 章节掌握程度
	 */ 
	private void chapterMasterDegree(String userID, String chapterId, Double qzScore){
		Double cwScore = jedisClient.zscore(mergeCwareChapterScore(chapterId), userID);
		if(cwScore == null){
			cwScore = (double) 0;
		}
		if(qzScore > 100){
			qzScore = 100.0;
		}
		jedisClient.zadd(mergeMasterDegree(chapterId), cwScore * 0.3 + qzScore * 0.7, userID);
	}

	public void initCourse(String courseId) {
		Set<Tuple> set = jedisClient.zrangeWithScores(mergeCourseScoreKey(courseId), 0, -1);
		Iterator<Tuple> it = set.iterator();
		while(it.hasNext()){
			Tuple tu = it.next();
			courseStudyEffect(tu.getElement(), courseId, tu.getScore());
		}
	}
	
	/**
	 * @Title: courseStudyEffect
	 * @Description: 课程学习效果
	 */ 
	private void courseStudyEffect(String userID, String courseId, Double qzScore){
		Double cwScore = jedisClient.zscore(mergeCwareCourseScore(courseId), userID);
		if(cwScore == null){
			cwScore = (double) 0;
		}
		if(qzScore > 100){
			qzScore = 100.0;
		}
		jedisClient.zadd(mergeStudyEffectKey(courseId), cwScore * 0.3 + qzScore * 0.7, userID);
	}

	/**
	 * @Title: mergeChapterScoreKey
	 * @Description: 学员本章节得分
	 */ 
	private String mergeChapterScoreKey(String chapterId){
		return QZCHAPTER_SCORE + chapterId;
	}
	
	/**
	 * @Title: mergeCwareScore
	 * @Description: 学员章节听课得分
	 */ 
	private String mergeCwareChapterScore(String chapterId){
		return CWCHAPTER_SCORE + chapterId;
	}
	
	/**
	 * @Title: mergeMasterDegree
	 * @Description: 章节下掌握程度
	 */ 
	private String mergeMasterDegree(String chapterId){
		return CHAPTER_MASTERY_DEGREE + chapterId;
	}
	
	/**
	 * @Title: mergeCourseScoreKey
	 * @Description: 学员课程下总得分key
	 */ 
	private String mergeCourseScoreKey(String courseId){
		return QZCOURSE_SCORE + courseId;
	}
	
	/**
	 * @Title: mergeStudyEffectKey
	 * @Description: 课程下学习效果
	 */ 
	private String mergeStudyEffectKey(String courseId){
		return COURSE_STUDY_EFFECT + courseId;
	}
	
	/**
	 * @Title: mergeCwareScore
	 * @Description: 讲义的得分
	 */ 
	private String mergeCwareCourseScore(String courseId){
		return CWCOURSE_SCORE + courseId;
	}
}
