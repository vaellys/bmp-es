package com.ist.ioc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ist.common.es.util.page.Pagination;
import com.ist.ioc.service.common.elasticsearch.IESService;


@RequestMapping("/es")
@Controller
public class ESController {
    
    @Autowired
    private IESService iesService;
    private static final String INDEX_NAME = "vae";
    private static final String INDEX_TYPE = "test";
    @RequestMapping(value = "/index.do")
    public String documentIndex(ModelMap modelMap, HttpServletRequest request){
//        String path = "D:\\upload\\";
        try {
            String realPath = request.getSession().getServletContext().getRealPath("/");
            String dir = realPath + "/resources/doc/";
            iesService.dirHandler(INDEX_NAME, INDEX_TYPE, dir, 1);
            modelMap.put("message", "文章索引已创建成功!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "home";
    }
    
    @RequestMapping(value = "/search.do")
    public String documentSearch(ModelMap modelMap, HttpServletRequest request, String keywords){
        try {
            List<String> queryFields = new ArrayList<String>();
            queryFields.add("title");
            queryFields.add("description");
            queryFields.add("content");
            queryFields.add("name");
            queryFields.add("content.py");
            queryFields.add("content.en");
            List<String> indexNames = new ArrayList<String>();
            indexNames.add(INDEX_NAME);
            List<String> indexTypes = new ArrayList<String>();
            indexTypes.add(INDEX_TYPE);
            Pagination p = iesService.documentSearch(indexNames , indexTypes , queryFields, keywords, 1, 10);
            modelMap.put("esDtos", p.getList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "search";
    }
    
    @RequestMapping(value = "/home.do")
    public String index(){
        return "home";
    }
}
