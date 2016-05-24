package com.cdeledu.storm.service.impl;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.cdeledu.storm.service.AverageService;
import com.cdeledu.util.RedisClusterClient;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;

public class AverageServiceImpl implements AverageService {

	Logger logger = Logger.getLogger(AverageServiceImpl.class);
	private static JedisCluster jedisClient = RedisClusterClient.getJedisClient();
	private static NumberFormat nf = NumberFormat.getNumberInstance();
	private static final int DAY = 7;

	public AverageServiceImpl() {
		// 四舍五入保留2位
		nf.setMaximumFractionDigits(2);
	}

	public void chapterAverage(String chapterId) {
		chapterPercentAverage(chapterId);
		effectTotalAverage(chapterId);
		effectRightAverage(chapterId);
		masteryDegreeAverage(chapterId);
		chapterTimeAverage(chapterId);
	}
	
	public void courseAverage(String courseId){
		coursePercentAVG(courseId);
		studyEffectAVG(courseId);
		courseTimeWeekAverage(courseId);
	}
	private void coursePercentAVG(String courseId){
		average(mergeCoursePercentKey(courseId), mergeCoursePercentAVG(), courseId);
	}
	private void studyEffectAVG(String courseId){
		average(mergeStudyEffectKey(courseId), mergeStudyEffectAVG(), courseId);
	}

	private void chapterPercentAverage(String chapterId) {
		average(mergeRightPercentKey(chapterId), mergeChapterPercentAVG(), chapterId);
	}

	private void effectTotalAverage(String chapterId) {
		average(mergeEffectTotal(chapterId), mergeEffectTotalAVG(), chapterId);
	}

	private void effectRightAverage(String chapterId) {
		average(mergeEffectRight(chapterId), mergeEffectRightAVG(), chapterId);
	}

	private void masteryDegreeAverage(String chapterId) {
		average(mergeMasterDegree(chapterId), mergeMasteryDegreeAVG(), chapterId);
	}
	
	private void chapterTimeAverage(String chapterId){
		average(mergeChapterTotalTime(chapterId), mergeChapterStudyTimeAVG(), chapterId);
	}
	
	private void courseTimeWeekAverage(String courseId){
		SimpleDateFormat sf = new SimpleDateFormat("yyMMdd");
		Calendar calendar = Calendar.getInstance();
		double total = 0;
		for(int i = 0; i < DAY ; i++){
			String str = sf.format(calendar.getTime());
			double dayTotal = getAverage(mergeCourseDayTotalTime(courseId, str));
			if(dayTotal != 0){
				total += dayTotal;
			}
			calendar.add(Calendar.DATE, -1);//得到前days天
		}
		if(total != 0){
			jedisClient.hset(mergeCourseWeekTimeAVG(), courseId, nf.format(total));
		}
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
	
	private double getAverage(String zsetKey){
		double avg = 0;
		Set<Tuple> set = jedisClient.zrangeWithScores(zsetKey, 0, -1);
		Iterator<Tuple> it = set.iterator();
		while (it.hasNext()) {
			Tuple tuple = it.next();
			avg += tuple.getScore();
		}
		if (avg != 0) {
			avg /= jedisClient.zcard(zsetKey);
		}
		return avg;
	}
	
	/**
	 * @Title: mergeRightPercentKey
	 * @Description: 学员某个章节的正确率
	 */
	private String mergeRightPercentKey(String chapterId) {
		return QZCH + PERCENT + SPLITER + chapterId;
	}

	/**
	 * @Title: mergeMasterDegree
	 * @Description: 章节下掌握程度
	 */
	private String mergeMasterDegree(String chapterId) {
		return CHAPTER_MASTERY_DEGREE + chapterId;
	}

	/**
	 * @Title: mergeEffectTotal
	 * @Description: 章节下学员有效做题总数，即去重
	 */
	private String mergeEffectTotal(String chapterId) {
		return QZCH_EFFECT_TOTAL + chapterId;
	}

	/**
	 * @Title: mergeEffectRight
	 * @Description: 章节下学员有效的做题正确总数，去重
	 */
	private String mergeEffectRight(String chapterId) {
		return QZCH_EFFECT_RIGHT + chapterId;
	}

	private String mergeChapterPercentAVG() {
		return QZCH + PERCENT + SPLITER + AVG;
	}

	private String mergeEffectTotalAVG() {
		return QZCH_EFFECT_TOTAL + AVG;
	}

	private String mergeEffectRightAVG() {
		return QZCH_EFFECT_RIGHT + AVG;
	}

	private String mergeMasteryDegreeAVG() {
		return CHAPTER_MASTERY_DEGREE + AVG;
	}
	
	/**
	 * @Title: mergeCoursePercentKey
	 * @Description: 学员某个课程下做题的正确率
	 */ 
	private String mergeCoursePercentKey(String courseId){
		return QZCOURSE + PERCENT + SPLITER + courseId;
	}
	
	/**
	 * @Title: mergeStudyEffectKey
	 * @Description: 课程下学习效果
	 */ 
	private String mergeStudyEffectKey(String courseId){
		return COURSE_STUDY_EFFECT + courseId;
	}
	
	private String mergeCoursePercentAVG(){
		return QZCOURSE + PERCENT + SPLITER + AVG;
	}
	
	private String mergeStudyEffectAVG(){
		return COURSE_STUDY_EFFECT + AVG;
	}
	
	private String mergeChapterTotalTime(String chapterId){
		return CWCH + TOTAL + SPLITER + chapterId;
	}
	
	private String mergeChapterStudyTimeAVG(){
		return CWCH + LEN + SPLITER + AVG;
	}
	
	private String mergeCourseWeekTimeAVG(){
		return CWCU + WEEK + SPLITER + AVG;
	}
	
	private String mergeCourseDayTotalTime(String courseId, String dayId){
		return CWCU + DAYTOTAL + SPLITER + courseId + SPLITER + dayId;
	}
}
