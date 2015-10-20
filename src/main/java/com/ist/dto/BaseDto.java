package com.ist.dto;

import java.io.Serializable;

import io.searchbox.annotations.JestId;

/**
 * elasticsearch搜索和索引实体
 * 
 * @author qianguobing
 */
public class BaseDto implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -8479901738616586903L;
    /**
     * 文档id 用注解指定es索引id
     */
    @JestId
    private String id;
    /**
     * 文档名称
     */
    private String name;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档摘要
     */
    private String description;
    /**
     * 文档内容
     */
    private String content;
    /**
     * 文档路径
     */
    private String path;
    /**
     * 生成时间
     */
    private String createTime;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
