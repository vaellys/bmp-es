package com.ist.ioc.service.common.elasticsearch.impl;

import static com.ist.ioc.service.common.Constants.ES_RESULT;
import static com.ist.ioc.service.common.Constants.ES_SCORE;
import static com.ist.ioc.service.common.Constants.ES_SCORE_MATCH_FUZZY;
import static com.ist.ioc.service.common.Constants.ES_SCORE_MATCH_QUERY_STR;
import static com.ist.ioc.service.common.Constants.ES_SCORE_MATCH_SLOP_1;
import static com.ist.ioc.service.common.Constants.ES_SCORE_MATCH_SLOP_2;
import static com.ist.ioc.service.common.Constants.ES_SCORE_MATCH_SLOP_3;
import static com.ist.ioc.service.common.Constants.ES_SCORE_NOT_MUST_MATCH;
import static com.ist.ioc.service.common.Constants.ES_SCORE_PERFECT_MATCH;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyLikeThisQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;

import com.ist.common.es.util.FileUtils;
import com.ist.common.es.util.LogUtils;
import com.ist.common.es.util.ParseDocumentUtil;
import com.ist.common.es.util.page.Pagination;
import com.ist.dto.bmp.ESDto;
import com.ist.ioc.service.common.elasticsearch.AbstractIESService;

public class IESServiceImpl extends AbstractIESService {
    private static final Log logger = LogFactory.getLog(IESServiceImpl.class);
    
    private static final String FILE_UPLOAD_DIR = "c:/awp_data/upload/";
    
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
            FuzzyQueryBuilder fuzzyBuilder = this.fuzzyBuilder("content.prototype", keywords, Fuzziness.TWO);
            
            BoolQueryBuilder boolQueryBuilder = this.boolQueryBuilder();
            boolQueryBuilder.should(queryStringBuilder).should(fuzzyBuilder);
            return this.documentSearch(indexNames, indexTypes, boolQueryBuilder, true, keywords, Pagination.cpn(pageNow), Pagination.cps(pageSize));
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
    
    @Override
    public Pagination documentSearchWithTerm(List<String> indexNames, List<String> indexTypes, String queryField, String keywords, Integer pageNow,
            Integer pageSize) throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("requestParams:"
                        + LogUtils.format("indexNames", indexNames, "indexTypes", indexTypes, "keywords", keywords, "pageNow", pageNow, "pageSize",
                                pageSize));
            }
            TermQueryBuilder termBuilder = this.termBuilder(queryField, keywords);
            return this.documentSearch(indexNames, indexTypes, termBuilder, true, Pagination.cpn(pageNow), Pagination.cps(pageSize));
        } catch (IOException e) {
            logger.error(
                    "搜索失败"
                            + LogUtils.format("indexNames", indexNames, "indexTypes", indexTypes, "keywords", keywords, "pageNow", pageNow,
                                    "pageSize", pageSize), e);
            throw new IOException("搜索失败", e);
        }
    }
    
    @Override
    public Pagination documentSearchWithMatch(List<String> indexNames, List<String> indexTypes, String queryField, String keywords, Integer pageNow,
            Integer pageSize) throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("requestParams:"
                        + LogUtils.format("indexNames", indexNames, "indexTypes", indexTypes, "keywords", keywords, "pageNow", pageNow, "pageSize",
                                pageSize));
            }
            MatchQueryBuilder matchBuilder = this.matchBuilder(queryField, keywords);
            return this.documentSearch(indexNames, indexTypes, matchBuilder, true, Pagination.cpn(pageNow), Pagination.cps(pageSize));
        } catch (IOException e) {
            logger.error(
                    "搜索失败"
                            + LogUtils.format("indexNames", indexNames, "indexTypes", indexTypes, "keywords", keywords, "pageNow", pageNow,
                                    "pageSize", pageSize), e);
            throw new IOException("搜索失败", e);
        }
    }
    
    @Override
    public Pagination documentSearchWithFunctionScore(List<String> indexNames, List<String> indexTypes, List<String> queryFields, String keywords, Integer pageNow,
            Integer pageSize) throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("requestParams:"
                        + LogUtils.format("indexNames", indexNames, "indexTypes", indexTypes, "keywords", keywords, "pageNow", pageNow, "pageSize",
                                pageSize));
            }
            FunctionScoreQueryBuilder functionScoreBuilder = this.functionScoreBuilder(keywords, queryFields);
            return this.documentSearch(indexNames, indexTypes, functionScoreBuilder, true, Pagination.cpn(pageNow), Pagination.cps(pageSize));
        } catch (IOException e) {
            logger.error(
                    "搜索失败"
                            + LogUtils.format("indexNames", indexNames, "indexTypes", indexTypes, "keywords", keywords, "pageNow", pageNow,
                                    "pageSize", pageSize), e);
            throw new IOException("搜索失败", e);
        }
    }
    
    @Override
    public Map<String, Object> documentSearchWithMatchPhrase(String indexName, String indexType, String queryField, List<String> hlFields, String keywords, Integer pageNow,
            Integer pageSize) throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("requestParams:"
                        + LogUtils.format("indexNames", indexName, "indexTypes", indexType, "keywords", keywords, "pageNow", pageNow, "pageSize",
                                pageSize));
            }
            MatchQueryBuilder matchPhraseBuilder = this.matchPhraseBuilder(keywords, queryField);
            return this.documentSearch(indexName, indexType, matchPhraseBuilder, true, hlFields, Pagination.cpn(pageNow), Pagination.cps(pageSize));
        } catch (IOException e) {
            logger.error(
                    "搜索失败"
                            + LogUtils.format("indexNames", indexName, "indexTypes", indexType, "keywords", keywords, "pageNow", pageNow,
                                    "pageSize", pageSize), e);
            throw new IOException("搜索失败", e);
        }
    }
    
    @Override
    public Map<String, Object> documentSearchWithFuzzy(String indexName, String indexType, String queryField, List<String> hlFields, String keywords, Integer pageNow,
            Integer pageSize) throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("requestParams:"
                        + LogUtils.format("indexNames", indexName, "indexTypes", indexType, "keywords", keywords, "pageNow", pageNow, "pageSize",
                                pageSize));
            }
            FuzzyQueryBuilder fuzzyBuilder = this.fuzzyBuilder(queryField, keywords);
            return this.documentSearch(indexName, indexType, fuzzyBuilder, true, hlFields, Pagination.cpn(pageNow), Pagination.cps(pageSize));
        } catch (IOException e) {
            logger.error(
                    "搜索失败"
                            + LogUtils.format("indexNames", indexName, "indexTypes", indexType, "keywords", keywords, "pageNow", pageNow,
                                    "pageSize", pageSize), e);
            throw new IOException("搜索失败", e);
        }
    }
    
    @Override
    public Map<String, Object> documentSearchWithFuzzy(String indexName, String indexType, String queryField, List<String> hlFields, Fuzziness fuzziness, String keywords, Integer pageNow,
            Integer pageSize) throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("requestParams:"
                        + LogUtils.format("indexNames", indexName, "indexTypes", indexType, "keywords", keywords, "pageNow", pageNow, "pageSize",
                                pageSize));
            }
            FuzzyQueryBuilder fuzzyBuilder = this.fuzzyBuilder(queryField, keywords, fuzziness);
            return this.documentSearch(indexName, indexType, fuzzyBuilder, true, hlFields, Pagination.cpn(pageNow), Pagination.cps(pageSize));
        } catch (IOException e) {
            logger.error(
                    "搜索失败"
                            + LogUtils.format("indexNames", indexName, "indexTypes", indexType, "keywords", keywords, "pageNow", pageNow,
                                    "pageSize", pageSize), e);
            throw new IOException("搜索失败", e);
        }
    }
    
    @Override
    public Map<String, Object> documentSearchWithMultiMatch(String indexName, String indexType, String[] queryFields, List<String> hlFields, String keywords, Integer pageNow,
            Integer pageSize) throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("requestParams:"
                        + LogUtils.format("indexNames", indexName, "indexTypes", indexType, "keywords", keywords, "pageNow", pageNow, "pageSize",
                                pageSize));
            }
            MultiMatchQueryBuilder multiMatchBuilder = this.multiMatchBuilder(queryFields, keywords);
            return this.documentSearch(indexName, indexType, multiMatchBuilder, true, hlFields, Pagination.cpn(pageNow), Pagination.cps(pageSize));
        } catch (IOException e) {
            logger.error(
                    "搜索失败"
                            + LogUtils.format("indexNames", indexName, "indexTypes", indexType, "keywords", keywords, "pageNow", pageNow,
                                    "pageSize", pageSize), e);
            throw new IOException("搜索失败", e);
        }
    }

    
    @Override
    public List<Map<String, Object>> documentSearchWithCustomerScore(String indexName, String indexType, List<String> queryFields, List<String> hlFields, String keywords, Integer pageNow,
            Integer pageSize) throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("requestParams:"
                        + LogUtils.format("indexNames", indexName, "indexTypes", indexType, "keywords", keywords, "pageNow", pageNow, "pageSize",
                                pageSize));
            }
            //总结果列表
            List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
            
            List<Map<String, Object>> totalResults = new ArrayList<Map<String, Object>>();
            //查询所有字段的完全匹配结果列表
            List<Map<String, Object>> allFieldListWithPerfectMatch = new ArrayList<Map<String, Object>>();
            //查询所有字段的slop为1的匹配结果列表
            List<Map<String, Object>> allFieldListWithMatchSlop1 = new ArrayList<Map<String, Object>>();
            //查询所有字段的slop为2的匹配结果列表
            List<Map<String, Object>> allFieldListWithMatchSlop2 = new ArrayList<Map<String, Object>>();
            //查询所有字段的slop为3的匹配结果列表
            List<Map<String, Object>> allFieldListWithMatchSlop3 = new ArrayList<Map<String, Object>>();
            //查询字符串进行搜索匹配
            List<Map<String, Object>> allFieldListWithQueryString = new ArrayList<Map<String, Object>>();
            //模糊查询
            List<Map<String, Object>> allFieldListWithFuzzy = new ArrayList<Map<String, Object>>();
            for(String field : queryFields){
                //完全匹配
                MatchQueryBuilder mqb = this.matchPhraseBuilder(keywords, field, 0);
                List<Map<String, Object>> resultList = this.documentSearchWithList(indexName, indexType, mqb, true, hlFields, ES_SCORE_PERFECT_MATCH, Pagination.cpn(pageNow), Pagination.cps(pageSize));
                allFieldListWithPerfectMatch.addAll(resultList);
                
                //不完全匹配 slop=1
                MatchQueryBuilder mqb1 = this.matchPhraseBuilder(keywords, field, 1);
                List<Map<String, Object>> resultList1 = this.documentSearchWithList(indexName, indexType, mqb1, true, hlFields, ES_SCORE_MATCH_SLOP_1, Pagination.cpn(pageNow), Pagination.cps(pageSize));
                allFieldListWithMatchSlop1.addAll(resultList1);
                
                //不完全匹配 slop=2
                MatchQueryBuilder mqb2 = this.matchPhraseBuilder(keywords, field, 2);
                List<Map<String, Object>> resultList2 = this.documentSearchWithList(indexName, indexType, mqb2, true, hlFields, ES_SCORE_MATCH_SLOP_2, Pagination.cpn(pageNow), Pagination.cps(pageSize));
                allFieldListWithMatchSlop2.addAll(resultList2);
                
                //不完全匹配 slop=3
                MatchQueryBuilder mqb3 = this.matchPhraseBuilder(keywords, field, 3);
                List<Map<String, Object>> resultList3 = this.documentSearchWithList(indexName, indexType, mqb3, true, hlFields, ES_SCORE_MATCH_SLOP_3, Pagination.cpn(pageNow), Pagination.cps(pageSize));
                allFieldListWithMatchSlop3.addAll(resultList3);
            }
            //查询字符串进行搜索
            QueryStringQueryBuilder queryStringBuilder = this.queryStringBuilder(keywords, queryFields);
            List<Map<String, Object>> resultList = this.documentSearchWithList(indexName, indexType, queryStringBuilder, true, hlFields, ES_SCORE_MATCH_QUERY_STR, Pagination.cpn(pageNow), Pagination.cps(pageSize));
            allFieldListWithQueryString.addAll(resultList);
            
            //模糊查询
            if(null != queryFields && !queryFields.isEmpty()){
                String[] strings = new String[queryFields.size()];
                FuzzyLikeThisQueryBuilder fuzzyLikeThisBuilder = this.fuzzyLikeThisBuilder(queryFields.toArray(strings), keywords);
                List<Map<String, Object>> resultListWithFuzzy = this.documentSearchWithList(indexName, indexType, fuzzyLikeThisBuilder, true, hlFields, ES_SCORE_MATCH_FUZZY, Pagination.cpn(pageNow), Pagination.cps(pageSize));
                allFieldListWithFuzzy.addAll(resultListWithFuzzy);
            }
            
            //记录不匹配的个数
            int count = 0;
            Map<String, Object> perfectMatchMap = new HashMap<String, Object>();
            //完全匹配得分设置
            if(!allFieldListWithPerfectMatch.isEmpty()){
                perfectMatchMap.put(ES_SCORE, ES_SCORE_PERFECT_MATCH);
                perfectMatchMap.put(ES_RESULT, allFieldListWithPerfectMatch);
                results.add(perfectMatchMap);
                totalResults.addAll(allFieldListWithPerfectMatch);
            }else{
                count += 1;
            }
            Map<String, Object> perfectMatchMapSlop1 = new HashMap<String, Object>();
            //部分匹配slop=1
            if(!allFieldListWithMatchSlop1.isEmpty()){
                perfectMatchMapSlop1.put(ES_SCORE, ES_SCORE_MATCH_SLOP_1);
                perfectMatchMapSlop1.put(ES_RESULT, allFieldListWithMatchSlop1); 
                results.add(perfectMatchMapSlop1);
                totalResults.addAll(allFieldListWithMatchSlop1);
            }else{
                count += 1;
            }
            Map<String, Object> perfectMatchMapSlop2 = new HashMap<String, Object>();
            //部分匹配slop=2
            if(!allFieldListWithMatchSlop2.isEmpty()){
                perfectMatchMapSlop2.put(ES_SCORE, ES_SCORE_MATCH_SLOP_2);
                perfectMatchMapSlop2.put(ES_RESULT, allFieldListWithMatchSlop2); 
                results.add(perfectMatchMapSlop2);
                totalResults.addAll(allFieldListWithMatchSlop2);
            }else{
                count += 1;
            }
            Map<String, Object> perfectMatchMapSlop3 = new HashMap<String, Object>();
            //部分匹配slop=3
            if(!allFieldListWithMatchSlop3.isEmpty()){
                perfectMatchMapSlop3.put(ES_SCORE, ES_SCORE_MATCH_SLOP_3);
                perfectMatchMapSlop3.put(ES_RESULT, allFieldListWithMatchSlop3); 
                results.add(perfectMatchMapSlop3);
                totalResults.addAll(allFieldListWithMatchSlop3);
            }else{
                count += 1;
            }
            Map<String, Object> perfectMatchQueryStr = new HashMap<String, Object>();
            //查询字符串匹配
            if(!allFieldListWithQueryString.isEmpty()){
                perfectMatchQueryStr.put(ES_SCORE, ES_SCORE_MATCH_QUERY_STR);
                perfectMatchQueryStr.put(ES_RESULT, allFieldListWithQueryString);
                results.add(perfectMatchQueryStr);
                totalResults.addAll(allFieldListWithQueryString);
            }else{
                count += 1;
            }
            Map<String, Object> fuzzyMatch = new HashMap<String, Object>();
            //模糊匹配
            if(!allFieldListWithFuzzy.isEmpty()){
                fuzzyMatch.put(ES_SCORE, ES_SCORE_MATCH_FUZZY);
                fuzzyMatch.put(ES_RESULT, allFieldListWithFuzzy);
                results.add(fuzzyMatch);
                totalResults.addAll(allFieldListWithFuzzy);
            }else{
                count += 1;
            }
            Map<String, Object> notMustMatchMap = new HashMap<String, Object>();
            //完全不匹配
            if(count == 6){
                notMustMatchMap.put(ES_SCORE, ES_SCORE_NOT_MUST_MATCH);
                totalResults.add(notMustMatchMap);
            }
            Set<String> hashSet = new HashSet<String>();
            List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
            for (Iterator<Map<String, Object>> iterator = totalResults.iterator(); iterator.hasNext();) {
                Map<String, Object> map = iterator.next();
                String id = String.valueOf(map.get("id"));
                if(hashSet.add(id)){
                    newlist.add(map);
                }
            }
            return newlist;
        } catch (IOException e) {
            logger.error(
                    "搜索失败"
                            + LogUtils.format("indexNames", indexName, "indexTypes", indexType, "keywords", keywords, "pageNow", pageNow,
                                    "pageSize", pageSize), e);
            throw new IOException("搜索失败", e);
        }
    }
    
    public boolean documentHandlerWith(String indexName, String indexType, List<ESDto> documents, Integer action) throws IOException {
        try {
            List<ESDto> list = new ArrayList<ESDto>();
            for (ESDto bean : documents) {
                // 获取文件上传的目录
//                String dir = sysConfigEs.getProperty("data.import");
                String dir = FILE_UPLOAD_DIR;
                String url = dir + bean.getPath();
                String text = ParseDocumentUtil.getText(url);
                String fileName = FileUtils.getFileNameByUrl(url);
                bean.setDescription(text);
                bean.setName(fileName);
                list.add(bean);
            }
          return documentHandlerWithDto(indexName, indexType, documents, action);
        } catch (IOException e) {
            logger.error("批量生成索引失败: " + LogUtils.format("documents", documents), e);
            throw new IOException("批量生成索引失败", e);
        }
    }
    
    @Override
    public boolean dirHandler(String indexName, String indexType, String path, Integer action) throws IOException {
        try {
            List<File> listFiles = FileUtils.getListFiles(path, null);
            List<ESDto> documents = new ArrayList<ESDto>();
            for (File file : listFiles) {
                String text = ParseDocumentUtil.getText(file.getAbsolutePath());
                ESDto dto = new ESDto();
                dto.setDescription(StringUtils.substring(text, 0, 200));
                dto.setName(file.getName());
                dto.setContent(text);
                dto.setPath(file.getName());
                documents.add(dto);
            }
          return documentHandlerWithDto(indexName, indexType, documents, action);
        } catch (IOException e) {
            logger.error("批量生成索引失败: " + LogUtils.format("", e));
            throw new IOException("批量生成索引失败", e);
        } catch (Exception e) {
            logger.error("索引失败", e);
            e.printStackTrace();
        }
        return false;
    }
    
    private List<String> buildFieldList(List<String> prefixes, List<String> suffixes){
        List<String> newList = new ArrayList<String>();
        for (String prefix : prefixes) {
            for(String suffix : suffixes){
                String field = prefix + "." + suffix;
                newList.add(field);
            }
            newList.add(prefix);
        }
        return newList;
    }
    
    public Map<String, Object> documentSearch(String indexName, String indexType, String keywords, Integer pageNow,
            Integer pageSize) throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("requestParams:"
                        + LogUtils.format("indexNames", indexName, "indexTypes", indexType, "keywords", keywords, "pageNow", pageNow, "pageSize",
                                pageSize));
            }
            List<String> fields = new ArrayList<String>();
            fields.add("NAME");
            fields.add("COUNTRY");
            List<String> hlSubFields = new ArrayList<String>();
            hlSubFields.add("en");
            hlSubFields.add("t2s");
            hlSubFields.add("russian");
            hlSubFields.add("py_only");
            hlSubFields.add("py_none");
            hlSubFields.add("french");
            hlSubFields.add("prototype");
            hlSubFields.add("raw");
            List<String> hlFields = buildFieldList(fields, hlSubFields);
            BoolQueryBuilder boolQueryBuilder = this.boolQueryBuilder();
            QueryStringQueryBuilder queryStringBuilder = this.queryStringBuilder(keywords, hlFields);
//            FuzzyQueryBuilder nameFuzzyBuilder = this.fuzzyBuilder("NAME.prototype", keywords, Fuzziness.TWO);
//            FuzzyQueryBuilder countryFuzzyBuilder = this.fuzzyBuilder("COUNTRY.prototype", keywords, Fuzziness.TWO);
            WildcardQueryBuilder nameWildcardBuilder = this.wildcardBuilder("NAME.raw", keywords);
            WildcardQueryBuilder countryWildcardBuilder = this.wildcardBuilder("COUNTRY.raw", keywords);
            
            boolQueryBuilder.should(queryStringBuilder).should(nameWildcardBuilder).should(countryWildcardBuilder);
            List<String> similarityFields = new ArrayList<String>();
            similarityFields.add("NAME");
            similarityFields.add("COUNTRY");
            return this.documentSearch(indexName, indexType, boolQueryBuilder, true, hlFields, similarityFields, keywords, Pagination.cpn(pageNow), Pagination.cps(pageSize));
        } catch (IOException e) {
            logger.error(
                    "搜索失败"
                            + LogUtils.format("indexNames", indexName, "indexTypes", indexType, "keywords", keywords, "pageNow", pageNow,
                                    "pageSize", pageSize), e);
            throw new IOException("搜索失败", e);
        }
    }
}
