package com.cdeledu.util;

/**
 * @ClassName: RedisKey
 * @Description: 业务相关的key都存储在这里，使用实现即可
 * @author yangzhenping
 * @date 2016年4月19日 上午10:29:53
 *
 */ 
public interface RedisKey {
	
	public static final String SPLITER = "_";
	
	//做题情况前缀
	public static final String QZ = "QZ_";
	
	//知识点前缀
	public static final String QZPO = "QZPO_";
	
	//章节下的做题情况前缀
	public static final String QZCH = "QZCH_";
	
	//章节有效总数
	public static final String QZCH_EFFECT_TOTAL = "QZCH_ET_";
	
	//章节有效正确数
	public static final String QZCH_EFFECT_RIGHT = "QZCH_ER_";
	
	//课程下做题情况前缀
	public static final String QZCOURSE = "QZCOURSE_";
	
	//题和知识点间的关系
	public static final String QUEPOINT = "QUE_PO_";
	
	//知识点和课件的关系
	public static final String POINTCHAPTER = "PO_CH_";
	
	//知识点下总题数
	public static final String POINTTOTAL = "PO_QUE_TOTAL_";

	//章节总时长前缀
	public static final String CHQUETOTAL = "CH_QUE_TOTAL_";
	
	//课件和课程关系
	public static final String CHAPTERCOURSE = "CH_COURSE_";

	//课程总体量
	public static final String COURSETOTAL = "COURSE_TOTAL_";
	
	public static final String CHAPTER_MASTERY_DEGREE = "CH_MD_";
	
	public static final String COURSE_STUDY_EFFECT = "COURSE_SE_";
	
	public static final String DAY_STUDY_EFFECT = "DAY_SE_";
	
	public static final String DAY_MAX_STUDY_EFFECT = "DAY_MAX_SE_";
	
	public static final String CWCH = "CWCH_";
	
	public static final String CWCU = "CWCU";

	//public static final String DAY = "DAY";
	public static final String MAX = "MAX";
	public static final String SCORE = "SCORE";
	public static final String TOTAL = "TOTAL";
	public static final String RIGHT = "RIGHT";
	public static final String PERCENT = "PERCENT";
	public static final String PERCENTAGE_COMPLETE = "PC";
	//周平均值
	public static final String WEEK_AVERAGE = "WA";
	//累加值
	public static final String SUM = "SUM";
	
	public static final String AVG = "AVG";
	
	public static final String LEN = "LEN";
	
	public static final String WEEK = "WEEK";
	
	public static final String DAYTOTAL = "DAYTOTAL";
	
	//章节下的做题得分前缀
	public static final String QZCHAPTER_SCORE = "QZCH_SCORE_";
	
	//课程下做题得分前缀
	public static final String QZCOURSE_SCORE = "QZCOURSE_SCORE_";
	
	//章节下看课得分前缀
	public static final String CWCHAPTER_SCORE = "CWCH_SCORE_";
			
	//课程下看课得分前缀
	public static final String CWCOURSE_SCORE = "CWCU_SCORE_";
}
