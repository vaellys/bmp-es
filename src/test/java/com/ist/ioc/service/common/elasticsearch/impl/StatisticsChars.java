package com.ist.ioc.service.common.elasticsearch.impl;

public class StatisticsChars {
    
    public static int statistic(String str){
        return str.length();
    }
    public static void main(String[] args) {
        String str = "中国银行反洗钱和反恐怖融资客户尽职调查及风险分类管理办法 （2008年版） 第一章　总则 第一条　为了预防集团面临的洗钱和恐怖融资（以下统称“洗钱”）";
        System.out.println(statistic(str));
    }
    
}
