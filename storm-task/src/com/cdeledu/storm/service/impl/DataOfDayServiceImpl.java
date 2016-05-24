package com.cdeledu.storm.service.impl;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import com.cdeledu.storm.service.DataOfDayService;
import com.cdeledu.util.RedisClusterClient;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;

public class DataOfDayServiceImpl implements DataOfDayService {
	
	private static NumberFormat nf = NumberFormat.getNumberInstance();
	private static Date date;

	static {
		// 四舍五入保留2位
		nf.setMaximumFractionDigits(2);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		date = cal.getTime();
	}

	private SimpleDateFormat sf = new SimpleDateFormat("yyMMdd");
	private static JedisCluster jedisClient = RedisClusterClient.getJedisClient();
	
	public void MyStudyEffectOfDay(String courseId) {
		
		Set<Tuple> set = jedisClient.zrangeWithScores(mergeCourseSE(courseId), 0, -1);
		Iterator<Tuple> it = set.iterator();
		while(it.hasNext()){
			Tuple tu = it.next();
			jedisClient.hset(mergeDayCourseSE(courseId, tu.getElement()), getDateStr(), tu.getScore() + "");
		}
	}
	
	public void MaxStudyEffectOfDay(String courseId) {		
		Set<Tuple> set = jedisClient.zrevrangeWithScores(mergeCourseSE(courseId), 0, 0);
		Iterator<Tuple> it = set.iterator();
		while(it.hasNext()){
			Tuple tu = it.next();
			jedisClient.hset(mergeDayMaxCourseSE(courseId), getDateStr(), tu.getScore() + "");
		}
	}

	public void AVGStudyEffectOfDay(String courseId) {
		average(mergeCourseSE(courseId), mergeDaySEAVG(courseId), getDateStr());
	}

	private void average(String zsetKey, String avgKey, String field) {
		double avg = 0;
		Set<Tuple> set = jedisClient.zrangeWithScores(zsetKey, 0, -1);
		Iterator<Tuple> it = set.iterator();
		while (it.hasNext()) {
			Tuple tuple = it.next();
			avg += tuple.getScore();
		}
		if (set.size() > 0) {
			avg /= jedisClient.zcard(zsetKey);
			jedisClient.hset(avgKey, field, nf.format(avg));
		}
	}

	private String getDateStr(){		
		return sf.format(date);
	}
	
	private String mergeCourseSE(String courseId){
		return COURSE_STUDY_EFFECT + courseId;
	}
	
	private String mergeDayCourseSE(String courseId, String uid){
		return DAY_STUDY_EFFECT + courseId + SPLITER + uid;
	}
	
	private String mergeDayMaxCourseSE(String courseId){
		return DAY_MAX_STUDY_EFFECT + courseId;
	}
	
	private String mergeDaySEAVG(String courseId){
		return DAY_STUDY_EFFECT + AVG + SPLITER + courseId;
	}
}
