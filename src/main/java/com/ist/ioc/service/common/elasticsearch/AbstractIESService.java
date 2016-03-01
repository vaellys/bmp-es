package com.ist.ioc.service.common.elasticsearch;

import static com.ist.ioc.service.common.Constants.ES_ADD_ACTION;
import static com.ist.ioc.service.common.Constants.ES_BEGIN_TAG;
import static com.ist.ioc.service.common.Constants.ES_DELETE_ACTION;
import static com.ist.ioc.service.common.Constants.ES_END_TAG;
import static com.ist.ioc.service.common.Constants.ES_FIELD_CONTENT;
import static com.ist.ioc.service.common.Constants.ES_FIELD_DESCRIPTION;
import static com.ist.ioc.service.common.Constants.ES_FIELD_TITLE;
import static com.ist.ioc.service.common.Constants.ES_INDEX_CREATE_TIME;
import static com.ist.ioc.service.common.Constants.ES_PAGE_ID;
import static com.ist.ioc.service.common.Constants.ES_PAGE_SIZE;
import static com.ist.ioc.service.common.Constants.ES_RESULT;
import static com.ist.ioc.service.common.Constants.ES_RESULT_KEY;
import static com.ist.ioc.service.common.Constants.ES_SCORE;
import static com.ist.ioc.service.common.Constants.ES_TOTAL_PAGE;
import static com.ist.ioc.service.common.Constants.ES_TOTAL_SIZE;
import static com.ist.ioc.service.common.Constants.ES_UPDATE_ACTION;
import static com.ist.ioc.service.common.Constants.ES_UPDATE_DOC;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyLikeThisFieldQueryBuilder;
import org.elasticsearch.index.query.FuzzyLikeThisQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.RegexpQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.ist.assemble.CustomImmutableSetting;
import com.ist.common.es.util.DateUtils;
import com.ist.common.es.util.LogUtils;
import com.ist.common.es.util.PinYin4jUtils;
import com.ist.common.es.util.SimilarityUtils;
import com.ist.common.es.util.page.Pagination;
import com.ist.dto.bmp.ESDto;
import com.ist.dto.bmp.QueryParamsDto;

/**
 * 提供创建映射，索引，搜索等服务
 * 
 * @author qianguobing
 */
public abstract class AbstractIESService implements IESService {

    private static final Log logger = LogFactory.getLog(AbstractIESService.class);
    /**
     *总记录数
     */
    private static final int ES_TOTAL_COUNT = 10000;
    @Resource
    public JestClient client;
    @Resource
    private CustomImmutableSetting customImmutableSetting = null;
    @Autowired
    SearchSourceBuilder searchSourceBuilder = null;
    @Resource
    private GenericObjectPool<JestClient> pool;

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
                    if (null != typeList && !typeList.isEmpty()) {
                        for (String type : typeList) {
                            Bulk.Builder builder = new Bulk.Builder().defaultIndex(index);
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
//            this.deleteIndex(indexName, null);
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

    public boolean documentHandlerWithDto(String indexName, String indexType, List<ESDto> documents, Integer action) throws IOException {
        try {
            // es库名只能是小写字母
            indexName = StringUtils.lowerCase(indexName);
            // 删除索引
            this.deleteIndex(indexName, null);
            // 创建索引
            this.addIndex(indexName);
            // 设置默认的索引名字
            Bulk.Builder builder = new Bulk.Builder().defaultIndex(indexName);
            // 创建文档
            if (action == ES_ADD_ACTION && !documents.isEmpty()) {
                // 创建索引映射
                createIndexMapping(indexName, indexType);
                // 若文档为空则只创建索引类型
                builder.addAction(Arrays.asList(buildIndexDtoAction(documents)));
                // 删除文档
            } else if (action == ES_DELETE_ACTION && !documents.isEmpty()) {
                builder.addAction(Arrays.asList(buildDeleteDtoAction(documents)));
            } else if (action == ES_UPDATE_ACTION && !documents.isEmpty()) {
                builder.addAction(Arrays.asList(buildUpdateDtoAction(documents)));
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
            // 获取文件上传的目录
            // String dir = sysConfigEs.getProperty("data.import");
            // String url = dir + bean.getPath();
            // String description = bean.getDescription();
            // if(null == description){
            // String text = ParseDocumentUtil.getText(url);
            // String fileName = FileUtils.getFileNameByUrl(url);
            // bean.setDescription(text);
            // bean.setName(fileName);
            // }
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
            Object id = map.get(ES_RESULT_KEY);
            if(null != id && !StringUtils.isBlank((String.valueOf(id)))){
                indexs[i] = new Index.Builder(map).id(String.valueOf(id)).build();
            }else{
                indexs[i] = new Index.Builder(map).build();
            }
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
            logger.debug("params: " + LogUtils.format("index", index, "type", type));
            // String mappingJsonStr = buildMappingJsonStr(index, type);
            // String mappingJsonStr = buildMappingJsonStr(type);
            String mappingJsonStr = customImmutableSetting.getMappingStr();
            PutMapping putMapping = new PutMapping.Builder(index, type, mappingJsonStr).build();
            client.execute(putMapping);
        } catch (IOException e) {
            logger.debug("创建索引映射失败 : " + LogUtils.format("index", index, "type", type), e);
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
   /* protected String buildMappingJsonStr(String index, String type) {
        try {
            // 创建ik分词器
            Analyzer ik = new IKAnalyzer();
            Settings settings = customImmutableSetting.getBuilder().build();
            Analyzer py = new PinyinAnalyzer(settings);
            Analyzer en = new EnglishAnalyzer(Version.LUCENE_47);
            STConvertAnalyzer st = new STConvertAnalyzer(settings);
            // StandardAnalyzer sa = new StandardAnalyzer(Version.LUCENE_47);

            NamedAnalyzer naIk = new NamedAnalyzer(ES_IK, ik);
            NamedAnalyzer naPy = new NamedAnalyzer(ES_PINYIN_ANALYZER, py);
            NamedAnalyzer naEn = new NamedAnalyzer(ES_ENGLISH, en);
            // NamedAnalyzer naSt = new NamedAnalyzer(ES_S2T_CONVERT, st);
            NamedAnalyzer naSt = new NamedAnalyzer(ES_T2S_CONVERT, st);
            // 创建拼音分词器
            RootObjectMapper.Builder rootObjectMapperBuilder = new RootObjectMapper.Builder(type);
            org.elasticsearch.index.mapper.core.StringFieldMapper.Builder titleBuilder = getStringFieldBuilder(ES_FIELD_TITLE, naIk).store(true)
                    .index(true);
            org.elasticsearch.index.mapper.core.StringFieldMapper.Builder nameBuilder = getStringFieldBuilder(ES_FIELD_NAME, naSt).store(true).index(
                    true);
            org.elasticsearch.index.mapper.core.StringFieldMapper.Builder descBuilder = getStringFieldBuilder(ES_FIELD_DESCRIPTION, naIk).store(true)
                    .index(true);
            org.elasticsearch.index.mapper.core.StringFieldMapper.Builder pathBuilder = getStringFieldBuilder(ES_FIELD_PATH, naEn).store(true).index(
                    true);
            // 增加拼音字段子级
            org.elasticsearch.index.mapper.core.StringFieldMapper.Builder pinyinBuilder = getStringFieldBuilder(ES_FIELD_PY, naPy).store(true).index(
                    true);
            // 增加英文字段
            org.elasticsearch.index.mapper.core.StringFieldMapper.Builder enBuilder = getStringFieldBuilder(ES_FIELD_EN, naEn).store(true)
                    .index(true);
            org.elasticsearch.index.mapper.core.StringFieldMapper.Builder contentBuilder = getStringFieldBuilder(ES_FIELD_CONTENT, naIk).store(false)
                    .index(true).addMultiField(pinyinBuilder).addMultiField(enBuilder);
            rootObjectMapperBuilder.add(contentBuilder).add(titleBuilder).add(descBuilder).add(nameBuilder).add(pathBuilder);
            DocumentMapper documentMapper = new DocumentMapper.Builder(index, null, rootObjectMapperBuilder).build(null);
            String expectedMappingSource = documentMapper.mappingSource().toString();
            return expectedMappingSource;
        } catch (Exception e) {
            logger.debug("构建映射字符串失败 " + LogUtils.format("type", type), e);
            throw new RuntimeException("构建映射字符串失败", e);
        }
    }

    private org.elasticsearch.index.mapper.core.StringFieldMapper.Builder getStringFieldBuilder(String field, NamedAnalyzer na) {
        org.elasticsearch.index.mapper.core.StringFieldMapper.Builder fieldBuilder = new StringFieldMapper.Builder(field).indexAnalyzer(na)
                .searchAnalyzer(na);
        return fieldBuilder;
    }*/

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
    protected String buildMappingJsonStr(String type) throws Exception {
        try {
            XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();
            XContentBuilder content = jsonBuilder.startObject()
                .startObject(type)
                    .startObject("properties")
                    
                        .startObject("name")
                            .field("type", "string")
                            .field("store", "yes")
                            .field("index", "not_analyzed")
                        .endObject()
                        
                        .startObject("description")
                            .field("type", "multi_field")
                            .field("index", "analyzed")
                            .startObject("fields")
                                .startObject("py")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("index", "analyzed")
                                    .field("indexAnalyzer", "pinyin_analyzer")
                                    .field("searchAnalyzer", "pinyin_analyzer")
                                .endObject()
                                .startObject("description")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("index", "analyzed")
                                    .field("indexAnalyzer", "ik")
                                    .field("searchAnalyzer", "ik")
                                .endObject()
                            .endObject()
                        .endObject()
                        
                        .startObject("content")
                            .field("type", "multi_field")
                            .field("index", "analyzed")
                            .startObject("fields")
                                .startObject("py")
                                    .field("type", "string")
                                    .field("store", "no")
                                    .field("index", "analyzed")
                                    .field("indexAnalyzer", "pinyin_analyzer")
                                    .field("searchAnalyzer", "pinyin_analyzer")
                                .endObject()
                                .startObject("en")
                                    .field("type", "string")
                                    .field("store", "no")
                                    .field("index", "analyzed")
                                    .field("indexAnalyzer", "english")
                                    .field("searchAnalyzer", "english")
                                .endObject()
                                .startObject("content")
                                    .field("type", "string")
                                    .field("store", "no")
                                    .field("index", "analyzed")
                                    .field("indexAnalyzer", "ik")
                                    .field("searchAnalyzer", "ik")
                                .endObject()
                                .startObject("t2s")
                                    .field("type", "string")
                                    .field("store", "no")
                                    .field("index", "analyzed")
                                    .field("indexAnalyzer", "mytsconvert")
                                    .field("searchAnalyzer", "mytsconvert")
                                .endObject()
                                 .startObject("russian")
                                    .field("type", "string")
                                    .field("store", "no")
                                    .field("index", "analyzed")
                                    .field("indexAnalyzer", "russian")
                                    .field("searchAnalyzer", "russian")
                                .endObject()
                            .endObject()
                        .endObject()
                        
                        .startObject("path")
                            .field("type", "string")
                            .field("store", "yes")
                            .field("index", "not_analyzed")
                        .endObject()
                        
                         .startObject("title")
                            .field("type", "multi_field")
                            .field("index", "analyzed")
                            .startObject("fields")
                                .startObject("py_only")
                                    .field("type", "string")
                                    .field("store", "no")
                                    .field("index", "analyzed")
                                    .field("indexAnalyzer", "pinyin_analyzer")
                                    .field("searchAnalyzer", "pinyin_analyzer")
                                .endObject()
                                .startObject("py_none")
                                    .field("type", "string")
                                    .field("store", "no")
                                    .field("index", "analyzed")
                                    .field("indexAnalyzer", "user_name_analyzer1")
                                    .field("searchAnalyzer", "user_name_analyzer1")
                                .endObject()
                                .startObject("title")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("index", "analyzed")
                                    .field("indexAnalyzer", "ik")
                                    .field("searchAnalyzer", "ik")
                                .endObject()
                                .startObject("t2s")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("index", "analyzed")
                                    .field("indexAnalyzer", "mytsconvert")
                                    .field("searchAnalyzer", "mytsconvert")
                                .endObject()
                                .startObject("russian")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("index", "analyzed")
                                    .field("indexAnalyzer", "russian")
                                    .field("searchAnalyzer", "russian")
                                .endObject()
                                .startObject("french")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("index", "analyzed")
                                    .field("indexAnalyzer", "polish")
                                    .field("searchAnalyzer", "polish")
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject()
            .endObject();
            
            
            
            
         /*   XContentBuilder content = XContentFactory.jsonBuilder();
                      .startObject()
                    // 索引库名（类似数据库中的表）
                    
                    
                    
                    
                    
                    
                    
                    .startObject(type).startObject("_timestamp").field("enabled", true).field("store", "yes").field("path", ES_CREATETIME)
                    .field("format", "yyyy-MM-dd HH:mm:ss").endObject().startObject("properties").startObject("id").field("type", "string")
                    .endObject().startObject("title").field("type", "string").endObject().startObject(ES_PATH).field("type", "string").endObject()
                    .startObject(ES_CREATETIME).field("type", "date").field("store", "yes").field("format", "yyyy-MM-dd HH:mm:ss").endObject()
                    .startObject(ES_DESCRIPTION).field("type", "multi_field").field("index", "analyzed").startObject("fields")
                    .startObject(ES_DESCRIPTION).field("type", "string").field("store", "yes").field("index", "analyzed")
                    .field("indexAnalyzer", "ik").field("searchAnalyzer", "ik").field("term_vector", "with_positions_offsets")
                    .field("boost", 4.0)
                    // 打分(默认1.0)
                    .endObject().startObject("py").field("type", "string").field("store", "yes").field("index", "analyzed")
                    .field("indexAnalyzer", "pinyin_analyzer").field("searchAnalyzer", "pinyin_analyzer")
                    .field("term_vector", "with_positions_offsets").field("include_in_all", "false").field("boost", 4.0) // 打分(默认1.0)
                    .endObject().endObject().endObject().endObject().endObject().endObject();*/
            logger.debug(LogUtils.format("----------------映射字符串---------------", content.string()));
            return content.string();
        } catch (Exception e) {
            logger.debug("构建映射字符串失败 " + LogUtils.format("type", type), e);
            throw new Exception("构建映射字符串失败", e);
        }
    }

    /**
     * 根据字段进行搜索，支持多个索引类型
     * 
     * @param indexNames
     *            索引名称列表
     * @param indexTypes
     *            索引类型列表
     * @param queryQuery
     *            搜索条件
     * @param isHighlight
     *            是否高亮
     * @param hlFields
     *            高亮字段
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Map<String, Object> 查询结果集，带分页参数
     * @throws IOException
     */
    protected Map<String, Object> documentSearch(List<String> indexNames, List<String> indexTypes, QueryBuilder queryQuery, Boolean isHighlight,
            List<String> hlFields, Integer pageNow, Integer pageSize) throws IOException {
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
     * 
     * @param indexName
     *            索引名称
     * @param indexType
     *            索引类型
     * @param queryQuery
     *            搜索条件
     * @param isHighlight
     *            是否高亮
     * @param hlFields
     *            高亮字段
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Map<String, Object> 查询结果集，带分页参数
     * @throws IOException
     */
    protected Map<String, Object> documentSearch(String indexName, String indexType, QueryBuilder queryQuery, Boolean isHighlight,
            List<String> hlFields, Integer pageNow, Integer pageSize) throws IOException {
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
     * poc
     * @param indexName
     * @param indexType
     * @param queryQuery
     * @param isHighlight
     * @param hlFields
     * @param similarityFields
     * @param keywords
     * @param pageNow
     * @param pageSize
     * @return
     * @throws IOException
     */
    protected Map<String, Object> documentSearch(String indexName, String indexType, QueryBuilder queryQuery, Boolean isHighlight, List<String> hlFields,
            List<String> similarityFields, String keywords, Integer pageNow, Integer pageSize) throws IOException {
        try {
            Builder builder = getSearchBuilder(queryQuery, isHighlight, hlFields);
            // 构建搜索
            SearchResult result = searchResultExecute(indexName, indexType, builder);
            Map<String, Object> resultMap = searchResultHandler(result, similarityFields, keywords, pageNow, pageSize);
            logger.debug("--------------------------------结果--------------------------" + LogUtils.format("r", resultMap));
            return resultMap;
        } catch (IOException e) {
            logger.error("查询失败 ", e);
            throw new IOException("查询失败 ", e);
        }
    }

    protected List<Map<String, Object>> documentSearchWithList(String indexName, String indexType, QueryBuilder queryQuery, Boolean isHighlight,
            List<String> hlFields, int score, Integer pageNow, Integer pageSize) throws IOException {
        try {
            Builder builder = getSearchBuilder(queryQuery, isHighlight, hlFields, pageNow, pageSize);
            // 构建搜索
            SearchResult result = searchResultExecute(indexName, indexType, builder);
            List<Map<String, Object>> resultList = searchResultHandlerWithList(result, hlFields, score, pageNow, pageSize);
            logger.debug("--------------------------------结果--------------------------" + LogUtils.format("r", resultList));
            return resultList;
        } catch (IOException e) {
            logger.error("查询失败 ", e);
            throw new IOException("查询失败 ", e);
        }
    }
    
    protected Map<String, Object> documentSearch(String indexName, String indexType, QueryBuilder queryQuery, Boolean isHighlight, List<String> hlFields,
            List<String> similarityFields, Map<String, Object> mapFieldParams, Integer pageNow, Integer pageSize) throws IOException {
        try {
            Builder builder = getSearchBuilder(queryQuery, isHighlight, hlFields);
            // 构建搜索
            SearchResult result = searchResultExecute(indexName, indexType, builder);
            Map<String, Object> resultMap = searchResultHandler(result, mapFieldParams, pageNow, pageSize);
            logger.debug("--------------------------------结果--------------------------" + LogUtils.format("r", resultMap));
            return resultMap;
        } catch (IOException e) {
            logger.error("查询失败 ", e);
            throw new IOException("查询失败 ", e);
        }
    }
    
    protected Map<String, Object> documentSearch(String indexName, String indexType, QueryBuilder queryQuery, Boolean isHighlight, List<String> hlFields,
            List<String> similarityFields, Map<String, Object> mapFieldParams, Map<String, Object> mapWeightParams, Integer pageNow, Integer pageSize) throws IOException {
        try {
            Builder builder = getSearchBuilder(queryQuery, isHighlight, hlFields);
            // 构建搜索
            SearchResult result = searchResultExecute(indexName, indexType, builder);
            Map<String, Object> resultMap = searchResultHandler(result, mapFieldParams, mapWeightParams, pageNow, pageSize);
            logger.debug("--------------------------------结果--------------------------" + LogUtils.format("r", resultMap));
            return resultMap;
        } catch (IOException e) {
            logger.error("查询失败 ", e);
            throw new IOException("查询失败 ", e);
        }
    }

    /**
     * 根据字段进行搜索
     * 
     * @param indexName
     *            索引名称
     * @param indexType
     *            索引类型
     * @param queryQuery
     *            搜索条件
     * @param isHighlight
     *            是否高亮
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Map<String, Object> 查询结果集，带分页参数
     * @throws IOException
     */
    protected Pagination documentSearch(List<String> indexNames, List<String> indexTypes, QueryBuilder queryQuery, Boolean isHighlight,
            Integer pageNow, Integer pageSize) throws IOException {
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
     * 根据字段进行搜索
     * 
     * @param indexName
     *            索引名称
     * @param indexType
     *            索引类型
     * @param queryQuery
     *            搜索条件
     * @param isHighlight
     *            是否高亮
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Map<String, Object> 查询结果集，带分页参数
     * @throws IOException
     */
    protected Pagination documentSearch(List<String> indexNames, List<String> indexTypes, QueryBuilder queryQuery, Boolean isHighlight,
            String keywords, Integer pageNow, Integer pageSize) throws IOException {
        try {
            Builder builder = getSearchBuilder(queryQuery, isHighlight);
            // 构建搜索
            SearchResult result = searchResultExecute(indexNames, indexTypes, builder);
            Pagination p = searchResultHandler(result, keywords, pageNow, pageSize);
            logger.debug("--------------------------------结果--------------------------" + LogUtils.format("r", p));
            return p;
        } catch (IOException e) {
            logger.error("查询失败 ", e);
            throw new IOException("查询失败 ", e);
        }
    }
    
    /**
     * @param queryParams 查询参数实体
     * @return Pagination 分页实体
     * @throws IOException
     */
    protected Pagination documentSearch(QueryParamsDto queryParams) throws IOException {
        try {
            if (null != queryParams) {
                Builder builder = getSearchBuilder(queryParams.getQueryBuilder(), queryParams.getIsHighlight());
                // 构建搜索
                SearchResult result = searchResultExecute(queryParams.getIndexNames(), queryParams.getIndexTypes(), builder);
                Pagination p = searchResultHandler(result, queryParams.getKeywords(), queryParams.getPageNow(), queryParams.getPageSize());
                logger.debug("--------------------------------结果--------------------------" + LogUtils.format("r", p));
                return p;
            } else {
                return null;
            }
        } catch (IOException e) {
            logger.error("查询失败 ", e);
            throw new IOException("查询失败 ", e);
        }
    }

    /**
     * 返回查询结果
     * 
     * @param indexName
     *            索引名称
     * @param indexType
     *            索引类型
     * @param builder
     *            查询构建器
     * @return
     * @throws IOException
     */
    private SearchResult searchResultExecute(String indexName, String indexType, Builder builder) throws IOException {
        JestClient jestClient = null;
        try {
            jestClient = pool.borrowObject();
            Search search = builder.addIndex(indexName).addType(indexType).allowNoIndices(true).ignoreUnavailable(true).build();
            SearchResult result = jestClient.execute(search);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (jestClient != null) {
                    pool.returnObject(jestClient);
                }
                logger.debug("可用闲置对象: " + pool.getNumActive() + "   最大激活对象: " + pool.getMaxActive() + "  最小闲置对象: " + pool.getMinIdle());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 返回查询结果，支持多个索引类型
     * 
     * @param indexNames
     *            索引名称列表
     * @param indexTypes
     *            索引类型列表
     * @param builder
     *            查询构建器
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
     * 
     * @param queryQuery
     * @param isHighlight
     *            是否高亮
     * @param hlFields
     *            高亮字段
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Builder
     */
    protected Builder getSearchBuilder(QueryBuilder queryQuery, Boolean isHighlight, List<String> hlFields, Integer pageNow, Integer pageSize) {
        SearchSourceBuilder ssb = searchSourceBuilder.query(queryQuery);
        // 设置分页
        ssb.from(0).size(ES_TOTAL_COUNT);
        // 设置排序方式
        // ssb.sort("_score", SortOrder.DESC);
        if (isHighlight) {
            HighlightBuilder highlight = SearchSourceBuilder.highlight().requireFieldMatch(true).noMatchSize(10).field("content.en", 100, 1, 10);
            HighlightBuilder hlb = highlight.preTags(ES_BEGIN_TAG).postTags(ES_END_TAG);
            if (null != hlFields) {
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
    
    protected Builder getSearchBuilder(QueryBuilder queryQuery, Boolean isHighlight, List<String> hlFields) {
        SearchSourceBuilder ssb = searchSourceBuilder.query(queryQuery);
        // 设置分页
        ssb.from(0).size(ES_TOTAL_COUNT);
        // 设置排序方式
        // ssb.sort("_score", SortOrder.DESC);
        if (isHighlight) {
            HighlightBuilder highlight = SearchSourceBuilder.highlight().requireFieldMatch(true);
            HighlightBuilder hlb = highlight.preTags(ES_BEGIN_TAG).postTags(ES_END_TAG);
            if (null != hlFields) {
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
     * 
     * @param queryQuery
     * @param isHighlight
     *            是否高亮
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Builder
     */
    protected Builder getSearchBuilder(QueryBuilder queryQuery, Boolean isHighlight, Integer pageNow, Integer pageSize) {
        SearchSourceBuilder ssb = searchSourceBuilder.query(queryQuery);
        // ssb.fields("description");
        // ssb.fields("content");
        // 设置分页
        ssb.from(Pagination.getCurrentNum(pageNow, pageSize)).size(pageSize);
        // 设置排序方式
        // SuggestionBuilder<?> suggestion = new
        // TermSuggestionBuilder("suggestion").field("path").text("你好").analyzer("english");
        // ssb.suggest().addSuggestion(suggestion);
        if (isHighlight) {
            HighlightBuilder highlight = SearchSourceBuilder.highlight().requireFieldMatch(true);
            HighlightBuilder hlb = highlight.preTags("<font color='red'>").postTags("</font>");
            hlb.field(ES_FIELD_TITLE).field(ES_FIELD_DESCRIPTION).field(ES_FIELD_CONTENT).field("name").field("content.py").field("content.en")
                    .field("content.prototype");
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
     * 
     * @param queryQuery
     * @param isHighlight
     *            是否高亮
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Builder
     */
    protected Builder getSearchBuilder(QueryBuilder queryQuery, Boolean isHighlight) {
        SearchSourceBuilder ssb = searchSourceBuilder.query(queryQuery);
        // ssb.fields("description");
        // ssb.fields("content");
        // 设置分页
        ssb.from(0).size(ES_TOTAL_COUNT);
        // 设置排序方式
        // SuggestionBuilder<?> suggestion = new
        // TermSuggestionBuilder("suggestion").field("path").text("你好").analyzer("english");
        // ssb.suggest().addSuggestion(suggestion);
        if (isHighlight) {
            HighlightBuilder highlight = SearchSourceBuilder.highlight().requireFieldMatch(true);
            HighlightBuilder hlb = highlight.preTags("<font color='red'>").postTags("</font>");
            hlb.field(ES_FIELD_TITLE).field(ES_FIELD_DESCRIPTION).field(ES_FIELD_CONTENT).field("name").field("content.py").field("content.en")
                    .field("content.prototype");
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
     * 
     * @param result
     *            待处理的查询结果
     * @param fields
     *            查询字段
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Map<String, Object>
     */
    @SuppressWarnings({ "rawtypes" })
    private Map<String, Object> searchResultHandler(SearchResult result, List<String> hlFields, Integer pageNow, Integer pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Pagination p = null;
        Integer totalCount = 0;
        if (null != result && result.isSucceeded()) {
            // 构建分页对象
            p = getPagination(result, pageNow, pageSize);
            // 获取命中对象
            List<Hit<Map, Void>> hits = result.getHits(Map.class);
            for (Hit<Map, Void> hit : hits) {
                // 得到高亮标识的Map
                Map<String, Object> source = getHighlightMap(hlFields, hit);
                list.add(source);
            }
        }
        // 当前页
        map.put(ES_PAGE_ID, pageNow);
        // 页大小
        map.put(ES_PAGE_SIZE, pageSize);
        // 总页数
        map.put(ES_TOTAL_PAGE, null == p ? 1 : p.getTotalPage());
        // 总记录数
        map.put(ES_TOTAL_SIZE, totalCount);
        // 结果集
        map.put(ES_RESULT, list);
        return map;
    }

    @SuppressWarnings({ "rawtypes" })
    private List<Map<String, Object>> searchResultHandlerWithList(SearchResult result, List<String> hlFields, int score, Integer pageNow,
            Integer pageSize) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (null != result && result.isSucceeded()) {
            // 获取命中对象
            List<Hit<Map, Void>> hits = result.getHits(Map.class);
            for (Hit<Map, Void> hit : hits) {
                // 得到高亮标识的Map
                Map<String, Object> source = getHighlightMap(hlFields, hit);
                source.put(ES_SCORE, score);
                list.add(source);
            }
        }
        return list;
    }
    
    /**
     * 查询结果处理
     * 
     * @param result
     *            待处理的查询结果
     * @param fields
     *            查询字段
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Map<String, Object>
     */
    @SuppressWarnings({ "rawtypes" })
    private Map<String, Object> searchResultHandler(SearchResult result, List<String> similarityFields, String keywords, Integer pageNow, Integer pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Pagination p = null;
        if (null != result && result.isSucceeded()) {
            // 构建分页对象
//            p = getPagination(result, pageNow, pageSize);
            // 获取命中对象
            List<Hit<Map, Void>> hits = result.getHits(Map.class);
            for (Hit<Map, Void> hit : hits) {
                // 得到高亮标识的Map
                Map<String, Object> source = getHighlightMap(hit, similarityFields, keywords);
                list.add(source);
            }
        }
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                double s1 = (Double) o1.get("MATCHING_DEGREE");
                double s2 = (Double) o2.get("MATCHING_DEGREE");
                if (s2 > s1) {
                    return 1;
                } else if (s2 == s1) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        if(null != list && !list.isEmpty()){
            p = new Pagination(pageNow, pageSize, list, list.size());
        }else{
            p = new Pagination();
        }
        // 当前页
        map.put(ES_PAGE_ID, pageNow);
        // 页大小
        map.put(ES_PAGE_SIZE, pageSize);
        // 总页数
        map.put(ES_TOTAL_PAGE, null == p ? 1 : p.getTotalPage());
        // 总记录数
        map.put(ES_TOTAL_SIZE, list.size());
        // 结果集
        map.put(ES_RESULT, p.getList());
        return map;
    }
    
    
    @SuppressWarnings({ "rawtypes" })
    private Map<String, Object> searchResultHandler(SearchResult result, Map<String, Object> mapFieldParams, Integer pageNow, Integer pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Pagination p = null;
        if (null != result && result.isSucceeded()) {
            // 构建分页对象
//            p = getPagination(result, pageNow, pageSize);
            // 获取命中对象
            List<Hit<Map, Void>> hits = result.getHits(Map.class);
            for (Hit<Map, Void> hit : hits) {
                // 得到高亮标识的Map
                Map<String, Object> source = getHighlightMap(hit, mapFieldParams);
                list.add(source);
            }
        }
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                double s1 = (Double) o1.get("MATCHING_DEGREE");
                double s2 = (Double) o2.get("MATCHING_DEGREE");
                if (s2 > s1) {
                    return 1;
                } else if (s2 == s1) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        if(null != list && !list.isEmpty()){
            p = new Pagination(pageNow, pageSize, list, list.size());
        }else{
            p = new Pagination();
        }
        // 当前页
        map.put(ES_PAGE_ID, pageNow);
        // 页大小
        map.put(ES_PAGE_SIZE, pageSize);
        // 总页数
        map.put(ES_TOTAL_PAGE, null == p ? 1 : p.getTotalPage());
        // 总记录数
        map.put(ES_TOTAL_SIZE, list.size());
        // 结果集
        map.put(ES_RESULT, p.getList());
        return map;
    }
    
    @SuppressWarnings({ "rawtypes" })
    private Map<String, Object> searchResultHandler(SearchResult result, Map<String, Object> mapFieldParams, Map<String, Object> mapWeightParams, Integer pageNow, Integer pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Pagination p = null;
        if (null != result && result.isSucceeded()) {
            // 构建分页对象
//            p = getPagination(result, pageNow, pageSize);
            // 获取命中对象
            List<Hit<Map, Void>> hits = result.getHits(Map.class);
            for (Hit<Map, Void> hit : hits) {
                // 得到高亮标识的Map
                Map<String, Object> source = getHighlightMap(hit, mapFieldParams, mapWeightParams);
                list.add(source);
            }
        }
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                double s1 = (Double) o1.get("MATCHING_DEGREE");
                double s2 = (Double) o2.get("MATCHING_DEGREE");
                if (s2 > s1) {
                    return 1;
                } else if (s2 == s1) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        if(null != list && !list.isEmpty()){
            p = new Pagination(pageNow, pageSize, list, list.size());
        }else{
            p = new Pagination();
        }
        // 当前页
        map.put(ES_PAGE_ID, pageNow);
        // 页大小
        map.put(ES_PAGE_SIZE, pageSize);
        // 总页数
        map.put(ES_TOTAL_PAGE, null == p ? 1 : p.getTotalPage());
        // 总记录数
        map.put(ES_TOTAL_SIZE, list.size());
        // 结果集
        map.put(ES_RESULT, p.getList());
        return map;
    }

    /**
     * 查询结果处理
     * 
     * @param result
     *            待处理的查询结果
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Pagination 分页对象
     */
    protected Pagination searchResultHandler(SearchResult result, Integer pageNow, Integer pageSize) {
        Pagination p = null;
        List<ESDto> list = new ArrayList<ESDto>();
        if (null != result && result.isSucceeded()) {
            p = getPagination(result, pageNow, pageSize);
            List<Hit<ESDto, Void>> hits = result.getHits(ESDto.class);
            for (Hit<ESDto, Void> hit : hits) {
                ESDto source = hit.source;
                Map<String, List<String>> hl = hit.highlight;
                if (null != hl) {
                    List<String> titles = hl.get(ES_FIELD_TITLE);
                    List<String> des = hl.get(ES_FIELD_DESCRIPTION);
                    List<String> content = hl.get(ES_FIELD_CONTENT);
                    List<String> contentPy = hl.get("content.py");
                    List<String> contentEn = hl.get("content.en");
                    List<String> contentPrototype = hl.get("content.prototype");
                    List<String> name = hl.get("name");
                    if (null != titles) {
                        source.setTitle(titles.get(0));
                    }
                    if (null != des) {
                        source.setDescription(des.get(0));
                    }
                    if (null != content) {
                        source.setContent(content.get(0));
                        logger.debug("content: " + content.get(0));
                    } else {
                        if (null != contentEn) {
                            source.setContent(contentEn.get(0));
                            logger.debug("contentEn: " + contentEn.get(0));
                        }
                    }
                    if (null != name) {
                        source.setName(name.get(0));
                    }
                    if (null != contentPrototype) {
                        source.setContent(contentPrototype.get(0));
                        logger.debug("contentPrototype: " + contentPrototype.get(0));
                    }
                    if (null != contentPy) {
                        source.setContent(contentPy.get(0));
                        logger.debug("contentPy: " + contentPy.get(0));
                    }
                }
                list.add(source);
            }
            p.setList(list);
        }
        return p;
    }

    /**
     * 查询结果处理
     * 
     * @param result
     *            待处理的查询结果
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Pagination 分页对象
     */
    protected Pagination searchResultHandler(SearchResult result, String keywords, Integer pageNow, Integer pageSize) {
        Pagination p = null;
        List<ESDto> list = new ArrayList<ESDto>();
        if (null != result && result.isSucceeded()) {
//            p = getPagination(result, pageNow, pageSize);
            List<Hit<ESDto, Void>> hits = result.getHits(ESDto.class);
            for (Hit<ESDto, Void> hit : hits) {
                ESDto source = hit.source;
                String c = source.getContent();
                Map<String, List<String>> hl = hit.highlight;
                if (null != hl) {
                    List<String> titles = hl.get(ES_FIELD_TITLE);
                    List<String> des = hl.get(ES_FIELD_DESCRIPTION);
                    List<String> content = hl.get(ES_FIELD_CONTENT);
                    List<String> contentPy = hl.get("content.py");
                    List<String> contentEn = hl.get("content.en");
                    List<String> contentPrototype = hl.get("content.prototype");
                    List<String> name = hl.get("name");
                    //判断是否为中文
                    if (null != content) {
                        setSimilarity(keywords, source, c);
                        source.setContent(content.get(0));
                        logger.debug("content: " + content.get(0));
                    }
                    if (null != contentPy) {
                        String chinieseToPinyin = PinYin4jUtils.chinieseToPinyin(c);
                        setSimilarity(keywords, source, chinieseToPinyin);
                        source.setContent(contentPy.get(0));
                        logger.debug("contentPy: " + contentPy.get(0));
                    } 
                    if (null != contentEn) {
                        setSimilarity(keywords, source, c);
                        source.setContent(contentEn.get(0));
                        logger.debug("contentEn: " + contentEn.get(0));
                    } 
                    if (null != contentPrototype) {
                        setSimilarity(keywords, source, c);
                        source.setContent(contentPrototype.get(0));
                        logger.debug("contentPrototype: " + contentPrototype.get(0));
                    }
                    if (null != titles) {
                        source.setTitle(titles.get(0));
                    }
                    if (null != des) {
                        source.setDescription(des.get(0));
                    }
                    if (null != name) {
                        source.setName(name.get(0));
                    }
                }
                list.add(source);
            }
            Collections.sort(list, new Comparator<ESDto>() {
                @Override
                public int compare(ESDto o1, ESDto o2) {
                    double s1 = o1.getSimilarity();
                    double s2 = o2.getSimilarity();
                    if (s2 > s1) {
                        return 1;
                    } else if (s2 == s1) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
            if(null != list && !list.isEmpty()){
                p = new Pagination(pageNow, pageSize, list, list.size());
            }else{
                p = new Pagination();
            }
        }
        return p;
    }

    private void setSimilarity(String keywords, Map<String, Object> source, String c) {
        double similarity = SimilarityUtils.similarity(keywords, c);
        source.put("MATCHING_DEGREE", similarity);
    }
    
    private double getSimilarity(String keywords, String c) {
        return  SimilarityUtils.similarity(keywords, c);
    }
    
    private void setSimilarity(String keywords, ESDto source, String c) {
        double similarity = SimilarityUtils.similarity(keywords, c);
        source.setSimilarity(similarity);
    }

    /**
     * 查询结果处理
     * 
     * @param result
     *            待处理的查询结果
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Pagination 分页对象
     */
    protected Pagination searchResultHandlerWithField(SearchResult result, Integer pageNow, Integer pageSize) {
        logger.debug("result: " + result.getJsonString());
        Pagination p = null;
        // List<ESDto> list = new ArrayList<ESDto>();
        // jsonMap = result.getJsonMap();
        if (null != result && result.isSucceeded()) {
            p = getPagination(result, pageNow, pageSize);
            // JsonObject jsonObject = result.
            // JsonElement jsonElement = jsonObject.get("fields");
            /*
             * for (Hit<ESDto, Void> hit : hits) { ESDto source = hit.source;
             * Map<String, List<String>> hl = hit.highlight; if (null != hl) {
             * List<String> titles = hl.get(ES_FIELD_TITLE); List<String> des =
             * hl.get(ES_FIELD_DESCRIPTION); List<String> content =
             * hl.get(ES_FIELD_CONTENT); List<String> contentPy =
             * hl.get("content.py"); List<String> name = hl.get("name"); if(null
             * != titles){ source.setTitle(titles.get(0)); } if(null != des){
             * source.setDescription(des.get(0)); } if(null != content){
             * source.setContent(content.get(0)); } if(null != name){
             * source.setName(name.get(0)); }
             * 
             * } list.add(source);
             */
            // p.setList(list);
            // }
        }
        return p;
    }

    /**
     * 构建分页对象
     * 
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
     * 
     * @param fields
     *            高亮的字段
     * @param hit
     *            命中的对象
     * @return Map<String, Object>
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Map<String, Object> getHighlightMap(List<String> hlFields, Hit<Map, Void> hit) {
        Map<String, Object> source = hit.source;
        Map<String, List<String>> highlight = hit.highlight;
        if (null != highlight) {
            if (null != hlFields) {
                for (String field : hlFields) {
                    if (highlight.containsKey(field)) {
                        List<String> h = highlight.get(field);
                        source.put(field, h.get(0));
                    }
                }
            }
        }
        return source;
    }
    
    /**
     * 获取设置高亮之后的对象
     * 
     * @param fields
     *            高亮的字段
     * @param hit
     *            命中的对象
     * @return Map<String, Object>
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected Map<String, Object> getHighlightMap(Hit<Map, Void> hit, List<String> similarityFields, String keywords) {
        Map<String, Object> source = hit.source;
        //拼接原始字段值
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            if(similarityFields.contains(entry.getKey())){
                sb.append(entry.getValue());
            }
        }
        Map<String, List<String>> highlight = hit.highlight;
        if (null != highlight) {
            
//            List<String> names = highlight.get("NAME");
            List<String> nameEn = highlight.get("NAME.en");
            List<String> namePyNone = highlight.get("NAME.py_none");
            List<String> namePyOnly = highlight.get("NAME.py_only");
            List<String> nameT2S = highlight.get("NAME.t2s");
            List<String> nameRussian = highlight.get("NAME.russian");
            List<String> nameFrench = highlight.get("NAME.french");
            List<String> namePrototype = highlight.get("NAME.prototype");
            List<String> nameRaw = highlight.get("NAME.raw");
            if (null != nameRaw && nameRaw.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameRaw.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("NAME", nameRaw.get(0));
            }else if (null != namePyNone && namePyNone.get(0).indexOf(ES_BEGIN_TAG)> -1 && namePyNone.get(0).indexOf(ES_END_TAG) > -1) {
                String chinieseToPinyin = PinYin4jUtils.chinieseToPinyin(sb.toString());
                setSimilarity(keywords, source, chinieseToPinyin);
                source.put("NAME", namePyNone.get(0));
            }else if(null != namePyOnly && namePyOnly.get(0).indexOf(ES_BEGIN_TAG)> -1 && namePyOnly.get(0).indexOf(ES_END_TAG) > -1){
                String chinieseToPinyin = PinYin4jUtils.getPinYinHeadChar(sb.toString());
                setSimilarity(keywords, source, chinieseToPinyin);
                source.put("NAME", namePyOnly.get(0));
            }else if(null != nameEn && nameEn.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameEn.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("NAME", nameEn.get(0));
            }else if(null != namePrototype && namePrototype.get(0).indexOf(ES_BEGIN_TAG)> -1 && namePrototype.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("NAME", namePrototype.get(0));
            }else if (null != nameT2S && nameT2S.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameT2S.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("NAME", nameT2S.get(0));
            }else if (null != nameRussian && nameRussian.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameRussian.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("NAME", nameRussian.get(0));
            }else if (null != nameFrench && nameFrench.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameFrench.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("NAME", nameFrench.get(0));
            }
            
            List<String> country = highlight.get("COUNTRY");
            List<String> countryEn = highlight.get("COUNTRY.en");
            List<String> countryPyNone = highlight.get("COUNTRY.py_none");
            List<String> countryPyOnly = highlight.get("COUNTRY.py_only");
            List<String> countryT2S = highlight.get("COUNTRY.t2s");
            List<String> countryRussian = highlight.get("COUNTRY.russian");
            List<String> countryFrench = highlight.get("COUNTRY.french");
            List<String> countryPrototype = highlight.get("COUNTRY.prototype");
            List<String> countryRaw = highlight.get("COUNTRY.raw");
            if (null != country && country.get(0).indexOf(ES_BEGIN_TAG)> -1 && country.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("COUNTRY", country.get(0));
            }else if (null != countryPyNone && countryPyNone.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryPyNone.get(0).indexOf(ES_END_TAG) > -1) {
                String chinieseToPinyin = PinYin4jUtils.chinieseToPinyin(sb.toString());
                setSimilarity(keywords, source, chinieseToPinyin);
                source.put("COUNTRY", countryPyNone.get(0));
            }else if(null != countryPyOnly && countryPyOnly.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryPyOnly.get(0).indexOf(ES_END_TAG) > -1){
                String chinieseToPinyin = PinYin4jUtils.getPinYinHeadChar(sb.toString());
                setSimilarity(keywords, source, chinieseToPinyin);
                source.put("COUNTRY", countryPyOnly.get(0));
            }else if(null != countryEn && countryEn.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryEn.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("COUNTRY", countryEn.get(0));
            }else if(null != countryPrototype && countryPrototype.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryPrototype.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("COUNTRY", countryPrototype.get(0));
            }else if (null != countryT2S && countryT2S.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryT2S.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("COUNTRY", countryT2S.get(0));
            }else if (null != countryRussian && countryRussian.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryRussian.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("COUNTRY", countryRussian.get(0));
            }else if (null != countryFrench && countryFrench.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryFrench.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("COUNTRY", countryFrench.get(0));
            }else if (null != countryRaw && countryRaw.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryRaw.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("COUNTRY", countryRaw.get(0));
            }
        }
        
        List<String> nationalid = highlight.get("NATIONALID");
        if (null != nationalid && nationalid.get(0).indexOf(ES_BEGIN_TAG)> -1 && nationalid.get(0).indexOf(ES_END_TAG) > -1) {
            setSimilarity(keywords, source, sb.toString());
            source.put("NATIONALID", nationalid.get(0));
        }
        List<String> passportid = highlight.get("PASSPORTID");
        if (null != passportid && passportid.get(0).indexOf(ES_BEGIN_TAG)> -1 && passportid.get(0).indexOf(ES_END_TAG) > -1) {
            setSimilarity(keywords, source, sb.toString());
            source.put("PASSPORTID", passportid.get(0));
        }
        return source;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected Map<String, Object> getHighlightMap(Hit<Map, Void> hit, Map<String, Object> mapFieldParams) {
        Map<String, Object> source = hit.source;
        
      //拼接原始字段值
        StringBuilder sbSource = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : mapFieldParams.entrySet()) {
            sbSource.append(entry.getValue());
            for (Map.Entry<String, Object> entryTarget : source.entrySet()) {
                if(entry.getKey().equals(entryTarget.getKey())){
                    sb.append(entryTarget.getValue());
                    break;
                }
            }
        }
        String keywords = sbSource.toString();
        
        Map<String, List<String>> highlight = hit.highlight;
        if (null != highlight) {
            
//            List<String> names = highlight.get("NAME");
            List<String> nameEn = highlight.get("NAME.en");
            List<String> namePyNone = highlight.get("NAME.py_none");
            List<String> namePyOnly = highlight.get("NAME.py_only");
            List<String> nameT2S = highlight.get("NAME.t2s");
            List<String> nameRussian = highlight.get("NAME.russian");
            List<String> nameFrench = highlight.get("NAME.french");
            List<String> namePrototype = highlight.get("NAME.prototype");
            List<String> nameRaw = highlight.get("NAME.raw");
            
            if (null != namePyNone && namePyNone.get(0).indexOf(ES_BEGIN_TAG)> -1 && namePyNone.get(0).indexOf(ES_END_TAG) > -1) {
                String chinieseToPinyin = PinYin4jUtils.chinieseToPinyin(sb.toString());
                setSimilarity(keywords, source, chinieseToPinyin);
                source.put("NAME", namePyNone.get(0));
            }else if(null != namePyOnly && namePyOnly.get(0).indexOf(ES_BEGIN_TAG)> -1 && namePyOnly.get(0).indexOf(ES_END_TAG) > -1){
                String chinieseToPinyin = PinYin4jUtils.getPinYinHeadChar(sb.toString());
                setSimilarity(keywords, source, chinieseToPinyin);
                source.put("NAME", namePyOnly.get(0));
            }else if(null != nameEn && nameEn.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameEn.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("NAME", nameEn.get(0));
            }else if(null != namePrototype && namePrototype.get(0).indexOf(ES_BEGIN_TAG)> -1 && namePrototype.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("NAME", namePrototype.get(0));
            }else if (null != nameT2S && nameT2S.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameT2S.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("NAME", nameT2S.get(0));
            }else if (null != nameRussian && nameRussian.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameRussian.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("NAME", nameRussian.get(0));
            }else if (null != nameFrench && nameFrench.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameFrench.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("NAME", nameFrench.get(0));
            }else if (null != nameRaw && nameRaw.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameRaw.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("NAME", nameRaw.get(0));
            }
            
            List<String> country = highlight.get("COUNTRY");
            List<String> countryEn = highlight.get("COUNTRY.en");
            List<String> countryPyNone = highlight.get("COUNTRY.py_none");
            List<String> countryPyOnly = highlight.get("COUNTRY.py_only");
            List<String> countryT2S = highlight.get("COUNTRY.t2s");
            List<String> countryRussian = highlight.get("COUNTRY.russian");
            List<String> countryFrench = highlight.get("COUNTRY.french");
            List<String> countryPrototype = highlight.get("COUNTRY.prototype");
            List<String> countryRaw = highlight.get("COUNTRY.raw");
            if (null != country && country.get(0).indexOf(ES_BEGIN_TAG)> -1 && country.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("COUNTRY", country.get(0));
            }else if (null != countryPyNone && countryPyNone.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryPyNone.get(0).indexOf(ES_END_TAG) > -1) {
                String chinieseToPinyin = PinYin4jUtils.chinieseToPinyin(sb.toString());
                setSimilarity(keywords, source, chinieseToPinyin);
                source.put("COUNTRY", countryPyNone.get(0));
            }else if(null != countryPyOnly && countryPyOnly.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryPyOnly.get(0).indexOf(ES_END_TAG) > -1){
                String chinieseToPinyin = PinYin4jUtils.getPinYinHeadChar(sb.toString());
                setSimilarity(keywords, source, chinieseToPinyin);
                source.put("COUNTRY", countryPyOnly.get(0));
            }else if(null != countryEn && countryEn.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryEn.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("COUNTRY", countryEn.get(0));
            }else if(null != countryPrototype && countryPrototype.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryPrototype.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("COUNTRY", countryPrototype.get(0));
            }else if (null != countryT2S && countryT2S.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryT2S.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("COUNTRY", countryT2S.get(0));
            }else if (null != countryRussian && countryRussian.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryRussian.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("COUNTRY", countryRussian.get(0));
            }else if (null != countryFrench && countryFrench.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryFrench.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("COUNTRY", countryFrench.get(0));
            }else if (null != countryRaw && countryRaw.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryRaw.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("COUNTRY", countryRaw.get(0));
            }
            List<String> nationalid = highlight.get("NATIONALID");
            if (null != nationalid && nationalid.get(0).indexOf(ES_BEGIN_TAG)> -1 && nationalid.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("NATIONALID", nationalid.get(0));
            }
            List<String> passportid = highlight.get("PASSPORTID");
            if (null != passportid && passportid.get(0).indexOf(ES_BEGIN_TAG)> -1 && passportid.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(keywords, source, sb.toString());
                source.put("PASSPORTID", passportid.get(0));
            }
        }
        return source;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected Map<String, Object> getHighlightMap(Hit<Map, Void> hit, Map<String, Object> mapFieldParams, Map<String, Object> mapWeightParams) {
        Map<String, Object> source = hit.source;
        Map<String, List<String>> highlight = hit.highlight;
        if (null != highlight) {
//            List<String> names = highlight.get("NAME");
            List<String> nameEn = highlight.get("NAME.en");
            List<String> namePyNone = highlight.get("NAME.py_none");
            List<String> namePyOnly = highlight.get("NAME.py_only");
            List<String> nameT2S = highlight.get("NAME.t2s");
            List<String> nameRussian = highlight.get("NAME.russian");
            List<String> nameFrench = highlight.get("NAME.french");
            List<String> namePrototype = highlight.get("NAME.prototype");
            List<String> nameRaw = highlight.get("NAME.raw");
            
            if (null != namePyNone && namePyNone.get(0).indexOf(ES_BEGIN_TAG)> -1 && namePyNone.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.pyNone);
                source.put("NAME", namePyNone.get(0));
            }else if(null != namePyOnly && namePyOnly.get(0).indexOf(ES_BEGIN_TAG)> -1 && namePyOnly.get(0).indexOf(ES_END_TAG) > -1){
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.pyOnly);
                source.put("NAME", namePyOnly.get(0));
            }else if(null != nameEn && nameEn.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameEn.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("NAME", nameEn.get(0));
            }else if(null != namePrototype && namePrototype.get(0).indexOf(ES_BEGIN_TAG)> -1 && namePrototype.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("NAME", namePrototype.get(0));
            }else if (null != nameT2S && nameT2S.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameT2S.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("NAME", nameT2S.get(0));
            }else if (null != nameRussian && nameRussian.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameRussian.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("NAME", nameRussian.get(0));
            }else if (null != nameFrench && nameFrench.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameFrench.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("NAME", nameFrench.get(0));
            }else if (null != nameRaw && nameRaw.get(0).indexOf(ES_BEGIN_TAG)> -1 && nameRaw.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("NAME", nameRaw.get(0));
            }
            
            List<String> country = highlight.get("COUNTRY");
            List<String> countryEn = highlight.get("COUNTRY.en");
            List<String> countryPyNone = highlight.get("COUNTRY.py_none");
            List<String> countryPyOnly = highlight.get("COUNTRY.py_only");
            List<String> countryT2S = highlight.get("COUNTRY.t2s");
            List<String> countryRussian = highlight.get("COUNTRY.russian");
            List<String> countryFrench = highlight.get("COUNTRY.french");
            List<String> countryPrototype = highlight.get("COUNTRY.prototype");
            List<String> countryRaw = highlight.get("COUNTRY.raw");
            if (null != country && country.get(0).indexOf(ES_BEGIN_TAG)> -1 && country.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("COUNTRY", country.get(0));
            }else if (null != countryPyNone && countryPyNone.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryPyNone.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.pyNone);
                source.put("COUNTRY", countryPyNone.get(0));
            }else if(null != countryPyOnly && countryPyOnly.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryPyOnly.get(0).indexOf(ES_END_TAG) > -1){
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.pyOnly);
                source.put("COUNTRY", countryPyOnly.get(0));
            }else if(null != countryEn && countryEn.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryEn.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("COUNTRY", countryEn.get(0));
            }else if(null != countryPrototype && countryPrototype.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryPrototype.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("COUNTRY", countryPrototype.get(0));
            }else if (null != countryT2S && countryT2S.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryT2S.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("COUNTRY", countryT2S.get(0));
            }else if (null != countryRussian && countryRussian.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryRussian.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("COUNTRY", countryRussian.get(0));
            }else if (null != countryFrench && countryFrench.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryFrench.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("COUNTRY", countryFrench.get(0));
            }else if (null != countryRaw && countryRaw.get(0).indexOf(ES_BEGIN_TAG)> -1 && countryRaw.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("COUNTRY", countryRaw.get(0));
            }
            List<String> nationalid = highlight.get("NATIONALID");
            if (null != nationalid && nationalid.get(0).indexOf(ES_BEGIN_TAG)> -1 && nationalid.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("NATIONALID", nationalid.get(0));
            }
            List<String> passportid = highlight.get("PASSPORTID");
            if (null != passportid && passportid.get(0).indexOf(ES_BEGIN_TAG)> -1 && passportid.get(0).indexOf(ES_END_TAG) > -1) {
                setSimilarity(mapFieldParams, mapWeightParams, source, PyOther.other);
                source.put("PASSPORTID", passportid.get(0));
            }
        }
        return source;
    }

    @SuppressWarnings("rawtypes")
    private void setSimilarity(Map<String, Object> mapFieldParams, Map<String, Object> mapWeightParams, Map<String, Object> source, Enum pyOther) {
        //每个字段的相似度与权重
        Map<Double, Double> simWeightMap = new HashMap<Double, Double>();
        //动态分配比例
        int divider = 0;
        //是否所有字段都存在
        boolean isFull = true;
        for (Map.Entry<String, Object> entry : mapFieldParams.entrySet()) {
            String src = (String) entry.getValue();
            for (Map.Entry<String, Object> entryTarget : source.entrySet()) {
                Object value = entry.getValue();
                if(entry.getKey().equals(entryTarget.getKey())){
                   if(StringUtils.isEmpty(value == null ? "" : String.valueOf(value))){
                       isFull = false;
                       break;
                   } else {
                       String tar = (String) entryTarget.getValue();
                       double similarity = 0.0;
                       if(PyOther.pyNone.equals(pyOther)){
                           String chinieseToPinyin = PinYin4jUtils.chinieseToPinyin(tar);
                           similarity = getSimilarity(src, chinieseToPinyin);
                       }else if(PyOther.pyOnly.equals(pyOther)){
                           String chinieseToPinyin = PinYin4jUtils.getPinYinHeadChar(tar);
                           similarity = getSimilarity(src, chinieseToPinyin);
                       }else if(PyOther.other.equals(pyOther)){
                           similarity = getSimilarity(src, tar);
                       }
                       Double weight = Double.valueOf(String.valueOf(mapWeightParams.get(entry.getKey())));
                       divider += weight*10;
                       simWeightMap.put(similarity, weight);
                   }
                }
            }
        }
        //总的相似度
        Double totalSim = 0.0;
        //计算总相似度
        for (Map.Entry<Double, Double> simWeight : simWeightMap.entrySet()) {
            Double key = simWeight.getKey();
            Double value = simWeight.getValue();
            if(isFull){
                totalSim += key * value;
            }else {
                totalSim += key * (value / divider);
            }
        }
        source.put("MATCHING_DEGREE", totalSim);
    }
    
    enum PyOther {
        pyNone,
        pyOnly,
        other
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
    /*
     * public List<ESDto> documentSearch(String query, List<String> indexNames,
     * List<String> indexTypes, Integer pageNow, Integer pageSize) throws
     * IOException { try { SearchSourceBuilder searchSourceBuilder = new
     * SearchSourceBuilder(); // 使用query_string进行搜索 QueryStringQueryBuilder
     * queryStrBuilder = QueryBuilders.queryString(query); // 是否允许开头为通配符,是否分析通配符
     * queryStrBuilder
     * .analyzeWildcard(true).allowLeadingWildcard(true).field(ES_FIELD_CONTENT
     * ).field(ES_FIELD_CONTENT_PY)
     * .defaultOperator(org.elasticsearch.index.query
     * .QueryStringQueryBuilder.Operator.OR); SearchSourceBuilder ssb =
     * searchSourceBuilder.query(queryStrBuilder); // 设置分页
     * ssb.from((pageNow-1)*pageSize).size(pageSize); // TermsBuilder
     * termsBuilder = new TermsBuilder("groupById"); // 设置聚合 //
     * termsBuilder.field(ES_FIELD); // ssb.aggregation(termsBuilder); // 设置高亮
     * HighlightBuilder highlight = SearchSourceBuilder.highlight();
     * highlight.preTags("<b>").postTags("</b>").field(ES_FIELD_CONTENT);
     * ssb.highlight(highlight); // 设置排序方式 ssb.sort("_score", SortOrder.DESC);
     * 
     * if (logger.isDebugEnabled()) { logger.debug("requestParams:" +
     * ssb.toString()); } Builder builder = new Search.Builder(ssb.toString());
     * // 构建搜索 Search search =
     * builder.addIndex(indexNames).addType(indexTypes).allowNoIndices
     * (true).ignoreUnavailable
     * (true).setSearchType(SearchType.QUERY_THEN_FETCH).build(); SearchResult
     * result = client.execute(search);
     * System.out.println("记录数：---------------------------" +
     * result.getTotal()); logger.debug("search result response: " +
     * result.getJsonString()); List<ESDto> esDtos = new ArrayList<ESDto>(); if
     * (null != result && result.isSucceeded()) { List<Hit<ESDto, Void>> hits =
     * result.getHits(ESDto.class); for (Hit<ESDto, Void> hit : hits) { ESDto
     * source = hit.source; Map<String, List<String>> hl = hit.highlight; if
     * (null != hl) { List<String> list = hl.get(ES_FIELD_CONTENT);
     * source.setDescription(list.get(0)); } esDtos.add(source); }
     * logger.debug(LogUtils.format("esDtos", esDtos)); } else {
     * logger.debug("查询失败 :" + result.getErrorMessage()); } // 去重 // List<ESDto>
     * newList = CollectionUtils.removeDuplicateWithOrder(esDtos); return
     * esDtos; } catch (IOException e) { logger.debug("查询失败 " +
     * LogUtils.format("query", query, "indexNames", indexNames, "indexTypes",
     * indexTypes), e); throw new IOException("查询失败 ", e); } }
     */

    /**
     * 构建单个词条
     * 
     * @param field
     * @param value
     * @return
     */
    protected TermQueryBuilder termBuilder(String field, String value) {
        // 词条查询
        return QueryBuilders.termQuery(field, value);
    }

    /**
     * 范围构建
     * 
     * @param field
     * @param from
     * @return
     */
    protected RangeQueryBuilder rangeBuilderFrom(String field, String value) {
        // 范围查询
        return QueryBuilders.rangeQuery(field).gte(value);
    }

    /**
     * 范围构建
     * 
     * @param field
     * @param value
     * @return
     */
    protected RangeQueryBuilder rangeBuilderTo(String field, String value) {
        // 范围查询
        return QueryBuilders.rangeQuery(field).lte(value);
    }

    /**
     * like
     * 
     * @param field
     * @param value
     * @return
     */
    protected MoreLikeThisQueryBuilder likeBuilder(String field, String value) {
        // like查询
        return QueryBuilders.moreLikeThisQuery(field).likeText(value).minDocFreq(1).minTermFreq(1);
    }

    /**
     * fuzzy_like_this
     * 
     * @param field
     * @param value
     * @return
     */
    protected FuzzyLikeThisFieldQueryBuilder fuzzyLikeBuilder(String field, String value) {
        // fuzzy_like_this查询
        return QueryBuilders.fuzzyLikeThisFieldQuery(field).likeText(value).fuzziness(Fuzziness.fromSimilarity(0.0f));
    }

    /**
     * fuzzy_like_this
     * 
     * @param field
     * @param value
     * @return
     */
    protected FuzzyLikeThisQueryBuilder fuzzyLikeThisBuilder(String[] fields, String value) {
        // fuzzy_like_this查询
        return QueryBuilders.fuzzyLikeThisQuery(fields).likeText(value);
    }

    /**
     * fuzzy
     * 
     * @param field
     * @param value
     * @return
     */
    protected FuzzyQueryBuilder fuzzyBuilder(String field, String value) {
        // fuzzy查询
        return QueryBuilders.fuzzyQuery(field, value);
    }

    /**
     * fuzzy
     * 
     * @param field
     * @param value
     * @return
     */
    protected FuzzyQueryBuilder fuzzyBuilder(String field, String value, Fuzziness fuzziness) {
        // fuzzy查询
        return QueryBuilders.fuzzyQuery(field, value).fuzziness(fuzziness);
    }

    /**
     * match
     * 
     * @param field
     * @param value
     * @return
     */
    protected MatchQueryBuilder matchBuilder(String field, String value) {
        // match查询
        return QueryBuilders.matchQuery(field, value).fuzziness(0.5);
    }

    protected MultiMatchQueryBuilder multiMatchBuilder(String[] fieldNames, String value) {
        // multiMatch查询
        return QueryBuilders.multiMatchQuery(value, fieldNames).slop(0);
    }

    /**
     * matchAll
     * 
     * @param field
     * @param value
     * @return
     */
    protected MatchAllQueryBuilder matchAllBuilder() {
        // matchAll查询
        return QueryBuilders.matchAllQuery();
    }

    /**
     * 多个词条
     * 
     * @param field
     * @param values
     * @return
     */
    protected TermsQueryBuilder termsBuilder(String field, List<String> values) {
        // 多个词条查询
        return QueryBuilders.termsQuery(field, values);
    }

    /**
     * 字符串查询
     * 
     * @param value
     * @param fields
     * @return
     */
    protected QueryStringQueryBuilder queryStringBuilder(String value, List<String> fields) {
        QueryStringQueryBuilder queryStrBuilder = QueryBuilders.queryString(value);
        setQueryBuilderField(fields, queryStrBuilder);
        return queryStrBuilder.allowLeadingWildcard(false).analyzeWildcard(false);
    }

    private void setQueryBuilderField(List<String> fields, QueryStringQueryBuilder queryStrBuilder) {
        if (null != fields && !fields.isEmpty()) {
            for (String field : fields) {
                queryStrBuilder.field(field);
            }
        }
    }
    
    protected QueryStringQueryBuilder queryStringBuilder(List<String> fields, String value) {
        QueryStringQueryBuilder queryStrBuilder = QueryBuilders.queryString("*" + value + "*");
        setQueryBuilderField(fields, queryStrBuilder);
        return queryStrBuilder.allowLeadingWildcard(true).analyzeWildcard(true);
    }

    /**
     * 正则表达式
     * 
     * @param field
     * @param value
     * @return
     */
    protected RegexpQueryBuilder regexpBuilder(String field, String value) {
        // 正则表达式查询
        return QueryBuilders.regexpQuery(field, "+" + value + "");
    }

    /**
     * 通配符查询
     * 
     * @param field
     * @param value
     * @return
     */
    protected WildcardQueryBuilder wildcardBuilder(String field, String value) {
        // 通配符查询
        return QueryBuilders.wildcardQuery(field, "*" + value + "*");
    }

    /**
     * 前缀查询
     * 
     * @param field
     * @param value
     * @return
     */
    protected PrefixQueryBuilder prefixBuilder(String field, String value) {
        // 前缀查询
        return QueryBuilders.prefixQuery(field, value);
    }

    protected MatchQueryBuilder matchPhraseBuilder(String field, String value) {
        return QueryBuilders.matchPhraseQuery(value, field).slop(5);
    }

    protected MatchQueryBuilder matchPhraseBuilder(String field, String value, int slop) {
        return QueryBuilders.matchPhraseQuery(value, field).slop(slop);
    }

    protected BoolQueryBuilder boolQueryBuilder() {
        return QueryBuilders.boolQuery();
    }

    protected FunctionScoreQueryBuilder functionScoreBuilder(String value, List<String> fields) {
        return QueryBuilders.functionScoreQuery(queryStringBuilder(value, fields)).add(new ScoreFunctionBuilder() {

            @Override
            public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
                return builder.field("functions").startArray().startObject().field("script_score").startObject().field("script", "_score*1")
                        .endObject().endObject().startObject().field("random_score").startObject().field("seed", 12345).endObject().endObject()
                        .endArray().field("min_score", "0.75");
            }

            @Override
            public String getName() {
                return "function";
            }
        });
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
    public boolean addIndex(String indexName) throws IOException {
        JestResult result = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("indexName:" + indexName);
            }
            org.elasticsearch.common.settings.ImmutableSettings.Builder buildIndexSetting = this.buildIndexSetting();
            io.searchbox.indices.CreateIndex.Builder bIndex = new CreateIndex.Builder(indexName);
            CreateIndex cIndex = bIndex.settings(buildIndexSetting.build().getAsMap()).build();
            // CreateIndex cIndex = bIndex.build();
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
    public ImmutableSettings.Builder buildIndexSetting() throws IOException {
        // try {
        ImmutableSettings.Builder settingsBuilder = customImmutableSetting.getBuilder();
        // XContentBuilder settings =
        // XContentFactory.jsonBuilder().startObject().startObject("analysis").startObject("analyzer")
        // .startObject("pinyin_analyzer").startArray("filter").value("word_delimiter").value("nGram").endArray().startArray("tokenizer")
        // .value("my_pinyin").endArray().endObject().endObject().startObject("tokenizer").startObject("my_pinyin").field("type",
        // "pinyin")
        // .field("first_letter", "prefix").field("padding_char",
        // " ").endObject().endObject().endObject();
        // XContentBuilder settings = XContentFactory.jsonBuilder();
        // settingsBuilder.put(JsonUtils.jsonToObject(settings.string(),
        // Map.class));
        // logger.debug(LogUtils.format("settings", settingsBuilder));
        return settingsBuilder;
        // } catch (IOException e) {
        // logger.debug("构建索引配置失败", e);
        // throw new IOException("构建索引配置失败");
        // }
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
            if (!StringUtils.isEmpty(indexType)) {
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
    
    /**
     * 根据索引全部删除
     * @param indexName
     * @return
     * @throws IOException
     */
    public boolean deleteIndex(String indexName) throws IOException {
        return deleteIndex(indexName, null);
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
