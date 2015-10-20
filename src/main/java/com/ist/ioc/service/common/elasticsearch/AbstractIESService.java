package com.ist.ioc.service.common.elasticsearch;

import static com.ist.ioc.service.common.Constants.*;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.Search.Builder;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import io.searchbox.core.Update;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.PutMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.analysis.NamedAnalyzer;
import org.elasticsearch.index.analysis.PinyinAnalyzer;
import org.elasticsearch.index.analysis.STConvertAnalyzer;
import org.elasticsearch.index.mapper.DocumentMapper;
import org.elasticsearch.index.mapper.core.StringFieldMapper;
import org.elasticsearch.index.mapper.object.RootObjectMapper;
import org.elasticsearch.index.query.FuzzyLikeThisFieldQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.RegexpQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.SuggestBuilder.SuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.ist.assemble.CustomImmutableSetting;
import com.ist.common.es.util.DateUtils;
import com.ist.common.es.util.JsonUtils;
import com.ist.common.es.util.LogUtils;
import com.ist.common.es.util.page.Pagination;
import com.ist.dto.bmp.ESDto;

/**
 * 提供创建映射，索引，搜索等服务
 * 
 * @author qianguobing
 */
public abstract class AbstractIESService implements IESService {

    private static final Log logger = LogFactory.getLog(AbstractIESService.class);
	@Resource
	public JestClient client = null;
	@Resource
	private CustomImmutableSetting customImmutableSetting = null;
	@Autowired
    SearchSourceBuilder searchSourceBuilder = null;
	
	/**
     * <p>
     * 批量添加索引文档
     * <p>
     * <p>
     * 这里使用bulk api 它会极大提高索引文档的速度
     * </p>
     * 
     * @param indexParams
     *            索引参数Map
     *            <p>
     *            Map中String分别key,value对应于分行，与支行列表 对应于es中的索引名称和索引类型
     *            </p>
     * @param documents
     *            要索引的文档列表
     * @param action
     *            处理文档动作 1:创建或更新 2:删除3:局部更新
     * @throws IOException 
     */
    public boolean documentHandler(Map<String, List<String>> mapParams, List<ESDto> documents, Integer action) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug(LogUtils.format("indexParams", mapParams, "documents", documents));
        }
        try {
            if (null != mapParams && !mapParams.isEmpty()) {
                for (Entry<String, List<String>> entry : mapParams.entrySet()) {
                    String key = entry.getKey();
                    String index = StringUtils.lowerCase(key);
                    List<String> typeList = entry.getValue();
                    // 删除索引
                     this.deleteIndex(index, null);
                    // 创建索引
                    this.addIndex(index);
                    Bulk.Builder builder = new Bulk.Builder().defaultIndex(index);
                    if (null != typeList && !typeList.isEmpty()) {
                        for (String type : typeList) {
                            // 创建文档
                            if (action == ES_ADD_ACTION) {
                                // 创建索引映射
                                createIndexMapping(index, type);
                                // 若文档为空则只创建索引类型
                                if (null != documents && !documents.isEmpty()) {
                                    builder.addAction(Arrays.asList(buildIndexDtoAction(documents)));
                                }
                                // 删除文档
                            } else if (action == ES_DELETE_ACTION && null != documents && !documents.isEmpty()) {
                                builder.addAction(Arrays.asList(buildDeleteDtoAction(documents)));
                            } else if (action == ES_UPDATE_ACTION && null != documents && !documents.isEmpty()) {
                                builder.addAction(Arrays.asList(buildUpdateDtoAction(documents)));
                            }
                            builder.defaultType(type);
                            client.execute(builder.build());
                        }
                    }
                }
            }
            return true;
        } catch (IOException e) {
            logger.debug("构建索引败:" + LogUtils.format("indexParams", mapParams, "documents", documents), e);
            throw new IOException("构建索引败", e);
        } 
    }
	
	public boolean documentHandler(String indexName, String indexType, List<Map<String, Object>> documents, Integer action) throws IOException {
        try {
            // es库名只能是小写字母
            indexName = StringUtils.lowerCase(indexName);
            // 删除索引
             this.deleteIndex(indexName, indexType);
            // 创建索引
            this.addIndex(indexName);
            // 设置默认的索引名字
            Bulk.Builder builder = new Bulk.Builder().defaultIndex(indexName);
            // 创建文档
            if (action == ES_ADD_ACTION && !documents.isEmpty()) {
                // 创建索引映射
                createIndexMapping(indexName, indexType);
                // 若文档为空则只创建索引类型
                builder.addAction(Arrays.asList(buildIndexAction(documents)));
                // 删除文档
            } else if (action == ES_DELETE_ACTION && !documents.isEmpty()) {
                builder.addAction(Arrays.asList(buildDeleteAction(documents)));
            } else if (action == ES_UPDATE_ACTION && !documents.isEmpty()) {
                builder.addAction(Arrays.asList(buildUpdateAction(documents)));
            }
            builder.defaultType(indexType);
            JestResult response = client.execute(builder.build());
            if (response.isSucceeded()) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            logger.error("构建索引败:" + LogUtils.format("indexName", indexName, "indexType", indexType, "documents", documents), e);
            throw new IOException("构建索引败", e);
        }
    }
	
	/**
     * 构建批量创建索引动作
     * 
     * @param documents
     *            文档列表
     * @return Index[] 返回添加动作
     */
    private Index[] buildIndexDtoAction(List<ESDto> documents) {
        Index[] indexs = new Index[documents.size()];
        for (int i = 0; i < documents.size(); i++) {
            ESDto bean = documents.get(i);
            bean.setCreateTime(DateUtils.getCurrTime());
            //获取文件上传的目录
//            String dir = sysConfigEs.getProperty("data.import");
//            String url = dir + bean.getPath();
//            String description = bean.getDescription();
//            if(null == description){
//                String text = ParseDocumentUtil.getText(url);
//                String fileName = FileUtils.getFileNameByUrl(url);
//                bean.setDescription(text);
//                bean.setName(fileName);
//            }
            indexs[i] = new Index.Builder(bean).build();
        }
        return indexs;
    }
    
    /**
     * 构建批量删除动作
     * 
     * @param documents
     *            文档列表
     * @return Delete[] 返回删除动作
     */
    private Delete[] buildDeleteDtoAction(List<ESDto> documents) {
        Delete[] deleteAction = new Delete[documents.size()];
        for (int i = 0; i < documents.size(); i++) {
            ESDto bean = documents.get(i);
            deleteAction[i] = new Delete.Builder(bean.getId()).build();
        }
        return deleteAction;
    }
    
    /**
     * 构建批量更新动作
     * 
     * @param documents
     *            文档列表
     * @return Update[] 返回更新动作
     */
    private Update[] buildUpdateDtoAction(List<ESDto> documents) {
        Update[] updateAction = new Update[documents.size()];
        for (int i = 0; i < documents.size(); i++) {
            Map<String, Object> update = new HashMap<String, Object>();
            ESDto dto = documents.get(i);
            String id = dto.getId();
            update.put(ES_UPDATE_DOC, dto);
            updateAction[i] = new Update.Builder(update).id(id).build();
        }
        return updateAction;
    }
	
	/**
     * 构建批量创建索引动作
     * 
     * @param documents
     *            文档列表
     * @return Index[] 返回添加动作
     */
    private Index[] buildIndexAction(List<Map<String, Object>> documents) {
        Index[] indexs = new Index[documents.size()];
        for (int i = 0; i < documents.size(); i++) {
            Map<String, Object> map = documents.get(i);
            map.put(ES_INDEX_CREATE_TIME, DateUtils.getCurrTime());
            String id = String.valueOf(map.get(ES_RESULT_KEY));
            indexs[i] = new Index.Builder(map).id(id).build();
        }
        return indexs;
    }

    /**
     * 构建批量删除动作
     * 
     * @param documents
     *            文档列表
     * @return Delete[] 返回删除动作
     */
    private Delete[] buildDeleteAction(List<Map<String, Object>> documents) {
        Delete[] deleteAction = new Delete[documents.size()];
        for (int i = 0; i < documents.size(); i++) {
            Map<String, Object> map = documents.get(i);
            String id = String.valueOf(map.get(ES_RESULT_KEY));
            deleteAction[i] = new Delete.Builder(id).build();
        }
        return deleteAction;
    }
    
    /**
     * 构建批量更新动作
     * 
     * @param documents
     *            文档列表
     * @return Update[] 返回更新动作
     */
    private Update[] buildUpdateAction(List<Map<String, Object>> documents) {
        Update[] updateAction = new Update[documents.size()];
        for (int i = 0; i < documents.size(); i++) {
            Map<String, Object> update = new HashMap<String, Object>();
            Map<String, Object> map = documents.get(i);
            String id = (String) map.get("id");
            update.put(ES_UPDATE_DOC, map);
            updateAction[i] = new Update.Builder(update).id(id).build();
        }
        return updateAction;
    }
	
	/**
	 * 创建索引
	 * 
	 * @param index
	 *            索引名称
	 * @param type
	 *            索引类型
	 */
	private void createIndexMapping(String index, String type) {
		try {
			logger.debug("params: "
					+ LogUtils.format("index", index, "type", type));
//			String mappingJsonStr = buildMappingJsonStr(type);
			String mappingJsonStr = buildMappingJsonStr(index, type);
			PutMapping putMapping = new PutMapping.Builder(index, type,
					mappingJsonStr).build();
			client.execute(putMapping);
		} catch (IOException e) {
			logger.debug(
					"创建索引映射失败 : "
							+ LogUtils.format("index", index, "type", type), e);
		} 
	}

	/**
	 * 通过一个根映射器构建器去设置各个字段来构建映射源
	 * 
	 * @param index
	 *            索引名称
	 * @param type
	 *            索引类型
	 * @return String 映射字符串
	 */
	protected String buildMappingJsonStr(String index, String type) {
		try {
			// 创建ik分词器
			Analyzer ik = new IKAnalyzer();
			Settings settings = customImmutableSetting.getBuilder().build();
            Analyzer py = new PinyinAnalyzer(settings);
            Analyzer en = new EnglishAnalyzer(Version.LUCENE_47);
            STConvertAnalyzer st = new STConvertAnalyzer(settings);
            StandardAnalyzer sa = new StandardAnalyzer(Version.LUCENE_47);
            
            NamedAnalyzer naIk = new NamedAnalyzer(ES_IK, ik);
            NamedAnalyzer naPy = new NamedAnalyzer(ES_PINYIN_ANALYZER, py);
            NamedAnalyzer naEn = new NamedAnalyzer(ES_ENGLISH, en);
//            NamedAnalyzer naSt = new NamedAnalyzer(ES_S2T_CONVERT, st);
            NamedAnalyzer naSt = new NamedAnalyzer(ES_T2S_CONVERT, st);
			// 创建拼音分词器
			RootObjectMapper.Builder rootObjectMapperBuilder = new RootObjectMapper.Builder(
					type);
			org.elasticsearch.index.mapper.core.StringFieldMapper.Builder titleBuilder = getStringFieldBuilder(ES_FIELD_TITLE, naIk)
                    .store(true).index(true);
			org.elasticsearch.index.mapper.core.StringFieldMapper.Builder nameBuilder = getStringFieldBuilder(ES_FIELD_NAME, naSt)
                    .store(true).index(true);
			org.elasticsearch.index.mapper.core.StringFieldMapper.Builder descBuilder = getStringFieldBuilder(ES_FIELD_DESCRIPTION, naIk)
                    .store(true).index(true);
			org.elasticsearch.index.mapper.core.StringFieldMapper.Builder pathBuilder = getStringFieldBuilder(ES_FIELD_PATH, naEn)
			        .store(true).index(true);
			//增加拼音字段子级
			org.elasticsearch.index.mapper.core.StringFieldMapper.Builder pinyinBuilder = getStringFieldBuilder(ES_FIELD_PY, naPy)
			        .store(true).index(true);
			//增加英文字段
			org.elasticsearch.index.mapper.core.StringFieldMapper.Builder enBuilder = getStringFieldBuilder(ES_FIELD_EN, naEn)
                    .store(true).index(true);
			org.elasticsearch.index.mapper.core.StringFieldMapper.Builder contentBuilder = getStringFieldBuilder(ES_FIELD_CONTENT, naIk)
                    .store(false).index(true).addMultiField(pinyinBuilder).addMultiField(enBuilder);
			rootObjectMapperBuilder.add(contentBuilder).add(titleBuilder).add(descBuilder).add(nameBuilder).add(pathBuilder);
			DocumentMapper documentMapper = new DocumentMapper.Builder(index,
					null, rootObjectMapperBuilder).build(null);
			String expectedMappingSource = documentMapper.mappingSource()
					.toString();
			return expectedMappingSource;
		} catch (Exception e) {
			logger.debug("构建映射字符串失败 " + LogUtils.format("type", type), e);
			throw new RuntimeException("构建映射字符串失败", e);
		}
	}
	
	private org.elasticsearch.index.mapper.core.StringFieldMapper.Builder getStringFieldBuilder(String field, NamedAnalyzer na){
	    org.elasticsearch.index.mapper.core.StringFieldMapper.Builder fieldBuilder = new StringFieldMapper.Builder(field).indexAnalyzer(na).searchAnalyzer(na);
	    return fieldBuilder;
	}
	
	 /**
     * 
     * "1364": { "_timestamp": { "enabled": true, "store": true, "path":
     * "createTime", "format": "yyyy-MM-dd HH:mm:ss" }, "properties": { "id": {
     * "type": "string" }, "createTime": { "store": true, "format":
     * "yyyy-MM-dd HH:mm:ss", "type": "date" }, "title": { "type": "string" },
     * "description": { "store": true, "analyzer": "ik", "boost": 4,
     * "term_vector": "with_positions_offsets", "type": "string", "fields": {
     * "py": { "store": true, "analyzer": "pinyin_analyzer", "boost": 4,
     * "term_vector": "with_positions_offsets", "type": "string" } } }, "path":
     * { "type": "string" } }, "_all": { "auto_boost": true } }
     * 通过一个根映射器构建器去设置各个字段来构建映射源
     * 
     * @param index
     *            索引名称
     * @param type
     *            索引类型
     * @return String 映射字符串
     * @throws IOException 
     */
	/*private String buildMappingJsonStr(String type) throws IOException {
        try {
            XContentBuilder content = XContentFactory
                    .jsonBuilder()
                    .startObject()
                    // 索引库名（类似数据库中的表）
                    .startObject(type).startObject("_timestamp").field("enabled", true).field("store", "yes").field("path", ES_CREATETIME)
                    .field("format", "yyyy-MM-dd HH:mm:ss").endObject().startObject("properties").startObject("id").field("type", "string")
                    .endObject().startObject(ES_TITLE).field("type", "string").endObject().startObject(ES_PATH).field("type", "string").endObject()
                    .startObject(ES_CREATETIME).field("type", "date").field("store", "yes").field("format", "yyyy-MM-dd HH:mm:ss").endObject()
                    .startObject(ES_DESCRIPTION).field("type", "multi_field").field("index", "analyzed").startObject("fields")
                    .startObject(ES_DESCRIPTION).field("type", "string").field("store", "yes").field("index", "analyzed")
                    .field("indexAnalyzer", "ik").field("searchAnalyzer", "ik").field("term_vector", "with_positions_offsets")
                    .field("boost", 4.0)
                    // 打分(默认1.0)
                    .endObject().startObject("py").field("type", "string").field("store", "yes").field("index", "analyzed")
                    .field("indexAnalyzer", "pinyin_analyzer").field("searchAnalyzer", "pinyin_analyzer")
                    .field("term_vector", "with_positions_offsets").field("include_in_all", "false").field("boost", 4.0) // 打分(默认1.0)
                    .endObject().endObject().endObject().endObject().endObject().endObject();
            logger.debug(LogUtils.format("----------------映射字符串---------------", content.string()));
            return content.string();
        } catch (IOException e) {
            logger.debug("构建映射字符串失败 " + LogUtils.format("type", type), e);
            throw new IOException("构建映射字符串失败", e);
        }
    }*/

	/**
	 * 根据字段进行搜索，支持多个索引类型
	 * @param indexNames 索引名称列表
	 * @param indexTypes 索引类型列表
	 * @param queryQuery 搜索条件
	 * @param isHighlight 是否高亮
	 * @param hlFields 高亮字段
	 * @param pageNow 当前页
	 * @param pageSize 页大小
	 * @return Map<String, Object> 查询结果集，带分页参数
	 * @throws IOException
	 */
	protected Map<String, Object> documentSearch(List<String> indexNames, List<String> indexTypes,
            QueryBuilder queryQuery, Boolean isHighlight, List<String> hlFields, Integer pageNow, Integer pageSize) throws IOException {
        try {
            Builder builder = getSearchBuilder(queryQuery, isHighlight, hlFields, pageNow, pageSize);
            // 构建搜索
            SearchResult result = searchResultExecute(indexNames, indexTypes, builder);
            Map<String, Object> resultMap = searchResultHandler(result, hlFields, pageNow, pageSize);
            logger.debug("--------------------------------结果--------------------------" + LogUtils.format("r", resultMap));
            return resultMap;
        } catch (IOException e) {
            logger.error("查询失败 ", e);
            throw new IOException("查询失败 ", e);
        }
    }
    
    /**
     * 根据字段进行搜索
     * @param indexName 索引名称
     * @param indexType 索引类型
     * @param queryQuery 搜索条件
     * @param isHighlight 是否高亮
     * @param hlFields 高亮字段
     * @param pageNow 当前页
     * @param pageSize 页大小
     * @return Map<String, Object> 查询结果集，带分页参数
     * @throws IOException
     */
    protected Map<String, Object> documentSearch(String indexName, String indexType,
            QueryBuilder queryQuery, Boolean isHighlight, List<String> hlFields, Integer pageNow, Integer pageSize) throws IOException {
        try {
            Builder builder = getSearchBuilder(queryQuery, isHighlight, hlFields, pageNow, pageSize);
            // 构建搜索
            SearchResult result = searchResultExecute(indexName, indexType, builder);
            Map<String, Object> resultMap = searchResultHandler(result, hlFields, pageNow, pageSize);
            logger.debug("--------------------------------结果--------------------------" + LogUtils.format("r", resultMap));
            return resultMap;
        } catch (IOException e) {
            logger.error("查询失败 ", e);
            throw new IOException("查询失败 ", e);
        }
    }
    
    /**
     * 根据字段进行搜索
     * @param indexName 索引名称
     * @param indexType 索引类型
     * @param queryQuery 搜索条件
     * @param isHighlight 是否高亮
     * @param pageNow 当前页
     * @param pageSize 页大小
     * @return Map<String, Object> 查询结果集，带分页参数
     * @throws IOException
     */
    protected Pagination documentSearch(List<String> indexNames, List<String> indexTypes,
            QueryBuilder queryQuery, Boolean isHighlight, Integer pageNow, Integer pageSize) throws IOException {
        try {
            Builder builder = getSearchBuilder(queryQuery, isHighlight, pageNow, pageSize);
            // 构建搜索
            SearchResult result = searchResultExecute(indexNames, indexTypes, builder);
            Pagination p = searchResultHandler(result, pageNow, pageSize);
            logger.debug("--------------------------------结果--------------------------" + LogUtils.format("r", p));
            return p;
        } catch (IOException e) {
            logger.error("查询失败 ", e);
            throw new IOException("查询失败 ", e);
        }
    }

    /**
     * 返回查询结果
     * @param indexName 索引名称
     * @param indexType 索引类型
     * @param builder 查询构建器
     * @return
     * @throws IOException
     */
    private SearchResult searchResultExecute(String indexName, String indexType, Builder builder) throws IOException {
        Search search = builder.addIndex(indexName).addType(indexType).allowNoIndices(true).ignoreUnavailable(true).build();
        SearchResult result = client.execute(search);
        return result;
    }
    
    /**
     * 返回查询结果，支持多个索引类型
     * @param indexNames 索引名称列表
     * @param indexTypes 索引类型列表
     * @param builder 查询构建器
     * @return
     * @throws IOException
     */
    private SearchResult searchResultExecute(List<String> indexNames, List<String> indexTypes, Builder builder) throws IOException {
        Search search = builder.addIndex(indexNames).addType(indexTypes).allowNoIndices(true).ignoreUnavailable(true).build();
        SearchResult result = client.execute(search);
        return result;
    }
    
    /**
     * 获取查询构建器
     * @param queryQuery 
     * @param isHighlight 是否高亮
     * @param hlFields 高亮字段
     * @param pageNow 当前页
     * @param pageSize 页大小
     * @return Builder 
     */
    private Builder getSearchBuilder(QueryBuilder queryQuery, Boolean isHighlight, List<String> hlFields, Integer pageNow, Integer pageSize) {
        SearchSourceBuilder ssb = searchSourceBuilder.query(queryQuery);
        // 设置分页
        ssb.from(Pagination.getCurrentNum(pageNow, pageSize)).size(pageSize);
        // 设置排序方式
        ssb.sort("_score", SortOrder.DESC);
        if(isHighlight){
            HighlightBuilder highlight = SearchSourceBuilder.highlight();
            HighlightBuilder hlb = highlight.preTags("<b>").postTags("</b>");
            if(null != hlFields){
                for (String field : hlFields) {
                    hlb.field(field);
                }
            }
            ssb.highlight(hlb);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("query string: " + ssb.toString());
        }
        Builder builder = new Search.Builder(ssb.toString());
        return builder;
    }

    /**
     * 获取查询构建器
     * @param queryQuery 
     * @param isHighlight 是否高亮
     * @param pageNow 当前页
     * @param pageSize 页大小
     * @return Builder 
     */
    private Builder getSearchBuilder(QueryBuilder queryQuery, Boolean isHighlight, Integer pageNow, Integer pageSize) {
        SearchSourceBuilder ssb = searchSourceBuilder.query(queryQuery);
        // 设置分页
        ssb.from(Pagination.getCurrentNum(pageNow, pageSize)).size(pageSize);
        // 设置排序方式
        ssb.sort("_score", SortOrder.DESC);
//        SuggestionBuilder<?> suggestion = new TermSuggestionBuilder("suggestion").field("path").text("你好").analyzer("english");
//        ssb.suggest().addSuggestion(suggestion);
        if(isHighlight){
            HighlightBuilder highlight = SearchSourceBuilder.highlight();
            HighlightBuilder hlb = highlight.preTags("<b>").postTags("</b>");
            hlb.field(ES_FIELD_TITLE).field(ES_FIELD_DESCRIPTION);
            ssb.highlight(hlb);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("query string: " + ssb.toString());
        }
        Builder builder = new Search.Builder(ssb.toString());
        return builder;
    }
    
    /**
     * 查询结果处理
     * @param result 待处理的查询结果
     * @param fields 查询字段
     * @param pageNow 当前页
     * @param pageSize 页大小
     * @return Map<String, Object>
     */
    @SuppressWarnings({ "rawtypes"})
    private Map<String, Object> searchResultHandler(SearchResult result, List<String> hlFields, Integer pageNow, Integer pageSize){
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Pagination p = null;
        Integer totalCount = 0;
        if(null != result && result.isSucceeded()){
            //构建分页对象
            p = getPagination(result, pageNow, pageSize);
            //获取命中对象
            List<Hit<Map, Void>> hits = result.getHits(Map.class);
            for (Hit<Map, Void> hit : hits) {
               //得到高亮标识的Map
               Map<String, Object> source = getHighlightMap(hlFields, hit);
               list.add(source);
            }
        }
        //当前页
        map.put(ES_PAGE_ID, pageNow);
        //页大小
        map.put(ES_PAGE_SIZE, pageSize);
        //总页数
        map.put(ES_TOTAL_PAGE, null == p ? 1 : p.getTotalPage());
        //总记录数
        map.put(ES_TOTAL_SIZE, totalCount);
        //结果集
        map.put(ES_RESULT, list);
        return map;
    }
    
    /**
     * 查询结果处理
     * @param result 待处理的查询结果
     * @param pageNow 当前页
     * @param pageSize 页大小
     * @return Pagination 分页对象
     */
    private Pagination searchResultHandler(SearchResult result, Integer pageNow, Integer pageSize){
        Pagination p = null;
        List<ESDto> list = new ArrayList<ESDto>();
        if(null != result && result.isSucceeded()){
            p = getPagination(result, pageNow, pageSize);
            List<Hit<ESDto, Void>> hits = result.getHits(ESDto.class);
            for (Hit<ESDto, Void> hit : hits) {
                ESDto source = hit.source;
                Map<String, List<String>> hl = hit.highlight;
                if (null != hl) {
                    List<String> titles = hl.get(ES_FIELD_TITLE);
                    List<String> des = hl.get(ES_FIELD_DESCRIPTION);
                    if(null != titles){
                        source.setTitle(titles.get(0));
                    }
                    if(null != des){
                        source.setDescription(des.get(0));
                    }
                    
                }
                list.add(source);
               p.setList(list);
            }
        }
        return p;
    }

    /**
     * 构建分页对象
     * @param result
     * @param pageNow
     * @param pageSize
     * @return Pagination
     */
    private Pagination getPagination(SearchResult result, Integer pageNow, Integer pageSize) {
        Pagination p;
        Integer totalCount = 0;
        totalCount = result.getTotal();
        p = new Pagination(pageNow, pageSize, totalCount);
        return p;
    }

    /**
     * 获取设置高亮之后的对象
     * @param fields 高亮的字段
     * @param hit 命中的对象
     * @return Map<String, Object>
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Map<String, Object> getHighlightMap(List<String> hlFields, Hit<Map, Void> hit) {
        Map<String, Object> source = hit.source;
           Map<String, List<String>> highlight = hit.highlight;
           if(null != highlight){
               if(null != hlFields){
                   for (String field : hlFields) {
                       if(highlight.containsKey(field)){
                           List<String> h = highlight.get(field);
                           source.put(field, h.get(0));
                       }
                   }
               }
           }
        return source;
    }
    
    /**
     * 根据关键字带分页的搜索文档
     * 
     * @param query
     *            搜索关键字
     * @param indexNames
     *            索引名称列表，可以搜索多个索引库
     * @param indexTypes
     *            索引类型，可以搜索多个索引类型
     * @param pageNow
     *            当前页no
     * @param pageSize
     *            每页显示数量
     * @return List<ESDto> eSDto列表
     * @throws IOException 
     */
   /* public List<ESDto> documentSearch(String query, List<String> indexNames, List<String> indexTypes, Integer pageNow, Integer pageSize) throws IOException {
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            // 使用query_string进行搜索
            QueryStringQueryBuilder queryStrBuilder = QueryBuilders.queryString(query);
            // 是否允许开头为通配符,是否分析通配符
            queryStrBuilder.analyzeWildcard(true).allowLeadingWildcard(true).field(ES_FIELD_CONTENT).field(ES_FIELD_CONTENT_PY)
                    .defaultOperator(org.elasticsearch.index.query.QueryStringQueryBuilder.Operator.OR);
            SearchSourceBuilder ssb = searchSourceBuilder.query(queryStrBuilder);
            // 设置分页
            ssb.from((pageNow-1)*pageSize).size(pageSize);
//            TermsBuilder termsBuilder = new TermsBuilder("groupById");
            // 设置聚合
//            termsBuilder.field(ES_FIELD);
//            ssb.aggregation(termsBuilder);
            // 设置高亮
            HighlightBuilder highlight = SearchSourceBuilder.highlight();
            highlight.preTags("<b>").postTags("</b>").field(ES_FIELD_CONTENT);
            ssb.highlight(highlight);
            // 设置排序方式
            ssb.sort("_score", SortOrder.DESC);

            if (logger.isDebugEnabled()) {
                logger.debug("requestParams:" + ssb.toString());
            }
            Builder builder = new Search.Builder(ssb.toString());
            // 构建搜索
            Search search = builder.addIndex(indexNames).addType(indexTypes).allowNoIndices(true).ignoreUnavailable(true).setSearchType(SearchType.QUERY_THEN_FETCH).build();
            SearchResult result = client.execute(search);
            System.out.println("记录数：---------------------------" + result.getTotal());
            logger.debug("search result response: " + result.getJsonString());
            List<ESDto> esDtos = new ArrayList<ESDto>();
            if (null != result && result.isSucceeded()) {
                List<Hit<ESDto, Void>> hits = result.getHits(ESDto.class);
                for (Hit<ESDto, Void> hit : hits) {
                    ESDto source = hit.source;
                    Map<String, List<String>> hl = hit.highlight;
                    if (null != hl) {
                        List<String> list = hl.get(ES_FIELD_CONTENT);
                        source.setDescription(list.get(0));
                    }
                    esDtos.add(source);
                }
                logger.debug(LogUtils.format("esDtos", esDtos));
            } else {
                logger.debug("查询失败 :" + result.getErrorMessage());
            }
            // 去重
//            List<ESDto> newList = CollectionUtils.removeDuplicateWithOrder(esDtos);
            return esDtos;
        } catch (IOException e) {
            logger.debug("查询失败 " + LogUtils.format("query", query, "indexNames", indexNames, "indexTypes", indexTypes), e);
            throw new IOException("查询失败 ", e);
        }
    }*/
    
    /**
     * 构建单个词条
     * @param field
     * @param value
     * @return
     */
    protected TermQueryBuilder termBuilder(String field, String value) {
         //词条查询
       return QueryBuilders.termQuery(field, value);
    }
    /**
     * 范围构建
     * @param field
     * @param from
     * @return
     */
    protected RangeQueryBuilder rangeBuilderFrom(String field, String value) {
        //范围查询
        return QueryBuilders.rangeQuery(field).gte(value);
    }
    /**
     * 范围构建
     * @param field
     * @param value
     * @return
     */
    protected RangeQueryBuilder rangeBuilderTo(String field, String value) {
        //范围查询
        return QueryBuilders.rangeQuery(field).lte(value);
    }
    /**
     * like
     * @param field
     * @param value
     * @return
     */
    protected MoreLikeThisQueryBuilder likeBuilder(String field, String value){
        //like查询
        return QueryBuilders.moreLikeThisQuery(field).likeText(value).minDocFreq(1).minTermFreq(1);
    }
    
    /**
     * fuzzy_like_this
     * @param field
     * @param value
     * @return
     */
    protected FuzzyLikeThisFieldQueryBuilder fuzzyLikeBuilder(String field, String value){
        //fuzzy_like_this查询
        return QueryBuilders.fuzzyLikeThisFieldQuery(field).likeText(value).fuzziness(Fuzziness.fromSimilarity(0.0f));
    }
    
    /**
     * fuzzy
     * @param field
     * @param value
     * @return
     */
    protected FuzzyQueryBuilder fuzzyBuilder(String field, String value){
        //fuzzy查询
        return QueryBuilders.fuzzyQuery(field, value);
    }
    /**
     * match
     * @param field
     * @param value
     * @return
     */
    protected MatchQueryBuilder matchBuilder(String field, String value){
        //match查询
        return QueryBuilders.matchQuery(field, value).fuzziness(0.5);
    }
    /**
     * matchAll
     * @param field
     * @param value
     * @return
     */
    protected MatchAllQueryBuilder matchAllBuilder(){
        //matchAll查询
        return QueryBuilders.matchAllQuery();
    }
    /**
     * 多个词条
     * @param field
     * @param values
     * @return
     */
    protected TermsQueryBuilder termsBuilder(String field, List<String> values){
        //多个词条查询
        return QueryBuilders.termsQuery(field, values);
    }
    
    /**
     * 字符串查询
     * @param value
     * @param fields
     * @return
     */
    protected QueryStringQueryBuilder queryStringBuilder(String value, List<String> fields){
        QueryStringQueryBuilder queryStrBuilder = QueryBuilders.queryString(value);
        setQueryBuilderField(fields, queryStrBuilder);
        return queryStrBuilder.analyzeWildcard(true).allowLeadingWildcard(true);
    }

    private void setQueryBuilderField(List<String> fields, QueryStringQueryBuilder queryStrBuilder) {
        if(null != fields && !fields.isEmpty()){
            for (String field : fields) {
                queryStrBuilder.field(field);
            }
        }
    }
    
    /**
     * 正则表达式
     * @param field
     * @param value
     * @return
     */
    protected RegexpQueryBuilder regexpBuilder(String field, String value){
        //正则表达式查询
        return QueryBuilders.regexpQuery(field, "+" + value +"");
    }
    
    /**
     * 通配符查询
     * @param field
     * @param value
     * @return
     */
    protected WildcardQueryBuilder wildcardBuilder(String field, String value){
        //通配符查询
        return QueryBuilders.wildcardQuery(field, "*" + value + "*");
    }
    
    /**
     * 前缀查询
     * @param field
     * @param value
     * @return
     */
    protected PrefixQueryBuilder prefixBuilder(String field, String value){
        //前缀查询
        return QueryBuilders.prefixQuery(field, value);
    }
    
    /**
     * 根据id删除指定的文档
     * 
     * @param indexName
     *            索引名称
     * @param indexType
     *            索引类型
     * @param docId
     *            文档id
     * @return boolean
     * @throws IOException 
     */
    @Override
    public boolean deleteDoc(String indexName, String indexType, String docId) throws IOException {
        JestResult result = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("indexName:" + indexName + " indexType:" + indexType + " docId:" + docId);
            }
            Delete d = new Delete.Builder(docId).index(indexName).type(indexType).build();
            result = client.execute(d);
            if (null != result && result.isSucceeded()) {
                return true;
            }
            return false;
        } catch (IOException e) {
            logger.error("删除索引失败", e);
            throw new IOException("删除索引失败", e);
        }
    }
	
    /**
     * 创建索引
     * 
     * @param indexName
     *            索引名称
     * @return boolean
     * @throws IOException
     */
    public boolean addIndex(String indexName) throws IOException{
        JestResult result = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("indexName:" + indexName);
            }
            org.elasticsearch.common.settings.ImmutableSettings.Builder buildIndexSetting = this.buildIndexSetting();
            io.searchbox.indices.CreateIndex.Builder bIndex = new CreateIndex.Builder(indexName);
            CreateIndex cIndex = bIndex.settings(buildIndexSetting.build().getAsMap()).build();
            result = client.execute(cIndex);
            if (null != result && result.isSucceeded()) {
                return true;
            }
            return false;
        } catch (IOException e) {
            logger.error("创建索引失败", e);
            throw new IOException("创建索引失败", e);
        }
    }
	
    /**
     * "analysis": { "analyzer": { "pinyin_analyzer": { "filter": [ "standard",
     * "nGram" ], "tokenizer": [ "my_pinyin" ] } }, "tokenizer": { "my_pinyin":
     * { "padding_char": "", "type": "pinyin", "first_letter": "only" } } }
     * 构建索引设置
     * 
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public ImmutableSettings.Builder buildIndexSetting() throws IOException {
        try {
            ImmutableSettings.Builder settingsBuilder = customImmutableSetting.getBuilder();
            XContentBuilder settings = XContentFactory.jsonBuilder().startObject().startObject("analysis").startObject("analyzer")
                    .startObject("pinyin_analyzer").startArray("filter").value("standard").value("nGram").endArray().startArray("tokenizer")
                    .value("my_pinyin").endArray().endObject().endObject().startObject("tokenizer").startObject("my_pinyin").field("type", "pinyin")
                    .field("first_letter", "none").field("padding_char", " ").endObject().endObject().endObject();
            settingsBuilder.put(JsonUtils.jsonToObject(settings.string(), Map.class));
            logger.debug(LogUtils.format("settings", settings.string()));
            return settingsBuilder;
        } catch (IOException e) {
            logger.debug("构建索引配置失败", e);
            throw new IOException("构建索引配置失败");
        }
    }
	
    /**
     * 删除索引
     * 
     * @param indexName
     *            索引名称
     * @return boolean
     * @throws IOException
     */
    @Override
    public boolean deleteIndex(String indexName, String indexType) throws IOException {
        JestResult result = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("indexName:" + indexName);
            }
            io.searchbox.indices.DeleteIndex.Builder builder = new DeleteIndex.Builder(indexName);
            if(!StringUtils.isEmpty(indexType)){
                builder.type(indexType);
            }
            DeleteIndex dIndex = builder.build();
            result = client.execute(dIndex);
            if (null != result && result.isSucceeded()) {
                return true;
            }
            return false;
        } catch (IOException e) {
            logger.error("删除索引失败", e);
            throw new IOException("删除索引失败", e);
        }
    }
    
    public boolean deleteIndexType(String indexName, String indexType) throws IOException {
        JestResult result = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("indexName:" + indexName);
            }
            DeleteIndex dIndex = new DeleteIndex.Builder(indexName).type(indexType).build();
            result = client.execute(dIndex);
            if (null != result && result.isSucceeded()) {
                return true;
            }
            return false;
        } catch (IOException e) {
            logger.debug("删除索引类型失败", e);
            throw new IOException("删除索引类型失败", e);
        }
    }
}
