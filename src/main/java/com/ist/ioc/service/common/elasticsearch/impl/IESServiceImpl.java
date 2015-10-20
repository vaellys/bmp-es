package com.ist.ioc.service.common.elasticsearch.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;

import com.ist.common.es.util.LogUtils;
import com.ist.common.es.util.page.Pagination;
import com.ist.ioc.service.common.elasticsearch.AbstractIESService;

public class IESServiceImpl extends AbstractIESService {
    private static final Log logger = LogFactory.getLog(IESServiceImpl.class);

    @Override
    public Map<String, Object> documentSearch(List<String> indexNames, List<String> indexTypes, List<String> queryFields, List<String> hlFields, String keywords, Integer pageNow, Integer pageSize)
            throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("requestParams:"
                        + LogUtils.format("indexNames", indexNames, "indexTypes", indexTypes, "keywords", keywords, "pageNow", pageNow, "pageSize",
                                pageSize));
            }
            QueryStringQueryBuilder queryStringBuilder = this.queryStringBuilder(keywords, queryFields);
            return this.documentSearch(indexNames, indexTypes, queryStringBuilder, true, hlFields, Pagination.cpn(pageNow), Pagination.cps(pageSize));
        } catch (IOException e) {
            logger.error(
                    "搜索失败"
                            + LogUtils.format("indexNames", indexNames, "indexTypes", indexTypes, "keywords", keywords, "pageNow", pageNow,
                                    "pageSize", pageSize), e);
            throw new IOException("搜索失败", e);
        }
    }

    @Override
    public Map<String, Object> documentSearch(String indexName, String indexType, List<String> queryFields, List<String> hlFields, String keywords, Integer pageNow,
            Integer pageSize) throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("requestParams:"
                        + LogUtils.format("indexNames", indexName, "indexTypes", indexType, "keywords", keywords, "pageNow", pageNow, "pageSize",
                                pageSize));
            }
            QueryStringQueryBuilder queryStringBuilder = this.queryStringBuilder(keywords, queryFields);
            return this.documentSearch(indexName, indexType, queryStringBuilder, true, hlFields, Pagination.cpn(pageNow), Pagination.cps(pageSize));
        } catch (IOException e) {
            logger.error(
                    "搜索失败"
                            + LogUtils.format("indexNames", indexName, "indexTypes", indexType, "keywords", keywords, "pageNow", pageNow,
                                    "pageSize", pageSize), e);
            throw new IOException("搜索失败", e);
        }
    }

    @Override
    public Pagination documentSearch(List<String> indexNames, List<String> indexTypes, List<String> queryFields, String keywords, Integer pageNow,
            Integer pageSize) throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("requestParams:"
                        + LogUtils.format("indexNames", indexNames, "indexTypes", indexTypes, "keywords", keywords, "pageNow", pageNow, "pageSize",
                                pageSize));
            }
            QueryStringQueryBuilder queryStringBuilder = this.queryStringBuilder(keywords, queryFields);
            return this.documentSearch(indexNames, indexTypes, queryStringBuilder, true, Pagination.cpn(pageNow), Pagination.cps(pageSize));
        } catch (IOException e) {
            logger.error(
                    "搜索失败"
                            + LogUtils.format("indexNames", indexNames, "indexTypes", indexTypes, "keywords", keywords, "pageNow", pageNow,
                                    "pageSize", pageSize), e);
            throw new IOException("搜索失败", e);
        }
    }
    
    @Override
    public Pagination documentSearchWithSuggestion(List<String> indexNames, List<String> indexTypes, List<String> queryFields, String keywords, Integer pageNow,
            Integer pageSize) throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("requestParams:"
                        + LogUtils.format("indexNames", indexNames, "indexTypes", indexTypes, "keywords", keywords, "pageNow", pageNow, "pageSize",
                                pageSize));
            }
            MatchAllQueryBuilder matchAllBuilder = this.matchAllBuilder();
            return this.documentSearch(indexNames, indexTypes, matchAllBuilder, true, Pagination.cpn(pageNow), Pagination.cps(pageSize));
        } catch (IOException e) {
            logger.error(
                    "搜索失败"
                            + LogUtils.format("indexNames", indexNames, "indexTypes", indexTypes, "keywords", keywords, "pageNow", pageNow,
                                    "pageSize", pageSize), e);
            throw new IOException("搜索失败", e);
        }
    }

}
