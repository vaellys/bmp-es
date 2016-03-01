package com.ist.ioc.service.common.elasticsearch;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.unit.Fuzziness;

import com.ist.common.es.util.page.Pagination;
import com.ist.dto.bmp.ESDto;

/**
 * 搜索服务提供者
 * 
 * @author qianguobing
 * @date 2015-07-17
 */
public interface IESService {

    /**
     * 创建索引
     * 
     * @param indexName
     *            索引名称
     * @return boolean
     * @throws IOException
     */
    public boolean addIndex(String indexName) throws IOException;

    /**
     * 删除索引类型
     * 
     * @param indexName
     *            索引名称
     * @param indexType
     *            索引类型 若为空，则删除指定的索引库，否则删除指定的索引类型
     * @return boolean
     * @throws IOException
     */
    public boolean deleteIndex(String indexName, String indexType) throws IOException;

    /**
     * 创建索引并对文档进行索引
     * 
     * @param indexName
     *            索引名称
     * @param indexType
     *            索引类型
     * @param documents
     *            索引的文档
     * @param action
     *            索引动作 1 创建或者更新 2 删除 3 局部更新
     * @return boolean
     * @throws IOException
     */
    public boolean documentHandler(String indexName, String indexType, List<Map<String, Object>> documents, Integer action) throws IOException;
    
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
    public boolean documentHandler(Map<String, List<String>> mapParams, List<ESDto> documents, Integer action) throws IOException;

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
     */
    public boolean deleteDoc(String indexName, String indexType, String docId) throws IOException;

    /**
     * 检索文档
     * 
     * @param indexNames
     *            索引名称集合
     * @param indexTypes
     *            索引类型集合
     * @param queryFields
     *            查询字段集合 若为空，则对所有字段进行检索
     * @param hlFields
     *            高亮显示字段   
     * @param keywords
     *            检索关键字
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Map<String, Object> 包含分页参数
     * @throws IOException
     */
    public Map<String, Object> documentSearch(List<String> indexNames, List<String> indexTypes, List<String> queryFields, List<String> hlFields, String keywords,
            Integer pageNow, Integer pageSize) throws IOException;
    
    /**
     * 检索文档
     * 
     * @param indexName
     *            索引名称
     * @param indexType
     *            索引类型
     * @param queryFields
     *            查询字段集合 若为空，则对所有字段进行检索
     * @param hlFields
     *            高亮显示字段   
     * @param keywords
     *            检索关键字
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Map<String, Object> 包含分页参数
     * @throws IOException
     */
    public Map<String, Object> documentSearch(String indexName, String indexType, List<String> queryFields, List<String> hlFields, String keywords,
            Integer pageNow, Integer pageSize) throws IOException;
    
    /**
     * 检索文档
     * <p>pagination中有当前页的集合List<ESDto><p>
     * @param indexNames
     *            索引名称集合
     * @param indexTypes
     *            索引类型集合
     * @param queryFields
     *            查询字段集合 若为空，则对所有字段进行检索
     * @param keywords
     *            检索关键字
     * @param pageNow
     *            当前页
     * @param pageSize
     *            页大小
     * @return Pagination 分页对象
     * @throws IOException
     */
    public Pagination documentSearch(List<String> indexNames, List<String> indexTypes, List<String> queryFields, String keywords,
            Integer pageNow, Integer pageSize) throws IOException;

    public Pagination documentSearchWithSuggestion(List<String> indexNames, List<String> indexTypes, List<String> queryFields, String keywords,
            Integer pageNow, Integer pageSize) throws IOException;

    public Pagination documentSearchWithTerm(List<String> indexNames, List<String> indexTypes, String queryField, String keywords, Integer pageNow,
            Integer pageSize) throws IOException;

    Pagination documentSearchWithMatch(List<String> indexNames, List<String> indexTypes, String queryField, String keywords, Integer pageNow,
            Integer pageSize) throws IOException;

    Pagination documentSearchWithFunctionScore(List<String> indexNames, List<String> indexTypes, List<String> queryFields, String keywords,
            Integer pageNow, Integer pageSize) throws IOException;

    Map<String, Object> documentSearchWithMatchPhrase(String indexName, String indexType, String queryFields, List<String> hlFields,
            String keywords, Integer pageNow, Integer pageSize) throws IOException;

    Map<String, Object> documentSearchWithFuzzy(String indexName, String indexType, String queryField, List<String> hlFields, String keywords,
            Integer pageNow, Integer pageSize) throws IOException;

    Map<String, Object> documentSearchWithMultiMatch(String indexName, String indexType, String[] queryFields, List<String> hlFields, String keywords,
            Integer pageNow, Integer pageSize) throws IOException;

    List<Map<String, Object>> documentSearchWithCustomerScore(String indexName, String indexType, List<String> queryFields, List<String> hlFields,
            String keywords, Integer pageNow, Integer pageSize) throws IOException;

    public Map<String, Object> documentSearchWithFuzzy(String indexName, String indexType, String queryField, List<String> hlFields, Fuzziness fuzziness,
            String keywords, Integer pageNow, Integer pageSize) throws IOException;

    boolean dirHandler(String indexName, String indexType, String path, Integer action) throws IOException;

    public Map<String, Object> documentSearch(String indexName, String indexType, String keywords, Integer pageNow, Integer pageSize) throws IOException;

    public boolean deleteIndex(String indexName) throws IOException;

    public Map<String, Object> documentSearch(String indexName, String indexType, Map<String, Object> mapFieldParams, Integer pageNow, Integer pageSize)
            throws IOException;

    public Map<String, Object> documentSearch(String indexName, String indexType, Map<String, Object> mapFieldParams, Map<String, Object> mapWeightParams,
            Integer pageNow, Integer pageSize) throws IOException;

}
