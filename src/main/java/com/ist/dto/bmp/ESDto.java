package com.ist.dto.bmp;

import java.io.Serializable;

import com.ist.dto.BaseDto;

/**
 * elasticsearch搜索和索引实体
 * 
 * @author qianguobing
 */
public class ESDto extends BaseDto implements Serializable {
    /**
     * 序列化Id
     */
    private static final long serialVersionUID = 7712247850042953111L;
    /**
     * 机构key
     */
    private String organkey;
    private double similarity;
    public String getOrgankey() {
        return organkey;
    }
    public void setOrgankey(String organkey) {
        this.organkey = organkey;
    }
    
    private String contentPy;
    public String getContentPy() {
        return contentPy;
    }
    public void setContentPy(String contentPy) {
        this.contentPy = contentPy;
    }
    public double getSimilarity() {
        return similarity;
    }
    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }
}
