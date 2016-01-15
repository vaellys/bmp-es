package com.ist.ioc.service.common.elasticsearch.impl.commonPool;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.annotation.Resource;

import com.ist.common.es.util.LogUtils;
import com.ist.ioc.service.common.elasticsearch.IESService;

public class Task implements Callable<String>{
    @Resource
    IESService iesService;
    
    public Task(IESService iesService){
        this.iesService = iesService;
    }

    @Override
    public String call() throws Exception {
        try {
//          List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
//          Map<String, Object> map = new HashMap<String, Object>();
//          map.put("id", 1111111);
//          map.put("title", "外媒评6s和6s Plus上手:中规中矩 微微提升");
//          String content = "搜狐科技 文/王雪莹iPhone 6s的s是什么意思呢？拿到iPhone 6s的《华尔街日报》说，这个s可能就是“slightly（些微）better”的意思……这话怎么说？说好的电池续航呢？对于iPhone续航能力的问题一直是人们关注的要点，尽管库克此前信誓旦旦地说6s和6s Plus能让用户摆脱“贴墙族”（总守着插头充电的一族）的称号，但实际情况似乎跟我们期待并不相同。在实测中人们发现，除非开启“节能模式”（系统将自动关闭新消息亮屏提示），否则6s在正常使用的情况下，其耗电量并没有明显下降，反倒是5.5英寸的6s Plus的耗电量有所降低，二者的持续续航时间分别为8小时和8小时20分。此前业内普遍认为，机身增厚的新机型会为电池容量带来很大的提升空间，然而事实证明，0.01英寸的增厚更多地是为了配合3D Touch感压触屏技术，而非万众期待的电池技术。至于无线充电及快充技术，笔者认为6s和6s Plus的这项功能也属于噱头大于功效——仍需等待至少2小时（6s Plus要3小时）才能充满。相比之下，三星的最新旗舰机75~90分钟即可搞定，而新Moto X Pure只要1小时就能充满。How are you?";
//          map.put("content", content);
//          list.add(map);
          String keywords = "王莹";
          List<String> strList = new ArrayList<String>();
//          strList.add("content");
//          strList.add("title.py_only");
//          strList.add("title.py_none");
          strList.add("title");
          strList.add("title.raw");
          strList.add("content");
          strList.add("content.raw");
//          strList.add("content.en");
              List<Map<String, Object>> resultList = iesService.documentSearchWithCustomerScore("appletest", "iphone", strList, null, keywords, 1, 10);
      } catch (Exception e) {
          e.printStackTrace();
          fail("----------构建索引失败----------");
      }
        return null;
    }

}
