package com.ist.ioc.service.common.elasticsearch.impl;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ist.common.es.util.LogUtils;
import com.ist.common.es.util.page.Pagination;
import com.ist.dto.bmp.ESDto;
import com.ist.ioc.service.common.elasticsearch.IESService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring/applicationContext-es.xml" })
public class IEPOCServiceImplTest extends AbstractJUnit4SpringContextTests {

    @Resource
    private IESService iesService;

    private Log logger = LogFactory.getLog(this.getClass());

    @Before
    public void setUp() throws Exception {
        // iess = new IESServiceImpl();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * 建索引
     * 测试拼音分词
     */
    @Test
    public void testDocumentHandler() {

        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("ENT_ID", 1);
                map.put("NAME", "张三");
                map.put("PASSPORTID", "88886");
                map.put("NATIONALID", "8888886");
                String content = "法国";
                map.put("COUNTRY", content);
                Map<String, Object> map1 = new HashMap<String, Object>();
                list.add(map);
            iesService.documentHandler("appletest", "iphone", list, 1);
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    /**
     * 建索引
     * 测试中文分词
     */
    @Test
    public void testDocumentHandler2() {

        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", 6);
            map.put("title", "搜狐科技 文/王雪莹iPhone 6s的s是什么意思呢？拿到iPhone 6s的《华尔街日报》说");
            String content = "搜狐科技 文/王雪莹iPhone 6s的s是什么意思呢？拿到iPhone 6s的《华尔街日报》说，这个s可能就是“slightly（些微）better”的意思……这话怎么说？说好的电池续航呢？对于iPhone续航能力的问题一直是人们关注的要点，尽管库克此前信誓旦旦地说6s和6s Plus能让用户摆脱“贴墙族”（总守着插头充电的一族）的称号，但实际情况似乎跟我们期待并不相同。在实测中人们发现，除非开启“节能模式”（系统将自动关闭新消息亮屏提示），否则6s在正常使用的情况下，其耗电量并没有明显下降，反倒是5.5英寸的6s Plus的耗电量有所降低，二者的持续续航时间分别为8小时和8小时20分。此前业内普遍认为，机身增厚的新机型会为电池容量带来很大的提升空间，然而事实证明，0.01英寸的增厚更多地是为了配合3D Touch感压触屏技术，而非万众期待的电池技术。至于无线充电及快充技术，笔者认为6s和6s Plus的这项功能也属于噱头大于功效——仍需等待至少2小时（6s Plus要3小时）才能充满。相比之下，三星的最新旗舰机75~90分钟即可搞定，而新Moto X Pure只要1小时就能充满。children";
            map.put("content", content);
            Map<String, Object> map1 = new HashMap<String, Object>();
            list.add(map);
            iesService.documentHandler("appletest", "iphone", list, 1);
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    
    /**
     * 测试英文分词
     */
    @Test
    public void testDocumentHandlerByEng() {

        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", 11121);
            map.put("title", "beijing chnia 1212121");
            String content = "How are you children foxes coming? 你好你们在干嘛，中华人民共和国";
            map.put("content", content);
            Map<String, Object> map1 = new HashMap<String, Object>();
            list.add(map);
            iesService.documentHandler("apple", "iphone", list, 1);
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    /**
     * 拼音搜索
     */
    @Test
    public void testDocumentSearch() {
        try {
            // List<Map<String, Object>> list= new ArrayList<Map<String,
            // Object>>();
            // Map<String, Object> map = new HashMap<String, Object>();
            // map.put("id", 1111111);
            // map.put("title", "外媒评6s和6s Plus上手:中规中矩 微微提升");
            // String content =
            // "搜狐科技 文/王雪莹iPhone 6s的s是什么意思呢？拿到iPhone 6s的《华尔街日报》说，这个s可能就是“slightly（些微）better”的意思……这话怎么说？说好的电池续航呢？对于iPhone续航能力的问题一直是人们关注的要点，尽管库克此前信誓旦旦地说6s和6s Plus能让用户摆脱“贴墙族”（总守着插头充电的一族）的称号，但实际情况似乎跟我们期待并不相同。在实测中人们发现，除非开启“节能模式”（系统将自动关闭新消息亮屏提示），否则6s在正常使用的情况下，其耗电量并没有明显下降，反倒是5.5英寸的6s Plus的耗电量有所降低，二者的持续续航时间分别为8小时和8小时20分。此前业内普遍认为，机身增厚的新机型会为电池容量带来很大的提升空间，然而事实证明，0.01英寸的增厚更多地是为了配合3D Touch感压触屏技术，而非万众期待的电池技术。至于无线充电及快充技术，笔者认为6s和6s Plus的这项功能也属于噱头大于功效——仍需等待至少2小时（6s Plus要3小时）才能充满。相比之下，三星的最新旗舰机75~90分钟即可搞定，而新Moto X Pure只要1小时就能充满。How are you?";
            // map.put("content", content);
            // list.add(map);
            String keywords = "zs";
            List<String> strList = new ArrayList<String>();
            // strList.add("content");
            strList.add("title.py_only");
            Map<String, Object> documentSearch = iesService.documentSearch("apple", "iphone", strList, null, keywords, 1, 10);
            logger.debug(LogUtils.format("R", documentSearch));
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }
    
    /**
     * 名单检索（前台）
     */
    @Test
    public void testDocumentSearch2() {
        try {
            String keywords = "982601192101150015测测个CHN982601192101150015";
            Map<String, Object> documentSearch = iesService.documentSearch("index", "wcoIndex", keywords, 1, 10);
            logger.debug(LogUtils.format("R", documentSearch));
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }
    
    @Test
    public void testDocumentSearchWithPyNone() {
        try {
            // List<Map<String, Object>> list= new ArrayList<Map<String,
            // Object>>();
            // Map<String, Object> map = new HashMap<String, Object>();
            // map.put("id", 1111111);
            // map.put("title", "外媒评6s和6s Plus上手:中规中矩 微微提升");
            // String content =
            // "搜狐科技 文/王雪莹iPhone 6s的s是什么意思呢？拿到iPhone 6s的《华尔街日报》说，这个s可能就是“slightly（些微）better”的意思……这话怎么说？说好的电池续航呢？对于iPhone续航能力的问题一直是人们关注的要点，尽管库克此前信誓旦旦地说6s和6s Plus能让用户摆脱“贴墙族”（总守着插头充电的一族）的称号，但实际情况似乎跟我们期待并不相同。在实测中人们发现，除非开启“节能模式”（系统将自动关闭新消息亮屏提示），否则6s在正常使用的情况下，其耗电量并没有明显下降，反倒是5.5英寸的6s Plus的耗电量有所降低，二者的持续续航时间分别为8小时和8小时20分。此前业内普遍认为，机身增厚的新机型会为电池容量带来很大的提升空间，然而事实证明，0.01英寸的增厚更多地是为了配合3D Touch感压触屏技术，而非万众期待的电池技术。至于无线充电及快充技术，笔者认为6s和6s Plus的这项功能也属于噱头大于功效——仍需等待至少2小时（6s Plus要3小时）才能充满。相比之下，三星的最新旗舰机75~90分钟即可搞定，而新Moto X Pure只要1小时就能充满。How are you?";
            // map.put("content", content);
            // list.add(map);
            String keywords = "yisi";
            List<String> strList = new ArrayList<String>();
            // strList.add("content");
//            strList.add("title.py_none");
            strList.add("content.py");
            strList.add("content");
            strList.add("content.en");
//            strList.add("title.py_none");
            List<String> hl = new ArrayList<String>();
//            hl.add("title.py_none");
            hl.add("content.py");
            hl.add("content");
            hl.add("content.en");
//            hl.add("title.py_only");
            Map<String, Object> documentSearch = iesService.documentSearch("appletest", "iphone", strList, hl, keywords, 1, 10);
            logger.debug(LogUtils.format("R", documentSearch));
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    @Test
    public void testDocumentSearchWithCN() {
        try {
            // List<Map<String, Object>> list= new ArrayList<Map<String,
            // Object>>();
            // Map<String, Object> map = new HashMap<String, Object>();
            // map.put("id", 1111111);
            // map.put("title", "外媒评6s和6s Plus上手:中规中矩 微微提升");
            // String content =
            // "搜狐科技 文/王雪莹iPhone 6s的s是什么意思呢？拿到iPhone 6s的《华尔街日报》说，这个s可能就是“slightly（些微）better”的意思……这话怎么说？说好的电池续航呢？对于iPhone续航能力的问题一直是人们关注的要点，尽管库克此前信誓旦旦地说6s和6s Plus能让用户摆脱“贴墙族”（总守着插头充电的一族）的称号，但实际情况似乎跟我们期待并不相同。在实测中人们发现，除非开启“节能模式”（系统将自动关闭新消息亮屏提示），否则6s在正常使用的情况下，其耗电量并没有明显下降，反倒是5.5英寸的6s Plus的耗电量有所降低，二者的持续续航时间分别为8小时和8小时20分。此前业内普遍认为，机身增厚的新机型会为电池容量带来很大的提升空间，然而事实证明，0.01英寸的增厚更多地是为了配合3D Touch感压触屏技术，而非万众期待的电池技术。至于无线充电及快充技术，笔者认为6s和6s Plus的这项功能也属于噱头大于功效——仍需等待至少2小时（6s Plus要3小时）才能充满。相比之下，三星的最新旗舰机75~90分钟即可搞定，而新Moto X Pure只要1小时就能充满。How are you?";
            // map.put("content", content);
            // list.add(map);
            String keywords = "beijing chnia";
            List<String> strList = new ArrayList<String>();
            // strList.add("content");
            strList.add("title");
            // strList.add("content.en");
            Map<String, Object> documentSearch = iesService.documentSearch("apple", "iphone", strList, null, keywords, 1, 10);
            logger.debug(LogUtils.format("R", documentSearch));
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    @Test
    public void testDocumentSearchWithEN() {
        try {
            // List<Map<String, Object>> list= new ArrayList<Map<String,
            // Object>>();
            // Map<String, Object> map = new HashMap<String, Object>();
            // map.put("id", 1111111);
            // map.put("title", "外媒评6s和6s Plus上手:中规中矩 微微提升");
            // String content =
            // "搜狐科技 文/王雪莹iPhone 6s的s是什么意思呢？拿到iPhone 6s的《华尔街日报》说，这个s可能就是“slightly（些微）better”的意思……这话怎么说？说好的电池续航呢？对于iPhone续航能力的问题一直是人们关注的要点，尽管库克此前信誓旦旦地说6s和6s Plus能让用户摆脱“贴墙族”（总守着插头充电的一族）的称号，但实际情况似乎跟我们期待并不相同。在实测中人们发现，除非开启“节能模式”（系统将自动关闭新消息亮屏提示），否则6s在正常使用的情况下，其耗电量并没有明显下降，反倒是5.5英寸的6s Plus的耗电量有所降低，二者的持续续航时间分别为8小时和8小时20分。此前业内普遍认为，机身增厚的新机型会为电池容量带来很大的提升空间，然而事实证明，0.01英寸的增厚更多地是为了配合3D Touch感压触屏技术，而非万众期待的电池技术。至于无线充电及快充技术，笔者认为6s和6s Plus的这项功能也属于噱头大于功效——仍需等待至少2小时（6s Plus要3小时）才能充满。相比之下，三星的最新旗舰机75~90分钟即可搞定，而新Moto X Pure只要1小时就能充满。How are you?";
            // map.put("content", content);
            // list.add(map);
            String keywords = "touch";
            List<String> strList = new ArrayList<String>();
            // strList.add("content");
            strList.add("content.en");
            List<String> hl = new ArrayList<String>();
            hl.add("content.en");
            Map<String, Object> documentSearch = iesService.documentSearch("appletest", "iphone", strList, hl, keywords, 1, 10);
            logger.debug(LogUtils.format("R", documentSearch));
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    @Test
    public void testDocumentHandlerWithTS() {

        try {
            Map<String, List<String>> mapParams = new HashMap<String, List<String>>();
            List<String> types = new ArrayList<String>();
            types.add("bmp");
            mapParams.put("ist", types);

            List<ESDto> documents = new ArrayList<ESDto>();
            ESDto dto = new ESDto();
            dto.setId("333");
            dto.setTitle("北京時間");
            dto.setContent("北京時間");
            dto.setName("北京時間");
            dto.setDescription("重用张江张江");
            dto.setPath("foxes how are computing children sherlock holmes work");
            documents.add(dto);

            iesService.documentHandler(mapParams, documents, 1);
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    @Test
    public void testDocumentHandlerWithTS2() {

        try {
            Map<String, List<String>> mapParams = new HashMap<String, List<String>>();
            List<String> types = new ArrayList<String>();
            types.add("bmp");
            mapParams.put("ist", types);

            List<ESDto> documents = new ArrayList<ESDto>();
            ESDto dto = new ESDto();
            dto.setId("333f4");
            dto.setTitle("國際電視臺");
            dto.setContent("北京時間");
            dto.setName("北京時間");
            dto.setDescription("重用张江张江");
            dto.setPath("foxes how are computing children sherlock holmes work");
            documents.add(dto);

            iesService.documentHandler(mapParams, documents, 1);
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    @Test
    public void testDocumentSearchWithTS() {
        try {
            List<String> indexNames = new ArrayList<String>();
            indexNames.add("ist");
            List<String> indexTypes = new ArrayList<String>();
            indexTypes.add("bmp");
            String query = "电视台";
            List<String> queryFields = new ArrayList<String>();
            // queryFields.add("title");
            // queryFields.add("description");
            queryFields.add("title.t2s");
            iesService.documentSearch(indexNames, indexTypes, queryFields, query, 1, 10);
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    @Test
    public void testDocumentSearchWithST() {
        try {
            List<String> indexNames = new ArrayList<String>();
            indexNames.add("ist");
            List<String> indexTypes = new ArrayList<String>();
            indexTypes.add("bmp");
            String query = "时";
            List<String> queryFields = new ArrayList<String>();
            // queryFields.add("title");
            // queryFields.add("description");
            queryFields.add("title.t2s");
            iesService.documentSearch(indexNames, indexTypes, queryFields, query, 1, 10);
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    @Test
    public void testDocumentSearchDto2() {
        try {
            List<String> indexNames = new ArrayList<String>();
            indexNames.add("ist");
            List<String> indexTypes = new ArrayList<String>();
            indexTypes.add("bmp");
            String query = "serlock holnes";
            List<String> queryFields = new ArrayList<String>();
            // queryFields.add("title");
            // queryFields.add("description");
            queryFields.add("path");
            iesService.documentSearch(indexNames, indexTypes, queryFields, query, 1, 10);
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    @Test
    public void testDocumentSearchDto3() {
        try {
            List<String> indexNames = new ArrayList<String>();
            indexNames.add("ist");
            List<String> indexTypes = new ArrayList<String>();
            indexTypes.add("bmp");
            String query = "serlock holnes";
            List<String> queryFields = new ArrayList<String>();
            // queryFields.add("title");
            // queryFields.add("description");
            queryFields.add("path");
            iesService.documentSearchWithSuggestion(indexNames, indexTypes, null, null, 1, 10);
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    @Test
    public void testDocumentHandlerWithRussian() {

        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", 2324221);
            map.put("title", "Мы все китайцы");
            String content = "Мы все китайцы";
            map.put("content", content);
            Map<String, Object> map1 = new HashMap<String, Object>();
            list.add(map);
            iesService.documentHandler("russian", "iphone", list, 1);
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    @Test
    public void testDocumentSearchWithRussian() {
        try {
            // List<Map<String, Object>> list= new ArrayList<Map<String,
            // Object>>();
            // Map<String, Object> map = new HashMap<String, Object>();
            // map.put("id", 1111111);
            // map.put("title", "外媒评6s和6s Plus上手:中规中矩 微微提升");
            // String content =
            // "搜狐科技 文/王雪莹iPhone 6s的s是什么意思呢？拿到iPhone 6s的《华尔街日报》说，这个s可能就是“slightly（些微）better”的意思……这话怎么说？说好的电池续航呢？对于iPhone续航能力的问题一直是人们关注的要点，尽管库克此前信誓旦旦地说6s和6s Plus能让用户摆脱“贴墙族”（总守着插头充电的一族）的称号，但实际情况似乎跟我们期待并不相同。在实测中人们发现，除非开启“节能模式”（系统将自动关闭新消息亮屏提示），否则6s在正常使用的情况下，其耗电量并没有明显下降，反倒是5.5英寸的6s Plus的耗电量有所降低，二者的持续续航时间分别为8小时和8小时20分。此前业内普遍认为，机身增厚的新机型会为电池容量带来很大的提升空间，然而事实证明，0.01英寸的增厚更多地是为了配合3D Touch感压触屏技术，而非万众期待的电池技术。至于无线充电及快充技术，笔者认为6s和6s Plus的这项功能也属于噱头大于功效——仍需等待至少2小时（6s Plus要3小时）才能充满。相比之下，三星的最新旗舰机75~90分钟即可搞定，而新Moto X Pure只要1小时就能充满。How are you?";
            // map.put("content", content);
            // list.add(map);
            String keywords = "китайц";
            List<String> strList = new ArrayList<String>();
            // strList.add("content");
            strList.add("title.russian");
            Map<String, Object> documentSearch = iesService.documentSearch("russian", "iphone", strList, null, keywords, 1, 10);
            logger.debug(LogUtils.format("R", documentSearch));
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    @Test
    public void testDocumentHandlerWithFrench() {

        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", 2324221);
            map.put("title", "La République populaire de Chine");
            String content = "La République populaire de Chine";
            map.put("content", content);
            Map<String, Object> map1 = new HashMap<String, Object>();
            list.add(map);
            iesService.documentHandler("french", "iphone", list, 1);
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    @Test
    public void testDocumentSearchWithFrench() {
        try {
            // List<Map<String, Object>> list= new ArrayList<Map<String,
            // Object>>();
            // Map<String, Object> map = new HashMap<String, Object>();
            // map.put("id", 1111111);
            // map.put("title", "外媒评6s和6s Plus上手:中规中矩 微微提升");
            // String content =
            // "搜狐科技 文/王雪莹iPhone 6s的s是什么意思呢？拿到iPhone 6s的《华尔街日报》说，这个s可能就是“slightly（些微）better”的意思……这话怎么说？说好的电池续航呢？对于iPhone续航能力的问题一直是人们关注的要点，尽管库克此前信誓旦旦地说6s和6s Plus能让用户摆脱“贴墙族”（总守着插头充电的一族）的称号，但实际情况似乎跟我们期待并不相同。在实测中人们发现，除非开启“节能模式”（系统将自动关闭新消息亮屏提示），否则6s在正常使用的情况下，其耗电量并没有明显下降，反倒是5.5英寸的6s Plus的耗电量有所降低，二者的持续续航时间分别为8小时和8小时20分。此前业内普遍认为，机身增厚的新机型会为电池容量带来很大的提升空间，然而事实证明，0.01英寸的增厚更多地是为了配合3D Touch感压触屏技术，而非万众期待的电池技术。至于无线充电及快充技术，笔者认为6s和6s Plus的这项功能也属于噱头大于功效——仍需等待至少2小时（6s Plus要3小时）才能充满。相比之下，三星的最新旗舰机75~90分钟即可搞定，而新Moto X Pure只要1小时就能充满。How are you?";
            // map.put("content", content);
            // list.add(map);
            String keywords = "populairy";
            List<String> strList = new ArrayList<String>();
            // strList.add("content");
            strList.add("title.french");
            Map<String, Object> documentSearch = iesService.documentSearch("french", "iphone", strList, null, keywords, 1, 10);
            logger.debug(LogUtils.format("R", documentSearch));
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }

    @Test
    public void testDocumentSearchWithTerm() {
        try {
            // List<Map<String, Object>> list= new ArrayList<Map<String,
            // Object>>();
            // Map<String, Object> map = new HashMap<String, Object>();
            // map.put("id", 1111111);
            // map.put("title", "外媒评6s和6s Plus上手:中规中矩 微微提升");
            // String content =
            // "搜狐科技 文/王雪莹iPhone 6s的s是什么意思呢？拿到iPhone 6s的《华尔街日报》说，这个s可能就是“slightly（些微）better”的意思……这话怎么说？说好的电池续航呢？对于iPhone续航能力的问题一直是人们关注的要点，尽管库克此前信誓旦旦地说6s和6s Plus能让用户摆脱“贴墙族”（总守着插头充电的一族）的称号，但实际情况似乎跟我们期待并不相同。在实测中人们发现，除非开启“节能模式”（系统将自动关闭新消息亮屏提示），否则6s在正常使用的情况下，其耗电量并没有明显下降，反倒是5.5英寸的6s Plus的耗电量有所降低，二者的持续续航时间分别为8小时和8小时20分。此前业内普遍认为，机身增厚的新机型会为电池容量带来很大的提升空间，然而事实证明，0.01英寸的增厚更多地是为了配合3D Touch感压触屏技术，而非万众期待的电池技术。至于无线充电及快充技术，笔者认为6s和6s Plus的这项功能也属于噱头大于功效——仍需等待至少2小时（6s Plus要3小时）才能充满。相比之下，三星的最新旗舰机75~90分钟即可搞定，而新Moto X Pure只要1小时就能充满。How are you?";
            // map.put("content", content);
            // list.add(map);
            String keywords = "Beijing china";
            List<String> indexNames = new ArrayList<String>();
            indexNames.add("apple");
            List<String> indexTypes = new ArrayList<String>();
            indexTypes.add("iphone");
            // strList.add("content");
            String field = "title";
            // strList.add("content.en");
            Pagination documentSearchWithTerm = iesService.documentSearchWithTerm(indexNames, indexTypes, field, keywords, 1, 10);
            logger.debug(LogUtils.format("R", documentSearchWithTerm));
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }


    @Test
    public void testDocumentSearchWithMatchPhrase() {
        try {
            // List<Map<String, Object>> list= new ArrayList<Map<String,
            // Object>>();
            // Map<String, Object> map = new HashMap<String, Object>();
            // map.put("id", 1111111);
            // map.put("title", "外媒评6s和6s Plus上手:中规中矩 微微提升");
            // String content =
            // "搜狐科技 文/王雪莹iPhone 6s的s是什么意思呢？拿到iPhone 6s的《华尔街日报》说，这个s可能就是“slightly（些微）better”的意思……这话怎么说？说好的电池续航呢？对于iPhone续航能力的问题一直是人们关注的要点，尽管库克此前信誓旦旦地说6s和6s Plus能让用户摆脱“贴墙族”（总守着插头充电的一族）的称号，但实际情况似乎跟我们期待并不相同。在实测中人们发现，除非开启“节能模式”（系统将自动关闭新消息亮屏提示），否则6s在正常使用的情况下，其耗电量并没有明显下降，反倒是5.5英寸的6s Plus的耗电量有所降低，二者的持续续航时间分别为8小时和8小时20分。此前业内普遍认为，机身增厚的新机型会为电池容量带来很大的提升空间，然而事实证明，0.01英寸的增厚更多地是为了配合3D Touch感压触屏技术，而非万众期待的电池技术。至于无线充电及快充技术，笔者认为6s和6s Plus的这项功能也属于噱头大于功效——仍需等待至少2小时（6s Plus要3小时）才能充满。相比之下，三星的最新旗舰机75~90分钟即可搞定，而新Moto X Pure只要1小时就能充满。How are you?";
            // map.put("content", content);
            // list.add(map);
            String keywords = "中华国";
            List<String> indexNames = new ArrayList<String>();
            indexNames.add("appletest");
            List<String> indexTypes = new ArrayList<String>();
            indexTypes.add("iphone");
            // strList.add("content");

            // strList.add("content.en");
            List<String> strList = new ArrayList<String>();
            // strList.add("content");
            strList.add("title.raw");
            List<String> hlFields = null;
            Map<String, Object> documentSearchWithMatchPhrase = iesService.documentSearchWithMatchPhrase("appletest", "iphone", "title.raw",
                    hlFields, keywords, 1, 10);
            logger.debug(LogUtils.format("R", documentSearchWithMatchPhrase));
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }
    
    @Test
    public void testDeleteIndex() {
        try {
            List<String> indexNames = new ArrayList<String>();
            indexNames.add("1300");
            indexNames.add("aaa");
            indexNames.add("edits");
            indexNames.add("french");
            indexNames.add("ist");
            indexNames.add("p12831");
            indexNames.add("p12832");
            indexNames.add("qgb");
            indexNames.add("qgbtest");
            indexNames.add("qq");
            indexNames.add("qq2");
            indexNames.add("russian");
            indexNames.add("testindex");
            for (String name : indexNames) {
                iesService.deleteIndex(name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 名单检索（后台）
     */
    @Test
    public void testDocumentSearchWithBack() {
        try {
            Map<String, Object> mapFieldParams = new HashMap<String, Object>();
            mapFieldParams.put("NAME", "Abdel-Bari, Adel Abdul Megeed");
            mapFieldParams.put("COUNTRY", "Morocco");
            mapFieldParams.put("PASSPORTID", "");
            mapFieldParams.put("NATIONALID", "");
            Map<String, Object> documentSearch = iesService.documentSearch("index", "wcoIndex", mapFieldParams, 1, 10);
            logger.debug(LogUtils.format("R", documentSearch));
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }
    
    /**
     * 名单检索（后台）
     */
    @Test
    public void testDocumentSearchWithBackWeight() {
        try {
            Map<String, Object> mapFieldParams = new HashMap<String, Object>();
            Map<String, Object> mapWightParams = new HashMap<String, Object>();
            mapFieldParams.put("NAME", "");
            mapFieldParams.put("COUNTRY", "");
            mapFieldParams.put("PASSPORTID", "9826011921011500");
            mapFieldParams.put("NATIONALID", "");
            mapWightParams.put("NAME", 0.2);
            mapWightParams.put("COUNTRY", 0.1);
            mapWightParams.put("PASSPORTID", 0.4);
            mapWightParams.put("NATIONALID", 0.3);
            Map<String, Object> documentSearch = iesService.documentSearch("index", "wcoIndex", mapFieldParams, mapWightParams, 1, 10);
            logger.debug(LogUtils.format("R", documentSearch));
        } catch (Exception e) {
            e.printStackTrace();
            fail("----------构建索引失败----------");
        }
    }
    
}
