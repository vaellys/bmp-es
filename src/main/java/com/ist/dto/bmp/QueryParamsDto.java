package com.ist.dto.bmp;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;



public class QueryParamsDto {
    /**
     * 索引名称列表
     */
    private List<String> indexNames;
    /**
     * 索引类型列表
     */
    private List<String> indexTypes;
    /**
     * 查询条件构建器
     */
    private QueryBuilder queryBuilder;
    /**
     * 是否高亮
     */
    private Boolean isHighlight;
    /**
     * 查询关键词
     */
    private String keywords;
    /**
     * 当前页
     */
    private Integer pageNow;
    /**
     * 页大小
     */
    private Integer pageSize;
    public List<String> getIndexNames() {
        return indexNames;
    }
    public void setIndexNames(List<String> indexNames) {
        this.indexNames = indexNames;
    }
    public List<String> getIndexTypes() {
        return indexTypes;
    }
    public void setIndexTypes(List<String> indexTypes) {
        this.indexTypes = indexTypes;
    }
    public QueryBuilder getQueryBuilder() {
        return queryBuilder;
    }
    public void setQueryBuilder(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }
    public Boolean getIsHighlight() {
        return isHighlight;
    }
    public void setIsHighlight(Boolean isHighlight) {
        this.isHighlight = isHighlight;
    }
    public String getKeywords() {
        return keywords;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public Integer getPageNow() {
        return pageNow;
    }
    public void setPageNow(Integer pageNow) {
        this.pageNow = pageNow;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
