package com.ist.ioc.service.common;

public class Constants{
	/**
	 * 创建动作
	 */
	public static final Integer ES_ADD_ACTION = 1;
	/**
	 * 删除动作
	 */
	public static final Integer ES_DELETE_ACTION = 2;
	
	/**
	 * es索引名称
	 */
	public static final String ES_INDEX_NAME = "index";
	/**
     * 更新动作
     */
    public static final Integer ES_UPDATE_ACTION = 3;
    /**
     * bulk局部更新参数
     */
    public static final String ES_UPDATE_DOC = "doc";
    /**
     * es id
     */
    public static final String ES_RESULT_KEY = "ENT_ID";
    /**
     * 结果常量
     */
    public static final String ES_RESULT = "result";
    /**
     * 索引生成时间
     */
    public static final String ES_INDEX_CREATE_TIME = "indexCreateTime";
    /**
     * 返回结果状态 1 成功 0 失败
     */
    public static final int ES_SUCCESS = 1;
    public static final int ES_FAILED = 0;
    
    /**
     * 分页常量 : PAGE_ID 当前页 PAGE_TOTALCOUNT 如果传-1则查询总数, 否则不查询总数 PAGE_SIZE 每页多少条记录
     */
    public static final String ES_PAGE_ID = "page_id";
    public static final String ES_PAGE_SIZE = "page_size";
    public static final String ES_TOTAL_SIZE = "page_count";
    public static final String ES_TABLE_NAME = "table_name";
    public static final String ES_TOTAL_PAGE = "page_total";
    
    /**
     * 分词器常量
     */
    public static final String ES_IK = "ik";
    public static final String ES_PINYIN_ANALYZER = "pinyin_analyzer";
    public static final String ES_ENGLISH = "english";
    public static final String ES_S2T_CONVERT = "s2t_convert";
    public static final String ES_T2S_CONVERT = "t2s_convert";
    
    /**
     * es常用字段常量
     */
    public static final String ES_FIELD_NAME = "name";
    public static final String ES_FIELD_TITLE = "title";
    public static final String ES_FIELD_DESCRIPTION = "description";
    public static final String ES_FIELD_CONTENT = "content";
    public static final String ES_FIELD_CONTENT_PY = "content.py";
    public static final String ES_FIELD_CONTENT_EN = "content.en";
    public static final String ES_FIELD_PY = "py";
    public static final String ES_FIELD_EN = "en";
    public static final String ES_FIELD_PATH = "path";
    public static final String ES_BEGIN_TAG = "<font color='red'>";
    public static final String ES_END_TAG = "</font>";
    
    /**
     * 查询得分定义
     */
    public static final String ES_SCORE = "score";
    //完全匹配
    public static final int ES_SCORE_PERFECT_MATCH = 100;
    //slop=1
    public static final int ES_SCORE_MATCH_SLOP_1 = 98;
    //slop=2
    public static final int ES_SCORE_MATCH_SLOP_2 = 95;
    //slop=3
    public static final int ES_SCORE_MATCH_SLOP_3 = 90;
    //查询字符串
    public static final int ES_SCORE_MATCH_QUERY_STR = 80;
    //模糊查询
    public static final int ES_SCORE_MATCH_FUZZY = 60;
    //完全不匹配
    public static final int ES_SCORE_NOT_MUST_MATCH = 0;
}